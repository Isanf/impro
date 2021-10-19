package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The CarteW entity.\n@author A true hipster
 */
@Entity
@Table(name = "carte_w")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarteW implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroCarteW
     */
    @Column(name = "numero_carte_w")
    private String numeroCarteW;

    @Column(name = "date_etablissement_carte_w")
    private LocalDate dateEtablissementCarteW;

    @Column(name = "date_expiration_carte_w")
    private LocalDate dateExpirationCarteW;

    @Column(name = "lieu_etablissement")
    private String lieuEtablissement;

    @Column(name = "code_qr")
    private String codeQr;

    @OneToOne
    @JoinColumn(unique = true)
    private Organisation organisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCarteW() {
        return numeroCarteW;
    }

    public CarteW numeroCarteW(String numeroCarteW) {
        this.numeroCarteW = numeroCarteW;
        return this;
    }

    public void setNumeroCarteW(String numeroCarteW) {
        this.numeroCarteW = numeroCarteW;
    }

    public LocalDate getDateEtablissementCarteW() {
        return dateEtablissementCarteW;
    }

    public CarteW dateEtablissementCarteW(LocalDate dateEtablissementCarteW) {
        this.dateEtablissementCarteW = dateEtablissementCarteW;
        return this;
    }

    public void setDateEtablissementCarteW(LocalDate dateEtablissementCarteW) {
        this.dateEtablissementCarteW = dateEtablissementCarteW;
    }

    public LocalDate getDateExpirationCarteW() {
        return dateExpirationCarteW;
    }

    public CarteW dateExpirationCarteW(LocalDate dateExpirationCarteW) {
        this.dateExpirationCarteW = dateExpirationCarteW;
        return this;
    }

    public void setDateExpirationCarteW(LocalDate dateExpirationCarteW) {
        this.dateExpirationCarteW = dateExpirationCarteW;
    }

    public String getLieuEtablissement() {
        return lieuEtablissement;
    }

    public CarteW lieuEtablissement(String lieuEtablissement) {
        this.lieuEtablissement = lieuEtablissement;
        return this;
    }

    public void setLieuEtablissement(String lieuEtablissement) {
        this.lieuEtablissement = lieuEtablissement;
    }

    public String getCodeQr() {
        return codeQr;
    }

    public CarteW codeQr(String codeQr) {
        this.codeQr = codeQr;
        return this;
    }

    public void setCodeQr(String codeQr) {
        this.codeQr = codeQr;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public CarteW organisation(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteW)) {
            return false;
        }
        return id != null && id.equals(((CarteW) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteW{" +
            "id=" + getId() +
            ", numeroCarteW='" + getNumeroCarteW() + "'" +
            ", dateEtablissementCarteW='" + getDateEtablissementCarteW() + "'" +
            ", dateExpirationCarteW='" + getDateExpirationCarteW() + "'" +
            ", lieuEtablissement='" + getLieuEtablissement() + "'" +
            ", codeQr='" + getCodeQr() + "'" +
            "}";
    }
}
