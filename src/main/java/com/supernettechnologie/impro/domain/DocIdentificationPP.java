package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.supernettechnologie.impro.domain.enumeration.TypeDocIdentification;

/**
 * The DocIdentification entity.\n@author A true hipster
 */
@Entity
@Table(name = "doc_identification_pp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocIdentificationPP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroDoc
     */
    @Column(name = "numero_doc")
    private String numeroDoc;

    @Column(name = "nip", unique=true)
    private String nip;

    @Column(name = "date_etablissement")
    private LocalDate dateEtablissement;

    @Column(name = "lieu_etablissement")
    private String lieuEtablissement;

    @Column(name = "autorite_emettrice")
    private String autoriteEmettrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_doc_identification")
    private TypeDocIdentification typeDocIdentification;

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

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public DocIdentificationPP numeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
        return this;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getNip() {
        return nip;
    }

    public DocIdentificationPP nip(String nip) {
        this.nip = nip;
        return this;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public LocalDate getDateEtablissement() {
        return dateEtablissement;
    }

    public DocIdentificationPP dateEtablissement(LocalDate dateEtablissement) {
        this.dateEtablissement = dateEtablissement;
        return this;
    }

    public void setDateEtablissement(LocalDate dateEtablissement) {
        this.dateEtablissement = dateEtablissement;
    }

    public String getLieuEtablissement() {
        return lieuEtablissement;
    }

    public DocIdentificationPP lieuEtablissement(String lieuEtablissement) {
        this.lieuEtablissement = lieuEtablissement;
        return this;
    }

    public void setLieuEtablissement(String lieuEtablissement) {
        this.lieuEtablissement = lieuEtablissement;
    }

    public String getAutoriteEmettrice() {
        return autoriteEmettrice;
    }

    public DocIdentificationPP autoriteEmettrice(String autoriteEmettrice) {
        this.autoriteEmettrice = autoriteEmettrice;
        return this;
    }

    public void setAutoriteEmettrice(String autoriteEmettrice) {
        this.autoriteEmettrice = autoriteEmettrice;
    }

    public TypeDocIdentification getTypeDocIdentification() {
        return typeDocIdentification;
    }

    public DocIdentificationPP typeDocIdentification(TypeDocIdentification typeDocIdentification) {
        this.typeDocIdentification = typeDocIdentification;
        return this;
    }

    public void setTypeDocIdentification(TypeDocIdentification typeDocIdentification) {
        this.typeDocIdentification = typeDocIdentification;
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
        if (!(o instanceof DocIdentificationPP)) {
            return false;
        }
        return id != null && id.equals(((DocIdentificationPP) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocIdentificationPP{" +
            "id=" + getId() +
            ", numeroDoc='" + getNumeroDoc() + "'" +
            ", nip='" + getNip() + "'" +
            ", dateEtablissement='" + getDateEtablissement() + "'" +
            ", lieuEtablissement='" + getLieuEtablissement() + "'" +
            ", autoriteEmettrice='" + getAutoriteEmettrice() + "'" +
            ", typeDocIdentification='" + getTypeDocIdentification() + "'" +
            "}";
    }
}
