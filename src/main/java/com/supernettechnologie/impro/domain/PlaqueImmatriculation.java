package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * The PlaqueImmatriculation entity.\n@author A true hipster
 */
@Entity
@Table(name = "plaque_immatriculation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlaqueImmatriculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroSerie
     */
    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "numero_immatriculation")
    private String numeroImmatriculation;

    @Column(name = "code_qr")
    private String codeQR;

    @ManyToOne
    @JsonIgnoreProperties("plaqueImmatriculations")
    private CertificatImmatriculation certificatImmatriculation;

    @ManyToOne
    @JsonIgnoreProperties("plaqueImmatriculations")
    private Vehicule vehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public PlaqueImmatriculation numeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroImmatriculation() {
        return numeroImmatriculation;
    }

    public PlaqueImmatriculation numeroImmatriculation(String numeroImmatriculation) {
        this.numeroImmatriculation = numeroImmatriculation;
        return this;
    }

    public void setNumeroImmatriculation(String numeroImmatriculation) {
        this.numeroImmatriculation = numeroImmatriculation;
    }

    public String getCodeQR() {
        return codeQR;
    }

    public PlaqueImmatriculation codeQR(String codeQR) {
        this.codeQR = codeQR;
        return this;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }

    public CertificatImmatriculation getCertificatImmatriculation() {
        return certificatImmatriculation;
    }

    public PlaqueImmatriculation certificatImmatriculation(CertificatImmatriculation certificatImmatriculation) {
        this.certificatImmatriculation = certificatImmatriculation;
        return this;
    }

    public void setCertificatImmatriculation(CertificatImmatriculation certificatImmatriculation) {
        this.certificatImmatriculation = certificatImmatriculation;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public PlaqueImmatriculation vehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
        return this;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaqueImmatriculation)) {
            return false;
        }
        return id != null && id.equals(((PlaqueImmatriculation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlaqueImmatriculation{" +
            "id=" + getId() +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", numeroImmatriculation='" + getNumeroImmatriculation() + "'" +
            ", codeQR='" + getCodeQR() + "'" +
            "}";
    }
}
