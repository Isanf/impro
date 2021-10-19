package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The CertificatImmatriculation entity.\n@author A true hipster
 */
@Entity
@Table(name = "certificat_immatriculation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CertificatImmatriculation implements Serializable {

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

    @Column(name = "code_qr")
    private String codeQr;

    @OneToMany(mappedBy = "certificatImmatriculation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlaqueImmatriculation> plaqueImmatriculations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("certificatImmatriculations")
    private CarnetASouche carnetASouche;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public CertificatImmatriculation numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodeQr() {
        return codeQr;
    }

    public CertificatImmatriculation codeQr(String codeQr) {
        this.codeQr = codeQr;
        return this;
    }

    public void setCodeQr(String codeQr) {
        this.codeQr = codeQr;
    }

    public Set<PlaqueImmatriculation> getPlaqueImmatriculations() {
        return plaqueImmatriculations;
    }

    public CertificatImmatriculation plaqueImmatriculations(Set<PlaqueImmatriculation> plaqueImmatriculations) {
        this.plaqueImmatriculations = plaqueImmatriculations;
        return this;
    }

    public CertificatImmatriculation addPlaqueImmatriculation(PlaqueImmatriculation plaqueImmatriculation) {
        this.plaqueImmatriculations.add(plaqueImmatriculation);
        plaqueImmatriculation.setCertificatImmatriculation(this);
        return this;
    }

    public CertificatImmatriculation removePlaqueImmatriculation(PlaqueImmatriculation plaqueImmatriculation) {
        this.plaqueImmatriculations.remove(plaqueImmatriculation);
        plaqueImmatriculation.setCertificatImmatriculation(null);
        return this;
    }

    public void setPlaqueImmatriculations(Set<PlaqueImmatriculation> plaqueImmatriculations) {
        this.plaqueImmatriculations = plaqueImmatriculations;
    }

    public CarnetASouche getCarnetASouche() {
        return carnetASouche;
    }

    public CertificatImmatriculation carnetASouche(CarnetASouche carnetASouche) {
        this.carnetASouche = carnetASouche;
        return this;
    }

    public void setCarnetASouche(CarnetASouche carnetASouche) {
        this.carnetASouche = carnetASouche;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertificatImmatriculation)) {
            return false;
        }
        return id != null && id.equals(((CertificatImmatriculation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CertificatImmatriculation{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", codeQr='" + getCodeQr() + "'" +
            "}";
    }
}
