package com.supernettechnologie.impro.decodage.util;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Pdf417Utils {

    private static final Logger log = LoggerFactory.getLogger(Pdf417Utils.class);
    public String numeroSerie;


    public static String stringToC40(String str) {
        str = StringUtils.stripAccents(str);
        str = str.toUpperCase();
        String c40 = "";
        if (str.length() < 3) {
            c40 = c40.concat(getThreeCharC40(str));
        } else {
            List<String> splitList = new ArrayList<>();
            for (int i = 0; i < str.length(); i += 3) {
                if ((i + 3) < str.length()) {
                    splitList.add(str.substring(i, i+3));
                    c40 = c40.concat(getThreeCharC40(str.substring(i, i+3)));
                } else {
                    c40 = c40.concat(getThreeCharC40(str.substring(i)));
                    splitList.add(str.substring(i));
                }

            }
        }
        return c40;
    }

    public static int getUsigneValue(int u1, int u2, int u3) {
        int u = (1600 * u1) + (40 * u2) + u3 + 1;
        return u;
    }

    public static String getThreeCharC40(String str_1) {

        C40Table c40Table = new C40Table();
        List<Integer> set_val = new ArrayList();
        int U = 0;
        String byte1 = "";
        String byte2 = "";
        if (str_1.length() == 3) {
            set_val = getIteration(str_1);
            log.debug("Valeur avant erreur "+ str_1);
            U = getUsigneValue(set_val.get(0), set_val.get(1), set_val.get(2)) ;
            byte1 = String.format("%02X",(int) U / 256);
            byte2 = String.format("%02X",(int) U % 256);
        } else if (str_1.length() == 2) {
            set_val = getIteration(str_1);
            set_val.add(0);
            U = getUsigneValue(set_val.get(0), set_val.get(1), set_val.get(2)) ;
            byte1 = String.format("%02X",(int) U / 256);
            byte2 = String.format("%02X",(int) U % 256);
        } else if (str_1.length() == 1) {
            byte1 = String.format("%02X", 254);
            byte2 = String.format("%02X",((int)str_1.charAt(0)) + 1);
        }
        return byte1 + byte2;
    }

    public static List<Integer> getIteration(String str_1) {
        C40Table c40Table = new C40Table();
        List<Integer> set_val = new ArrayList<>();
        for (int i = 0; i < str_1.length(); i++) {
            if (str_1.charAt(i) == ' ') {
                set_val.add(c40Table.getC40_table().get("SPACE"));
                System.out.println(str_1.charAt(i)+""+ c40Table.getC40_table().get("SPACE"));
            } else {
                set_val.add(c40Table.getC40_table().get(""+str_1.charAt(i)));
                System.out.println(str_1.charAt(i)+""+ c40Table.getC40_table().get(""+str_1.charAt(i)));
            }
        }
        return set_val;
    }

    public static String LocalDateToString(LocalDate localDate) {
        String str = "";
        str = str.concat(String.format("%02X", localDate.getMonthValue()));
        str = str.concat(String.format("%02X",localDate.getDayOfMonth()));
        str = str.concat(String.format("%04X",localDate.getYear()));
//        str = String.format("%02X", Integer.parseInt(str));
        return str;
    }

    public static PublicKey readCertificate(String filePath) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
// explicit BC factory required, knows about curve!
        CertificateFactory fact = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
        PemReader reader = new PemReader(new FileReader(filePath));
        PemObject readPemObject = reader.readPemObject();
        final byte[] cert = readPemObject.getContent();
        java.security.cert.Certificate generatedCertificate = fact.generateCertificate(new ByteArrayInputStream(cert));
        System.out.println(generatedCertificate.getPublicKey());
        return generatedCertificate.getPublicKey();
    }


    public static java.security.cert.Certificate getCertificate(String filePath) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
// explicit BC factory required, knows about curve!
        CertificateFactory fact = CertificateFactory.getInstance("X509", BouncyCastleProvider.PROVIDER_NAME);
        PemReader reader = new PemReader(new FileReader(filePath));
        PemObject readPemObject = reader.readPemObject();
        final byte[] cert = readPemObject.getContent();
        java.security.cert.Certificate generatedCertificate = fact.generateCertificate(new ByteArrayInputStream(cert));
        X509Certificate certificate = (X509Certificate) generatedCertificate;
        System.out.println("Certificate Serial Number "+ certificate.getIssuerDN());
//        System.out.println(generatedCertificate);
        return generatedCertificate;
    }

    public static String getHeader() {
        return "DC0360C76D2BAFA519A60C70613FA8F15d01";
    }

    public static String getHeaders(String mc, String signataire) {
        String mcn = stringToC40(mc);
        //String dateCreate = LocalDateToString(createdAt);
        //String dateSigne = LocalDateToString(signatureDate);
        String  signeur = stringToC40(signataire);
        //String serieCert = stringToC40(numeroSerie);

        mcn = "01"+ String.format("%02X",(mcn.length()/2))+mcn;
        //dateCreate = "02"+ String.format("%02X",(dateCreate.length()/2))+dateCreate;
        //dateSigne = "03"+ String.format("%02X",(dateSigne.length()/2))+dateSigne;
        signeur = "04"+ String.format("%02X",(signeur.length()/2))+signeur;
        //serieCert = "05"+ String.format("%02X",(serieCert.length()/2))+serieCert;

        return mcn+signeur;
    }
    public static String getHeaderImm(String mc, String signataire) {
        String mcn = stringToC40(mc);

        String  signeur = stringToC40(signataire);
        mcn = "01"+ String.format("%02X",(mcn.length()/2))+mcn;
        signeur = "04"+ String.format("%02X",(signeur.length()/2))+signeur;

        return mcn+signeur;
    }

    public static String getBody(String nomCons, String nomPrenom){
        String conss = stringToC40(nomCons);
//        String nIfu = stringToC40(ifu);
//        String nRccm = stringToC40(rccm);
        String name = stringToC40(nomPrenom);
        //String tel = stringToC40(phone);

        conss = "01"+ String.format("%02X",(conss.length()/2))+conss;
//        nIfu = "02"+ String.format("%02X",(nIfu.length()/2))+nIfu;
//        nRccm = "03"+ String.format("%02X",(nRccm.length()/2))+nRccm;
        name = "04"+ String.format("%02X",(name.length()/2))+name;
        //tel = "05"+ String.format("%02X",(tel.length()/2))+tel;

        return conss+name;
    }
    public static String getBodyConss(String nomCons, String carteW, LocalDate date){
        String conss = stringToC40(nomCons);
        String carte = stringToC40(carteW);
        String dateCreate = LocalDateToString(date);
        dateCreate = "02"+ String.format("%02X",(dateCreate.length()/2))+dateCreate;

        conss = "01"+ String.format("%02X",(conss.length()/2))+conss;
        carte = "02"+ String.format("%02X",(carte.length()/2))+carte;
        return conss+carte+dateCreate;
    }
    public static String getBodyImm(String sth, LocalDate date, String numeroSerie){
        String nRccm = stringToC40(numeroSerie);
        String sthh = stringToC40(sth);
        String dateCreate = LocalDateToString(date);
        nRccm = "03"+ String.format("%02X",(nRccm.length()/2))+nRccm;
        dateCreate = "02"+ String.format("%02X",(dateCreate.length()/2))+dateCreate;
        sthh = "03"+ String.format("%02X",(sthh.length()/2))+sthh;
        return sthh+dateCreate+nRccm;
    }

    public static byte[] sign(String plainText, String privateKeyPath) throws Exception {
        PrivateKey privateKey = loadKeyPair();
        System.out.println(privateKey.getAlgorithm());
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(plainText.getBytes("UTF-8"));
        return ecdsaSign.sign();
    }

    public static PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static PrivateKey readPrivateKey(String keyPath) throws Exception{
        File privKeyFile = new File(keyPath);
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(privKeyFile));
        } catch(FileNotFoundException e) {
            throw new Exception("Could not locate keyfile at '" + keyPath + "'", e);
        }
        byte[] privKeyBytes = new byte[(int)privKeyFile.length()];
        bis.read(privKeyBytes);
        bis.close();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec ks = new PKCS8EncodedKeySpec(privKeyBytes);
        RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(ks);
        return (PrivateKey) privKey;
    }

    //Method to retrieve the Public Key from a file
    public static PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePublic(spec);
    }

    public static PublicKey generatePublicKey(
        String filename) throws InvalidKeySpecException,
        FileNotFoundException, IOException {FileInputStream in = new FileInputStream(filename);
        byte[] keyBytes = new byte[in.available()];
        in.read(keyBytes);
        in.close();

        String pubKey = new String(keyBytes, "UTF-8");
        pubKey = pubKey.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");

        // don't use this for real projects!
        BASE64Decoder decoder = new BASE64Decoder();
        keyBytes = decoder.decodeBuffer(pubKey);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }

    public static boolean verify(String message, byte[] signature, PublicKey publicKeyPath) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        PublicKey publicKey = publicKeyPath;
        String st = URLDecoder.decode(new String(signature), "UTF-8");
        byte[] signatureByte = DatatypeConverter.parseBase64Binary(st);
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(message.getBytes(StandardCharsets.UTF_8));
        return ecdsaVerify.verify(signatureByte);
    }


    public static BigInteger extractR(byte[] signature) throws Exception {
        int startR = (signature[1] & 0x80) != 0 ? 3 : 2;
        int lengthR = signature[startR + 1];
        return new BigInteger(Arrays.copyOfRange(signature, startR + 2, startR + 2 + lengthR));
    }

    public static BigInteger extractS(byte[] signature) throws Exception {
        int startR = (signature[1] & 0x80) != 0 ? 3 : 2;
        int lengthR = signature[startR + 1];
        int startS = startR + 2 + lengthR;
        int lengthS = signature[startS + 1];
        return new BigInteger(Arrays.copyOfRange(signature, startS + 2, startS + 2 + lengthS));
    }

    public static String getBEREncondedSignature (String R, String S) {
        R = R.toUpperCase();
        String r = R;
        String s = S.toUpperCase();
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

    public static ASN1Sequence convertSignatureToASN1DER(String signatureHex)
    {
        System.out.println("Encoding hex signature to ASN1 DER... ");

        System.out.println("Received signature string : "+signatureHex);
        //extracting r and s
        System.out.println("Extracting r and s  as string");
        byte[] first32SignatureBytes = Arrays.copyOfRange(DatatypeConverter.parseHexBinary(signatureHex), 0,32);
        String signaturePartR = new String(Hex.encode(first32SignatureBytes));
        byte[] last32SignatureBytes = Arrays.copyOfRange(DatatypeConverter.parseHexBinary(signatureHex), 32,64);
        String signaturePartS = new String(Hex.encode(last32SignatureBytes));
        System.out.println("Signature part R"+signaturePartR);
        //converting r and s to asn1 and putting them in sequence
        System.out.println("Converting r and s to asn1 and putting them in sequence");
        ASN1Integer r = new ASN1Integer(new BigInteger(signaturePartR, 16));
        ASN1Integer s = new ASN1Integer(new BigInteger(signaturePartS, 16));
        ASN1Sequence seq = new DERSequence(new ASN1Encodable[] { r, s });
        System.out.println("r and s converted to asn1.. " + seq);

        return seq;
    }

    private static PrivateKey getPrivateKeyFromPEM(final Reader keyReader)
        throws IOException {
        final JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();

        final PEMParser pem = new PEMParser(keyReader);

        PrivateKey key;
        Object pemContent = pem.readObject();
        if(pemContent instanceof PEMKeyPair) {
            PEMKeyPair pemKeyPair = (PEMKeyPair)pemContent;
            KeyPair keyPair = jcaPEMKeyConverter.getKeyPair(pemKeyPair);
            key = keyPair.getPrivate();
        } else if (pemContent instanceof PrivateKeyInfo) {
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemContent;
            key = jcaPEMKeyConverter.getPrivateKey(privateKeyInfo);
        } else {
            throw new IllegalArgumentException("Unsupported private key format '" + pemContent.getClass().getSimpleName() + '"');
        }

        pem.close();
        return key;
    }

    public static PrivateKey readPrivateKeyFromFile(String privateKey) {
        try (StringReader keyReader = new StringReader(privateKey);
             PEMParser pemReader = new PEMParser(keyReader)) {

            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            Object keyPair = pemReader.readObject();
            if (keyPair instanceof PrivateKeyInfo) {
                return converter.getPrivateKey((PrivateKeyInfo) keyPair);
            } else {
                return converter.getPrivateKey(((PEMKeyPair) keyPair).getPrivateKeyInfo());
            }
        } catch (IOException x) {
            // Shouldn't occur, since we're only reading from strings
            throw new RuntimeException(x);
        }
    }

    private static PrivateKey loadKeyPair() throws IOException {
        PEMParser reader = new PEMParser(new FileReader(Signer.PRIVATE_KEY_PATH));
        Object pemObject;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        //PEMDecryptorProvider decryptionProv = new JcePEMDecryptorProviderBuilder().build(passphrase);

        while((pemObject = reader.readObject()) != null) {
            System.out.println("PemObject type: " + pemObject.getClass().getName());

            if(pemObject instanceof PEMKeyPair) {
                System.out.println("it match");
                PrivateKeyInfo pki = ((PEMKeyPair) pemObject).getPrivateKeyInfo();
                System.out.println("content: " + pki.getEncoded("UTF-8"));
                return converter.getPrivateKey(pki);
            } else {
                System.out.println("Dont match");
            }
        }

        System.out.println("fsdfsfs");
        return null;
    }


    public PublicKey getPublicKey() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Reader rdr = new StringReader(
            "-----BEGIN PUBLIC KEY-----\n" +
                "MEkwEwYHKoZIzj0CAQYIKoZIzj0DAQEDMgAEXMHnQfWiM4oCaLfx296llgz7iaVv\n" +
                "avMPppkzVNZAxtlNLhFlXnNWD0Mw9yzP8/Go\n" +
                "-----END PUBLIC KEY-----\n"
        ); // or from file etc.

        org.bouncycastle.util.io.pem.PemObject spki = new org.bouncycastle.util.io.pem.PemReader(rdr).readPemObject();
        PublicKey key = KeyFactory.getInstance("EC", "BC").generatePublic(new X509EncodedKeySpec(spki.getContent()));
        return key;
    }


}


