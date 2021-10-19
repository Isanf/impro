package com.supernettechnologie.impro.decodage.util;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class Test {

    public static int getUsigneValue(int u1, int u2, int u3) {
        return (1600 * u1) + (40 * u2) + u3 + 1;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        String data = "01021FE10204021B07E40304050C07E404046D82D25101046DBFAB77020419A626CA0304262626C104086DBFAB77157BA6E1FF31333730818602413cca87b34d0d229d0c99bde9adea1705bd1288bfd762ad197bd5b28b00555251948bf9e3452ab09acf4d3c5819b700baa49f28c6bd1ba0275a4c1c9a340c4a281c02410d0d462621ae0d43473ca4586c8955a8a4cf318ebda5824892e27ec6b94be0c923b509a7a8a3ceecab1d816a04750ceca79e2ab5be297778fbfd7e369515cec058";
        //buildPKCS8Key(new File("my.key.pem"));
        //System.out.println(Hex.toHexString(Pdf417Utils.sign("test", "my.key.pem")));
        //System.out.println(Pdf417Utils.stringToC40("DGTTM").length());
        System.out.println(Pdf417Utils.verify("test", Pdf417Utils.sign("test", "my.key.pem"), Pdf417Utils.getPublic("public.der")));
    }

    private static final String SPEC = "secp256k1";
    private static final String ALGO = "SHA256withECDSA";


    static final Base64.Decoder DECODER = Base64.getMimeDecoder();

    private static byte[] buildPKCS8Key(File privateKey) throws IOException {
        final String s = new String(Files.readAllBytes(privateKey.toPath()));
        if (s.contains("--BEGIN PRIVATE KEY--")) {
            return DECODER.decode(s.replaceAll("-----\\w+ PRIVATE KEY-----", ""));
        }
        if (!s.contains("--BEGIN EC PRIVATE KEY--")) {
            throw new RuntimeException("Invalid cert format: "+ s);
        }

        final byte[] innerKey = DECODER.decode(s.replaceAll("-----\\w+ EC PRIVATE KEY-----", ""));
        final byte[] result = new byte[innerKey.length + 26];
        System.arraycopy(DECODER.decode("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKY="), 0, result, 0, 26);
        System.arraycopy(BigInteger.valueOf(result.length - 4).toByteArray(), 0, result, 2, 2);
        System.arraycopy(BigInteger.valueOf(innerKey.length).toByteArray(), 0, result, 24, 2);
        System.arraycopy(innerKey, 0, result, 26, innerKey.length);
        return result;
    }
}
