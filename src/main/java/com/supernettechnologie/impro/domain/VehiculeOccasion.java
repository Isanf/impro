package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A VehiculeOccasion.
 */
@Entity
@Table(name = "vehicule_occasion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehiculeOccasion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "chassis")
    private String chassis;

    @Column(name = "marque")
    private String marque;

    @Column(name = "model")
    private String model;

    @Column(name = "nom_prenom")
    private String nomPrenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "carte_w")
    private String carteW;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "numero_cnib")
    private String numeroCNIB;

    @ManyToOne
    private PersonnePhysique personnePhysique;

    @ManyToOne
    private PersonneMorale personneMorale;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "vehiculeOccasions", allowSetters = true)
    private Organisation organisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChassis() {
        return chassis;
    }

    public VehiculeOccasion chassis(String chassis) {
        this.chassis = chassis;
        return this;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getMarque() {
        return marque;
    }

    public VehiculeOccasion marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public VehiculeOccasion model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public VehiculeOccasion nomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
        return this;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public VehiculeOccasion telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCarteW() {
        return carteW;
    }

    public VehiculeOccasion carteW(String carteW) {
        this.carteW = carteW;
        return this;
    }

    public void setCarteW(String carteW) {
        this.carteW = carteW;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public VehiculeOccasion createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNumeroCNIB() {
        return numeroCNIB;
    }

    public VehiculeOccasion numeroCNIB(String numeroCNIB) {
        this.numeroCNIB = numeroCNIB;
        return this;
    }

    public void setNumeroCNIB(String numeroCNIB) {
        this.numeroCNIB = numeroCNIB;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public VehiculeOccasion organisation(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public PersonnePhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public void setPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    public PersonneMorale getPersonneMorale() {
        return personneMorale;
    }

    public void setPersonneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehiculeOccasion)) {
            return false;
        }
        return id != null && id.equals(((VehiculeOccasion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehiculeOccasion{" +
            "id=" + getId() +
            ", chassis='" + getChassis() + "'" +
            ", marque='" + getMarque() + "'" +
            ", model='" + getModel() + "'" +
            ", nomPrenom='" + getNomPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", carteW='" + getCarteW() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", numeroCNIB='" + getNumeroCNIB() + "'" +
            "}";
    }
}
