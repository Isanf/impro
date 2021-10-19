package com.supernettechnologie.impro.decodage.service;

import com.supernettechnologie.impro.decodage.domain.CodeDatas;
import com.supernettechnologie.impro.decodage.domain.Data;
import com.supernettechnologie.impro.decodage.util.Pdf417Utils;
import com.supernettechnologie.impro.decodage.util.QuitanceUtils;
import com.supernettechnologie.impro.decodage.util.Signer;
import com.supernettechnologie.impro.decodage.util.Utils;
import com.supernettechnologie.impro.web.rest.errors.CustomParameterizedException;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DecodeQuitanceService {
    private final Logger log = LoggerFactory.getLogger(DecodeService.class);

    private final DecodeService decodeService;

    public DecodeQuitanceService(DecodeService decodeService) {
        this.decodeService = decodeService;
    }

    /*
       decodage du code scanne(PDF 417)
     */
    public CodeDatas decodage(String datas) throws IOException {
        datas=datas.trim();
        log.debug("*************---------------- {}", datas);
        String header = datas.substring(0, Utils.HEADER_LENGTH);
        List<Data> dataListHeader=Utils.decodeHeaderDatas(header);
        CodeDatas code=this.decodeQuitance(header, datas.substring(Utils.HEADER_LENGTH));
        code.setHeader(dataListHeader);
        return code;
    }

    public CodeDatas decodeQuitance(String header, String datas) {
        List<Data> list= QuitanceUtils. getDataStructure();
        List<Data> results = new ArrayList<>();
        int i = 0;
        String message = "";
        CodeDatas codeDatas = new CodeDatas();
        for (Data data : list) {
            i++;
            String code = "";
            String prev = datas.substring(0, 4);//tag+length
            datas = datas.substring(4);
            try {
                code = datas.substring(0, Utils.getDecimal(prev.substring(2)) * 2);
                datas = datas.substring((Utils.getDecimal(prev.substring(2)) * 2));//message restant
            } catch (StringIndexOutOfBoundsException ex) {
                throw new CustomParameterizedException("oups!!!! Veuillez réessayer à  nouveau!");
            }
            Utils.decode(results, data, code);
            message = message + prev + code;
            if (i == list.size()) {
                codeDatas.setSignature(datas);
            }
        }
        codeDatas.setMessage(results);
        boolean result = false;
        try {
            message = header + message;
            String signature = codeDatas.getSignature().substring(8);
            codeDatas.setSignature(signature);
            result = Pdf417Utils.verify(message, Hex.decode(signature), Pdf417Utils.getPublic(Signer.PUBLIC_KEY_PATH));
            codeDatas.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codeDatas;
    }



}
