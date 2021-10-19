package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * The DocIdentificationPM entity.\n@author A true hipster
 */
@Entity
@Table(name = "doc_identification_pm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocIdentificationPM implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numero
     */
    @Column(name = "numero")
    private String numero;

    @Column(name = "numero_ifu", unique=true)
    private String numeroIFU;

    @Column(name = "numero_rccm")
    private String numeroRCCM;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "siege_social")
    private String siegeSocial;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private Organisation organisation;

    @OneToOne
    @JoinColumn(unique = true)
    private PersonneMorale personneMorale;

    @ManyToOne
    @JoinColumn
    private Nation nation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public DocIdentificationPM numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroIFU() {
        return numeroIFU;
    }

    public DocIdentificationPM numeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
        return this;
    }

    public void setNumeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
    }

    public String getNumeroRCCM() {
        return numeroRCCM;
    }

    public DocIdentificationPM numeroRCCM(String numeroRCCM) {
        this.numeroRCCM = numeroRCCM;
        return this;
    }

    public void setNumeroRCCM(String numeroRCCM) {
        this.numeroRCCM = numeroRCCM;
    }

    public String getTelephone() {
        return telephone;
    }

    public DocIdentificationPM telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSiegeSocial() {
        return siegeSocial;
    }

    public DocIdentificationPM siegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
        return this;
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public DocIdentificationPM codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getEmail() {
        return email;
    }

    public DocIdentificationPM email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public DocIdentificationPM organisation(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public PersonneMorale getPersonneMorale() {
        return personneMorale;
    }

    public DocIdentificationPM personneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
        return this;
    }

    public void setPersonneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocIdentificationPM)) {
            return false;
        }
        return id != null && id.equals(((DocIdentificationPM) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocIdentificationPM{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", numeroIFU='" + getNumeroIFU() + "'" +
            ", numeroRCCM='" + getNumeroRCCM() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", siegeSocial='" + getSiegeSocial() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
