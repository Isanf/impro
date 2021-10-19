package com.supernettechnologie.impro.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Pay} entity.
 */
public class PayDTO implements Serializable {
    
    private Long id;

    private String iso;

    private String name;

    private String nicename;

    private String iso3;

    private Integer numcode;

    private Integer phonecode;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Integer getNumcode() {
        return numcode;
    }

    public void setNumcode(Integer numcode) {
        this.numcode = numcode;
    }

    public Integer getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(Integer phonecode) {
        this.phonecode = phonecode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayDTO)) {
            return false;
        }

        return id != null && id.equals(((PayDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayDTO{" +
            "id=" + getId() +
            ", iso='" + getIso() + "'" +
            ", name='" + getName() + "'" +
            ", nicename='" + getNicename() + "'" +
            ", iso3='" + getIso3() + "'" +
            ", numcode=" + getNumcode() +
            ", phonecode=" + getPhonecode() +
            "}";
    }
}
