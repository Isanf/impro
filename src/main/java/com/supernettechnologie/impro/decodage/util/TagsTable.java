package com.supernettechnologie.impro.decodage.util;

import java.util.HashMap;
import java.util.Map;

public class TagsTable {
    private Map<String, String> tagsTable = new HashMap<>();

    public TagsTable() {
        tagsTable.put("concessionnaire", "01");
        tagsTable.put("ifu", "02");
        tagsTable.put("rccm", "03");
        tagsTable.put("nom", "04");
    }

    public Map<String, String> getTagsTable() {
        return tagsTable;
    }
}
