package com.supernettechnologie.impro.decodage.web;


import com.supernettechnologie.impro.decodage.domain.CodeDatas;
import com.supernettechnologie.impro.decodage.service.DecodeQuitanceService;
import io.micrometer.core.annotation.Timed;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/api")
public class DecodeQuitanceRessource {
    private final Logger log = LoggerFactory.getLogger(DecodeRessource.class);
    private final DecodeQuitanceService decodeQuitanceService;

    public DecodeQuitanceRessource(DecodeQuitanceService decodeService) {
        this.decodeQuitanceService = decodeService;
    }

    @GetMapping("/decodage-quitance")
    @Timed
    public CodeDatas decodeDatas(@RequestParam("data") String datas) throws IOException {
        //datas = new String(Hex.decode(datas));
        return decodeQuitanceService.decodage(datas);
    }
}
