package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A VehiculeTraversant.
 */
@Entity
@Table(name = "vehicule_traversant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehiculeTraversant implements Serializable {

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

    @Column(name = "date_entre")
    private ZonedDateTime dateEntre;

    @Column(name = "date_sortie")
    private ZonedDateTime dateSortie;

    @Column(name = "provenance")
    private String provenance;

    @Column(name = "destination")
    private String destination;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @ManyToOne
    @JsonIgnoreProperties("vehicule_traversants")
    private PersonnePhysique personnePhysique;

    @ManyToOne
    @JsonIgnoreProperties("vehicule_traversants")
    private PersonneMorale personneMorale;

    @ManyToOne
    @JsonIgnoreProperties("vehicule_traversants")
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

    public VehiculeTraversant chassis(String chassis) {
        this.chassis = chassis;
        return this;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getMarque() {
        return marque;
    }

    public VehiculeTraversant marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public VehiculeTraversant model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ZonedDateTime getDateEntre() {
        return dateEntre;
    }

    public VehiculeTraversant dateEntre(ZonedDateTime dateEntre) {
        this.dateEntre = dateEntre;
        return this;
    }

    public void setDateEntre(ZonedDateTime dateEntre) {
        this.dateEntre = dateEntre;
    }

    public ZonedDateTime getDateSortie() {
        return dateSortie;
    }

    public VehiculeTraversant dateSortie(ZonedDateTime dateSortie) {
        this.dateSortie = dateSortie;
        return this;
    }

    public void setDateSortie(ZonedDateTime dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getProvenance() {
        return provenance;
    }

    public VehiculeTraversant provenance(String provenance) {
        this.provenance = provenance;
        return this;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getDestination() {
        return destination;
    }

    public VehiculeTraversant destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public VehiculeTraversant createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
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

    public Organisation getOrganisation() {
        return organisation;
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
        if (!(o instanceof VehiculeTraversant)) {
            return false;
        }
        return id != null && id.equals(((VehiculeTraversant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehiculeTraversant{" +
            "id=" + getId() +
            ", chassis='" + getChassis() + "'" +
            ", marque='" + getMarque() + "'" +
            ", model='" + getModel() + "'" +
            ", dateEntre='" + getDateEntre() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            ", provenance='" + getProvenance() + "'" +
            ", destination='" + getDestination() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
