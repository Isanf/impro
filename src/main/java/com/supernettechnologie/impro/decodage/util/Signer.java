package com.supernettechnologie.impro.decodage.util;

import com.supernettechnologie.impro.decodage.RSAKeys;
import com.supernettechnologie.impro.domain.Organisation;
import org.apache.commons.lang3.SystemUtils;
import org.bouncycastle.util.encoders.Hex;

import javax.xml.bind.DatatypeConverter;
import java.net.URLEncoder;
import java.time.LocalDate;

public class Signer {
    public static final String PUBLIC_KEY_PATH = publicKeyPath();
    public static final String PRIVATE_KEY_PATH = privateKeyPaht();

    private static String publicKeyPath() {
        if (SystemUtils.IS_OS_LINUX) {
            return "/root/PrivateKey/public.der";
        } else {
            return "public.der";
        }
    }

    private static String privateKeyPaht () {
        if (SystemUtils.IS_OS_LINUX) {
            return "/root/PrivateKey/my.key.pem";
        } else {
            return "my.key.pem";
        }
    }

    public static String getPdf417Bytes(Organisation organisation) throws Exception {
        TagsTable tagsTable = new TagsTable();
        String header = Pdf417Utils.getHeader();
        String numero = Pdf417Utils.stringToC40(organisation.getNumeroOrdre()+"");
        String nom = Pdf417Utils.stringToC40(organisation.getNom());
        String dateQuitance = Pdf417Utils.LocalDateToString(LocalDate.now());
        String nomDemandeur = organisation.getNom();
        numero = tagsTable.getTagsTable().get("numero") + String.format("%02X",(numero.length()/2))+numero;
        nom = tagsTable.getTagsTable().get("nom") + String.format("%02X",(nom.length()/2))+nom;
        dateQuitance = tagsTable.getTagsTable().get("dateQuitance") + String.format("%02X",(dateQuitance.length()/2))+dateQuitance;
        nomDemandeur = tagsTable.getTagsTable().get("nomDemandeur") + String.format("%02X", (nomDemandeur.length() / 2))+ nomDemandeur;
        String data = header+numero+nom+dateQuitance+nomDemandeur;
//        PublicKey publicKey = Pdf417Utils.getPublic(PUBLIC_KEY_PATH);
        byte[] signatureBytes = Pdf417Utils.sign(data, PRIVATE_KEY_PATH);
        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
        signToString = URLEncoder.encode(signToString, "UTF-8");
        String hex = new String (Hex.encode(signToString.getBytes()));
        return data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes())) + hex;
    }

}
