package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The CarnetASouche entity.\n@author A true hipster
 */
@Entity
@Table(name = "carnet_a_souche")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarnetASouche implements Serializable {

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

    @Column(name = "date_impression")
    private ZonedDateTime dateImpression;

    @Column(name = "date_livraison")
    private ZonedDateTime dateLivraison;

    @OneToMany(mappedBy = "carnetASouche", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CertificatImmatriculation> certificatImmatriculations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "carnetASouches", allowSetters = true)
    private Organisation concessionnaire;

    @ManyToOne
    @JsonIgnoreProperties(value = "carnetASouches", allowSetters = true)
    private LivraisonCarnetSouche livraisonCarnetSouche;

    @ManyToOne
    @JsonIgnoreProperties(value = "carnetSouches", allowSetters = true)
    private TypeCarnet typeCarnet;

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

    public CarnetASouche numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getDateImpression() {
        return dateImpression;
    }

    public CarnetASouche dateImpression(ZonedDateTime dateImpression) {
        this.dateImpression = dateImpression;
        return this;
    }

    public void setDateImpression(ZonedDateTime dateImpression) {
        this.dateImpression = dateImpression;
    }

    public Set<CertificatImmatriculation> getCertificatImmatriculations() {
        return certificatImmatriculations;
    }

    public CarnetASouche certificatImmatriculations(Set<CertificatImmatriculation> certificatImmatriculations) {
        this.certificatImmatriculations = certificatImmatriculations;
        return this;
    }

    public CarnetASouche addCertificatImmatriculation(CertificatImmatriculation certificatImmatriculation) {
        this.certificatImmatriculations.add(certificatImmatriculation);
        certificatImmatriculation.setCarnetASouche(this);
        return this;
    }

    public CarnetASouche removeCertificatImmatriculation(CertificatImmatriculation certificatImmatriculation) {
        this.certificatImmatriculations.remove(certificatImmatriculation);
        certificatImmatriculation.setCarnetASouche(null);
        return this;
    }

    public void setCertificatImmatriculations(Set<CertificatImmatriculation> certificatImmatriculations) {
        this.certificatImmatriculations = certificatImmatriculations;
    }

    public Organisation getConcessionnaire() {
        return concessionnaire;
    }

    public CarnetASouche concessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
        return this;
    }

    public void setConcessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
    }

    public LivraisonCarnetSouche getLivraisonCarnetSouche() {
        return livraisonCarnetSouche;
    }

    public CarnetASouche livraisonCarnetSouche(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCarnetSouche = livraisonCarnetSouche;
        return this;
    }

    public void setLivraisonCarnetSouche(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCarnetSouche = livraisonCarnetSouche;
    }

    public TypeCarnet getTypeCarnet() {
        return typeCarnet;
    }

    public CarnetASouche typeCarnet(TypeCarnet typeCarnet) {
        this.typeCarnet = typeCarnet;
        return this;
    }

    public void setTypeCarnet(TypeCarnet typeCarnet) {
        this.typeCarnet = typeCarnet;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarnetASouche)) {
            return false;
        }
        return id != null && id.equals(((CarnetASouche) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarnetASouche{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateImpression='" + getDateImpression() + "'" +
            "}";
    }
}
