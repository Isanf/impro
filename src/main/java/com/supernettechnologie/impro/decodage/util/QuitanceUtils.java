package com.supernettechnologie.impro.decodage.util;


import com.supernettechnologie.impro.decodage.domain.Data;
import com.supernettechnologie.impro.decodage.domain.Format;

import java.util.ArrayList;
import java.util.List;

import static com.supernettechnologie.impro.decodage.util.Utils.getDecimal;


public class QuitanceUtils {

    private final static List<Data> HEADER = new ArrayList<>();
    private final static List<Data> DATA_STRUCTURE = new ArrayList<>();

    static {
        //Magic Constant
        HEADER.add(new Data("Magic Constant", "Identification d’un code barre conforme à la spec", Format.NO_FORMAT, 1, 1));
        HEADER.add(new Data("Version", "Un byte pour identifier la version de la spec", Format.INT, 1, 2));
        HEADER.add(new Data("Country", "Code à 3 lettres du pays encodé en C40", Format.C40, 2, 3));
        HEADER.add(new Data("Cert Auth & Ref", "Code à 9 lettres identifiant le signataire et le certificat", Format.C40, 6, 4));
        HEADER.add(new Data("Doc issue date", "Date de création", Format.DATE, 3, 5));
        HEADER.add(new Data("signature date", "Date de signature", Format.DATE, 3, 6));
        HEADER.add(new Data("Doc Feature desc", "Défini le numéro et le codage du doc", Format.INT, 1, 7));
        HEADER.add(new Data("Doc Type", "Type de doc (DL ou VR)", Format.INT, 1, 8));

        /**
         * ***********Partie corps du message*************
         */
        //
        //DATA_STRUCTURE.add(new Data("Doc number", "Numéro de document", Format.C40, getDecimal("08"), 1));
        DATA_STRUCTURE.add(new Data("Nom", "Nom Concessionnaire", Format.C40, getDecimal("0C"), 1));
//        DATA_STRUCTURE.add(new Data("dateQuitance", "Date Quitance", Format.DATE, getDecimal("08"), 2));
//        DATA_STRUCTURE.add(new Data("numeroIfu", "Le numero ifu", Format.C40, getDecimal("02"), 2));
//        DATA_STRUCTURE.add(new Data("numeroRccm", "Le numero rccm", Format.C40, getDecimal("03"), 3));
        DATA_STRUCTURE.add(new Data("personne", "Nom personne", Format.C40, getDecimal("01"), 4));

        // MESSAGE_STRUCTURE.add(new Data("Signature message", "signature electronique; ", Format.C40, getDecimal("0A"), 11));
    }

    public static List<Data> getDataStructure() {
        return DATA_STRUCTURE;
    }

    public static List<Data> getHEADER() {
        return HEADER;
    }

}
