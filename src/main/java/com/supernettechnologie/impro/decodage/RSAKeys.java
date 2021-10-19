package com.supernettechnologie.impro.decodage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeys {
    public static final String PUBLIC_KEY_FILE = "public.key";
    public static final String PRIVATE_KEY_FILE = "private.key";

    public static void generateKeys(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec rsaPrivateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            RSAPublicKeySpec rsaPublicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

            RSAKeys rsaObj = new RSAKeys();
            rsaObj.savekeys(PRIVATE_KEY_FILE, rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());
            rsaObj.savekeys(PUBLIC_KEY_FILE, rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
            System.out.println("keys generated successful : ");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }
    }
    public static byte[] signer(String data){
        byte[] encrypteData;
        System.out.println("Avant Signature : " + data);

        RSAKeys rsaObj = new RSAKeys();
        encrypteData = rsaObj.encrypteData(data);

        System.out.println("Data Après signature : " + new String(encrypteData));
        return encrypteData;
    }
    private void savekeys(String filname, BigInteger mod, BigInteger expr) throws IOException {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(filname);
            oos = new ObjectOutputStream(new BufferedOutputStream(fos));
            oos.writeObject(mod);
            oos.writeObject(expr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (oos != null) {
                oos.close();
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    public byte[] encrypteData(String data){
        byte[] dataToEncrypte = data.getBytes();
        byte[] encryptedData = null;

        try {
            PublicKey publicKey = readPublicKeyFromFile(PUBLIC_KEY_FILE);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedData = cipher.doFinal(dataToEncrypte);

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | IOException | BadPaddingException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static byte[] decrypteData(byte[] data, String key){
        byte[] decriptedData = null;

        try {
            PrivateKey privateKey = readPrivateKeyFromFile(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decriptedData = cipher.doFinal(data);

        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return decriptedData;
    }

    public static PrivateKey readPrivateKeyFromFile(String filName) throws IOException {
        FileInputStream fis =null;
        ObjectInputStream ois = null;
        PrivateKey privateKey = null;
        try {
            fis = new FileInputStream(new File(filName));
            ois = new ObjectInputStream(fis);

            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            //Grt private key
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
            return privateKey;
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
                fis.close();
            }
        }
        return privateKey;
    }
    public static PublicKey readPublicKeyFromFile(String filName) throws IOException {
        FileInputStream fis =null;
        ObjectInputStream ois = null;
        PublicKey publicKey = null;
        try {
            fis = new FileInputStream(new File(filName));
            ois = new ObjectInputStream(fis);

            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();

            //Grt private key
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
            return publicKey;
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
                fis.close();
            }
        }
        return publicKey;
    }
}
