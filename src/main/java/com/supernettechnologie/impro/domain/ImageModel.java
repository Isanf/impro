package com.supernettechnologie.impro.domain;
/*package com.javainuse.model;*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "image_table")
public class ImageModel {

    public ImageModel() {
        super();
    }

    public ImageModel(String name, String type, byte[] picByte, Long orgid) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
        this.orgid = orgid;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    @Column(name = "picByte", length = 1000)
    private byte[] picByte;

    /*@OneToOne
    private Organisation Org;*/

    @Column(name = "orgid", unique=true)
    private Long orgid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

    /*public Organisation getOrg() {
        return Org;
    }

    public void setOrg(Organisation org) {
        Org = org;
    }*/

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }
}
