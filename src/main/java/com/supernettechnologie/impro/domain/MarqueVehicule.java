package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The MarqueVehicule entity.\n@author A true hipster
 */
@Entity
@Table(name = "marque_vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MarqueVehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * code
     */
    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "marqueVehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicule> vehicules = new HashSet<>();

    @OneToMany(mappedBy = "marqueVehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InfoCommandeVehicule> infoCommandeVehicules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public MarqueVehicule code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public MarqueVehicule libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public MarqueVehicule vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public MarqueVehicule addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.setMarqueVehicule(this);
        return this;
    }

    public MarqueVehicule removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.setMarqueVehicule(null);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Set<InfoCommandeVehicule> getInfoCommandeVehicules() {
        return infoCommandeVehicules;
    }

    public MarqueVehicule infoCommandeVehicules(Set<InfoCommandeVehicule> infoCommandeVehicules) {
        this.infoCommandeVehicules = infoCommandeVehicules;
        return this;
    }

    public MarqueVehicule addInfoCommandeVehicule(InfoCommandeVehicule infoCommandeVehicule) {
        this.infoCommandeVehicules.add(infoCommandeVehicule);
        infoCommandeVehicule.setMarqueVehicule(this);
        return this;
    }

    public MarqueVehicule removeInfoCommandeVehicule(InfoCommandeVehicule infoCommandeVehicule) {
        this.infoCommandeVehicules.remove(infoCommandeVehicule);
        infoCommandeVehicule.setMarqueVehicule(null);
        return this;
    }

    public void setInfoCommandeVehicules(Set<InfoCommandeVehicule> infoCommandeVehicules) {
        this.infoCommandeVehicules = infoCommandeVehicules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarqueVehicule)) {
            return false;
        }
        return id != null && id.equals(((MarqueVehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MarqueVehicule{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
