package com.supernettechnologie.impro.decodage.util;


import com.supernettechnologie.impro.decodage.domain.Data;
import com.supernettechnologie.impro.decodage.domain.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
    HEA
 */
public class Utils {

    private final static List<Data> HEADER_STRUCTURE = new ArrayList<>();
    private final static List<Data> MESSAGE_STRUCTURE = new ArrayList<>();
    public static final int HEADER_LENGTH = 24;
    public static final int HEADER_LENGTHI = 48;
    public static final int MESSAGE_LENGTH = 242;
    public static final int SIGNATURE_LENGTH = 66;
    public static final int MESSAGE_TAG_LENGTH = 2;
    public static final int MESSAGE_C_LENGTH = 2;
    public static final Integer NIVEAU_OPERATION_TYPE_ORGANISATION = 2;
    public static final Integer NIVEAU_OPERATION_GUICHET_CONCESSIONNAIRE = 3;
    public static final Integer NIVEAU_CONCESSIONNAIRE_REGIONAL = 4;
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    static {
        //Magic Constant
        HEADER_STRUCTURE.add(new Data("Magic Constant", "Identification d’un code barre conforme à la spec", Format.NO_FORMAT, 1, 1));
        //Version
        //HEADER_STRUCTURE.add(new Data("Date de Création", "date de création de la carte w", Format.DATE, 1, 2));
        //Country
        HEADER_STRUCTURE.add(new Data("Date de signature", "Date de la signature du doc", Format.DATE, 2, 2));
        //Cert Auth & Ref
        HEADER_STRUCTURE.add(new Data("Signataire", "Authorité de la signature", Format.C40, 6, 3));
        //Doc issue date
//        HEADER_STRUCTURE.add(new Data("Doc issue date", "Date de création", Format.DATE, 3, 5));
        //Signature date
//        HEADER_STRUCTURE.add(new Data("signature date", "Date de signature", Format.DATE, 3, 6));
        //Doc Feature desc
//        HEADER_STRUCTURE.add(new Data("Doc Feature desc", "Défini le numéro et le codage du doc", Format.INT, 1, 7));
        //Doc Type
//        HEADER_STRUCTURE.add(new Data("Doc Type", "Type de doc (DL ou VR)", Format.INT, 1, 8));

        /**
         * ***********Partie corps du message*************
         */
        //
        MESSAGE_STRUCTURE.add(new Data("Doc number", "Numéro de document", Format.C40, getDecimal("08"), 1));
        //Version
        MESSAGE_STRUCTURE.add(new Data("Chassis number-VIN", "Numéro de série", Format.C40, getDecimal("0C"), 2));
        //Country
        MESSAGE_STRUCTURE.add(new Data("Plate Registration Number", "Numéro d’immatriculation", Format.C40, getDecimal("08"), 3));
        //Cert Auth & Ref
        MESSAGE_STRUCTURE.add(new Data("Genre code (type )", "Genre", Format.INT, getDecimal("01"), 4));
        //Doc issue date
        MESSAGE_STRUCTURE.add(new Data("Brand", "Marque", Format.C40, getDecimal("0C"), 5));
        //Signature date
        MESSAGE_STRUCTURE.add(new Data("Color", "Couleur", Format.C40, getDecimal("08"), 6));
        //Doc Feature desc
        MESSAGE_STRUCTURE.add(new Data("PTAC", "PTAC", Format.C40, getDecimal("08"), 7));
        //Doc Type
        MESSAGE_STRUCTURE.add(new Data("People quantity allowed", "Nombre de places", Format.INT, getDecimal("01"), 8));
        //
        MESSAGE_STRUCTURE.add(new Data("First usage", "Date de première mise en circulation", Format.DATE, getDecimal("03"), 9));
        //
        MESSAGE_STRUCTURE.add(new Data("Owner", "Nom de titulaire + prénom si personne; ", Format.C40, getDecimal("28"), 10));

        // MESSAGE_STRUCTURE.add(new Data("Signature message", "signature electronique; ", Format.C40, getDecimal("0A"), 11));
    }

    /*
        Structure de la partie HEADER du message
     */
    public static List<Data> getHeaderDatasStructure() {
        return HEADER_STRUCTURE;
    }


    /*
        Structure de la partie Message du code
     */
    public static List<Data> getMessageDatasStructure() {
        return MESSAGE_STRUCTURE;
    }


    /*
        Convertir une chaine hexadecimal en C40
     */
    public static String fromHexatoC40(String valeur) {
        int length = valeur.length();
        String ascii = "";
        for (int i = 0; i < (int) length / 4; i++) {
            String hexa = valeur.substring(0, 4);
            valeur = valeur.substring(4);
            long i1 = getDecimal(hexa) / 256;
            long i2 = getDecimal(hexa) % 256;
            if (i1 == 0xFE) {
                ascii += ((char) (i2 - 1));
            } else {
                long val16 = (i1 * 256) + i2;
                long u1 = (val16 - 1) / 1600;
                long u2 = (val16 - (u1 * 1600)) / 40;
                long u3 = val16 - (u1 * 1600) - (u2 * 40) - 1;
                long[] list = {u1, u2, u3};
                for (long u : list) {
                    if (u == 3) {
                        ascii += ((char) (32));
                    } else if (u >= 4 && u <= 13) {
                        ascii += ((char) (u + 44));
                    } else if (u >= 14 && u <= 39) {
                        ascii += ((char) (u + 51));
                    }
                }
            }
        }
        return ascii;
    }

    /*
        Convertir une chaine hexadecimal en decimal
     */
    public static int getDecimal(String hex) {
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        int val = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    public static String fromHexaToDate(String hexa) {
        int decimalDate = getDecimal(hexa);
        String stringDate = "" + decimalDate;
        String str = "";
        int i = stringDate.length();
        while (i < 8) {
            str += "0";
            i++;
        }
        stringDate = str.concat(stringDate);
        System.out.println(stringDate);
        int month = Integer.parseInt(stringDate.substring(0, 2));
        int day = Integer.parseInt(stringDate.substring(2, 4));
        int year = Integer.parseInt(stringDate.substring(4));
        String monthStr = month+"";
        String dayStr = day +"";
        String yearStr = year +"";
        if(month < 10) {
            monthStr = "0"+monthStr;
        }
        if(day < 10) {
            dayStr = "0"+dayStr;
        }
        if(year < 10) {
            yearStr = "0"+yearStr;
        }
        return yearStr + "-" + monthStr + "-" + dayStr;
    }


    public static void decode(List<Data> results, Data data, String code) {
        String result = "";
        switch (data.getFormat()) {
            case C40:
                result = Utils.fromHexatoC40(code);
                System.out.println(result);
                break;
            case DATE:
                result = Utils.fromHexaToDate(code);
                System.out.println(result);
                break;
            case NO_FORMAT:
                result = code;
                System.out.println(result);
                break;
            case INT:
                result = String.valueOf(Utils.getDecimal(code));
                System.out.println(result);
                break;
        }
        Data resultData = new Data(data.getTagName(), data.getDescription(), result, code, data.getFormat());
        results.add(resultData);
    }

    /*
            Decodage de la partie HEADER du code scanne (PDF417)
            On utilise la liste decrivant la structure du HEADER
     */
    public static List<Data> decodeHeaderDatas(String datas) {
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
}
