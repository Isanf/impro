package com.supernettechnologie.impro.decodage.util;

import java.util.HashMap;
import java.util.Map;

public class C40Table {
    private Map<String, Integer> c40_table = new HashMap<>();

    public C40Table() {
        c40_table.put("SHIFT_1", 0);
        c40_table.put("SHIFT_2", 1);
        c40_table.put("SHIFT_3", 2);
        c40_table.put("SPACE", 3);
        c40_table.put("0", 4);
        c40_table.put("1", 5);
        c40_table.put("2", 6);
        c40_table.put("3", 7);
        c40_table.put("4", 8);
        c40_table.put("5", 9);
        c40_table.put("6", 10);
        c40_table.put("7", 11);
        c40_table.put("8", 12);
        c40_table.put("9", 13);
        c40_table.put("A", 14);
        c40_table.put("B", 15);
        c40_table.put("C", 16);
        c40_table.put("D", 17);
        c40_table.put("E", 18);
        c40_table.put("F", 19);
        c40_table.put("G", 20);
        c40_table.put("H", 21);
        c40_table.put("I", 22);
        c40_table.put("J", 23);
        c40_table.put("K", 24);
        c40_table.put("L", 25);
        c40_table.put("M", 26);
        c40_table.put("N", 27);
        c40_table.put("O", 28);
        c40_table.put("P", 29);
        c40_table.put("Q", 30);
        c40_table.put("R", 31);
        c40_table.put("S", 32);
        c40_table.put("T", 33);
        c40_table.put("U", 34);
        c40_table.put("V", 35);
        c40_table.put("W", 36);
        c40_table.put("X", 37);
        c40_table.put("Y", 38);
        c40_table.put("Z", 39);
        c40_table.put(".", 40);
        c40_table.put("_", 41);
        c40_table.put("-", 42);
        c40_table.put("(", 43);
        c40_table.put(")", 44);
        c40_table.put("/", 45);
        c40_table.put("\"", 46);
        c40_table.put("'", 47);
    }

    public Map<String, Integer> getC40_table() {
        return c40_table;
    }
}
