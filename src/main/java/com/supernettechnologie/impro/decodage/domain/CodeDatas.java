package com.supernettechnologie.impro.decodage.domain;

import java.util.List;

public class CodeDatas {
    private List<Data> header;
    private List<Data> message;
    private String signature;
    private String datas;
    private boolean result;

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public List<Data> getHeader() {
        return header;
    }

    public void setHeader(List<Data> header) {
        this.header = header;
    }

    public List<Data> getMessage() {
        return message;
    }

    public void setMessage(List<Data> message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
