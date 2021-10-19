package com.supernettechnologie.impro.decodage.service;


import com.supernettechnologie.impro.decodage.domain.CodeDatas;
import com.supernettechnologie.impro.decodage.domain.Data;
import com.supernettechnologie.impro.decodage.domain.Format;
import com.supernettechnologie.impro.decodage.util.RSA;
import com.supernettechnologie.impro.decodage.util.Utils;
import com.supernettechnologie.impro.service.UserService;
import com.supernettechnologie.impro.web.rest.errors.CustomParameterizedException;
import org.apache.commons.lang3.SystemUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for decode
 */
@Service
@Transactional
public class DecodeService {
    private final Logger log = LoggerFactory.getLogger(DecodeService.class);
    private final UserService userService;

    public DecodeService(UserService userService) {
        this.userService = userService;
    }


    /*
            Decodage de la partie HEADER du code scanne (PDF417)
            On utilise la liste decrivant la structure du HEADER
     */
    public List<Data> decodeHeaderDatas(String datas) {
        List<Data> list= Utils.getHeaderDatasStructure();
        List<Data> results = new ArrayList<>();
        for (Data data : list) {
            int length = data.getLength() * 2;
            Format format = data.getFormat();
            String code = "";
            code = datas.substring(0, length);
            datas = datas.substring(length);
            decode(results, data, code);
        }
        return results;
    }


    /*
        decodage de la partie Message du code scanne(PDF417)
        On utilise la liste decrivant la structure du MESSAGE
     */
    public CodeDatas decodeMessageDatas(String header, String datas, Data certAuth) throws IOException {
        List<Data> list=Utils.getMessageDatasStructure();
        List<Data> results = new ArrayList<>();
        int i = 0;
        String message = "";
        String signatureBer;

        CodeDatas codeDatas = new CodeDatas();
        for (Data data : list) {
            i++;
            Format format = data.getFormat();
            String code = "";
            String prev = datas.substring(0, 4);//tag+length
            log.debug("tag+length "+prev);
            try {
                if (data.getNumeroOrdre() == Utils.getDecimal(prev.substring(0,2))) {
                    datas = datas.substring(4);
                    code = datas.substring(0, Utils.getDecimal(prev.substring(2)) * 2);
                    datas = datas.substring((Utils.getDecimal(prev.substring(2)) * 2));//message restant
                    decode(results, data, code);


                    message = message + prev + code;
                }
            }
            catch (StringIndexOutOfBoundsException ex) {
                throw new CustomParameterizedException("oups!!!! Veuillez réessayer à  nouveau!");
            }
            if (i == list.size()) {
                //Apres avoir extrait le message le code restant represente la signature du code
                codeDatas.setSignature(datas);
                log.debug("signature : "+datas );
                log.debug("signature length : "+datas.length());
                log.debug("message length "+message.length());
                log.debug("Message : "+message);
            }
        }


        //VÃ©rification de l'authenticitÃ© du message
        signatureBer = getDerEncodedData(codeDatas.getSignature());
        String messages = header + message;

        return codeDatas;
    }

    /**
        Encodage de la signature en BER
     */
    private static String getBEREncondedSignature (String str) {
        String sig = str.toUpperCase();
        if (sig.startsWith("FF40")) {
            sig = sig.substring(4);
        }
        if (sig.endsWith("0D")) {
            sig = sig.substring(0, sig.length()-2);
        }
        System.out.println(sig);
        String r = sig.substring(0, sig.length()/2);
        String s = sig.substring(sig.length()/2);
        if (r.charAt(0) == '8' || r.charAt(0) == '9' || r.charAt(0) == 'A' || r.charAt(0) == 'B' || r.charAt(0) == 'C' || r.charAt(0) == 'D' || r.charAt(0) == 'E' || r.charAt(0) == 'F'){
            r = "00"+r;
        }
        if (s.charAt(0) == '8' || s.charAt(0) == '9' || s.charAt(0) == 'A' || s.charAt(0) == 'B' || s.charAt(0) == 'C' || s.charAt(0) == 'D' || s.charAt(0) == 'E' || s.charAt(0) == 'F'){
            s = "00"+s;
        }
        int len = r.length() + s.length();
        String sign = "30"+((len/2)-20) +"02"+((r.length()/3)-1)+r+"02"+((s.length()/3)-1)+s;
        System.out.println("Signature "+sign.toLowerCase());
        return sign.toLowerCase();
    }

    public static String getDerEncodedData(String signature) {
        String sig = signature.toUpperCase();
        if (sig.startsWith("FF40")) {
            sig = sig.substring(4);
        }
        if (sig.endsWith("0D")) {
            sig = sig.substring(0, sig.length()-2);
        }
        System.out.println(sig);
        String r = sig.substring(0, sig.length()/2);
        String s = sig.substring(sig.length()/2);
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(/*r*/new ASN1Integer(new BigInteger(r, 16)));
        v.add(/*s*/new ASN1Integer(new BigInteger(s, 16)));
        byte[] sigDer = new byte[0];
        try {
            sigDer = new DERSequence(v).getEncoded();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RSA.toHex(sigDer);
    }


    /*
        ecriture de la signature fprmant BER dansle fichier de vÃ©rification
     */
    public static void writeFile (String message,String pathWriting) throws IOException {
        File file = new File(pathWriting);
        file.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(file);
        writer.write(message);
        writer.close();
    }

    /*
        lecture du rÃ©sultat de la vÃ©rification dans le fichier result
     */
    public static String readFile (String pathreading) throws IOException {
        String sCurrentLine = "";
        File file = new File(pathreading);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            sCurrentLine = br.readLine();
            System.out.println(sCurrentLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sCurrentLine;
    }



      /*
        lancement du script de vÃ©rification de l'authenticitÃ© de la signature surwindows
     */
    public static void signVerificationWindows (String genKeysScripts,String  messageText,String messageBin,String signatureText,String signatureBin,String pkyPem,String resultat) {
        try {
            String cmd = String.format("%s %s %s %s %s %s %s", genKeysScripts, messageText, messageBin,signatureText,signatureBin, pkyPem,resultat);
//            Process p = new ProcessBuilder("C:/Program Files/Git/git-bash", "-c",cmd).start();
//            p.waitFor();

            final ProcessBuilder processBuilder = new ProcessBuilder("C:/Program Files/Git/git-bash", "-c", cmd);
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

            processBuilder.start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

     /*
        lancement du script de vÃ©rification de l'authenticitÃ© de la signature sur linux
     */
    public static void signVerificationLinux (String genKeysScripts,String  messageText,String messageBin,String signatureText,String signatureBin,String pkyPem,String resultat) {
        try {
            String cmd = String.format("%s %s %s %s %s %s %s", genKeysScripts, messageText, messageBin,signatureText,signatureBin, pkyPem,resultat);
            //String cmd = genKeysScriptsLinux;

            final ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
            processBuilder.redirectInput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

            processBuilder.start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    /*
        Permettre de decode une chaine en fonction du format
     */
    private void decode(List<Data> results, Data data, String code) {

        String result = "";
        switch (data.getFormat()) {
            case C40:
                result = Utils.fromHexatoC40(code);
                break;
            case DATE:
                result = Utils.fromHexaToDate(code);
                break;
            case NO_FORMAT:
                result = code;
                break;
            case INT:
                result = String.valueOf(Utils.getDecimal(code));
                break;
        }
        Data resultData = new Data(data.getTagName(), data.getDescription(), result, code, data.getFormat());
        results.add(resultData);
    }
}
