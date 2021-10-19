package com.supernettechnologie.impro.decodage.domain;

public class Data {
    private String init;
    private String tagName;
    private String description;
    private Format format;
    private int length;
    private int numeroOrdre;
    private int tagLength;
    private String value;

    public Data(String tagName, String description, Format format, int length, int numeroOrdre, int tagLength, String value) {
        this.tagName = tagName;
        this.description = description;
        this.format = format;
        this.length = length;
        this.numeroOrdre = numeroOrdre;
        this.tagLength = tagLength;
        this.value = value;
    }

    public Data(String tagName, String description, Format format, int length, int numeroOrdre) {
        this.tagName = tagName;
        this.description = description;
        this.format = format;
        this.length = length;
        this.numeroOrdre = numeroOrdre;
    }

    public Data(String tagName, String description, String value,String init,Format format) {
        this.tagName = tagName;
        this.description = description;
        this.value = value;
        this.init=init;
        this.format=format;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(int numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public int getTagLength() {
        return tagLength;
    }

    public void setTagLength(int tagLength) {
        this.tagLength = tagLength;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }
}
