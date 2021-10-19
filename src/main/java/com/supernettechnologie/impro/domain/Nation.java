package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Nation.
 */
@Entity
@Table(name = "nation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "iso")
    private String iso;

    @Column(name = "name")
    private String name;

    @Column(name = "nicename")
    private String nicename;

    @Column(name = "iso_3")
    private String iso3;

    @Column(name = "numcode")
    private Integer numcode;

    @Column(name = "phonecode")
    private Integer phonecode;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public Nation iso(String iso) {
        this.iso = iso;
        return this;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public Nation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicename() {
        return nicename;
    }

    public Nation nicename(String nicename) {
        this.nicename = nicename;
        return this;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getIso3() {
        return iso3;
    }

    public Nation iso3(String iso3) {
        this.iso3 = iso3;
        return this;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Integer getNumcode() {
        return numcode;
    }

    public Nation numcode(Integer numcode) {
        this.numcode = numcode;
        return this;
    }

    public void setNumcode(Integer numcode) {
        this.numcode = numcode;
    }

    public Integer getPhonecode() {
        return phonecode;
    }

    public Nation phonecode(Integer phonecode) {
        this.phonecode = phonecode;
        return this;
    }

    public void setPhonecode(Integer phonecode) {
        this.phonecode = phonecode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nation)) {
            return false;
        }
        return id != null && id.equals(((Nation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nation{" +
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
