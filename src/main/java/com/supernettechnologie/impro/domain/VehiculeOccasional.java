package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A VehiculeOccasional.
 */
@Entity
@Table(name = "vehicule_occasional")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehiculeOccasional implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("vehicule_traversants")
    private CarteW carteW;


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

    public VehiculeOccasional chassis(String chassis) {
        this.chassis = chassis;
        return this;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getMarque() {
        return marque;
    }

    public VehiculeOccasional marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public VehiculeOccasional model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public VehiculeOccasional createdAt(ZonedDateTime createdAt) {
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

    public CarteW getCarteW() {
        return carteW;
    }

    public void setCarteW(CarteW carteW) {
        this.carteW = carteW;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehiculeOccasional)) {
            return false;
        }
        return id != null && id.equals(((VehiculeOccasional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehiculeOccasional{" +
            "id=" + getId() +
            ", chassis='" + getChassis() + "'" +
            ", marque='" + getMarque() + "'" +
            ", model='" + getModel() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
