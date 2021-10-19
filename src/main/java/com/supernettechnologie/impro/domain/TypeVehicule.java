package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The TypeVehicule entity.\n@author A true hipster
 */
@Entity
@Table(name = "type_vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeVehicule implements Serializable {

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

    @Column(name = "nombre_plaque")
    private Long nombrePlaque;

    @Column(name = "est_cycle_moteur")
    private Boolean estCycleMoteur;

    @OneToMany(mappedBy = "typeVehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TypeCarnet> typeCarnets = new HashSet<>();

    @OneToMany(mappedBy = "typeVehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicule> vehicules = new HashSet<>();

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

    public TypeVehicule code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeVehicule libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getNombrePlaque() {
        return nombrePlaque;
    }

    public TypeVehicule nombrePlaque(Long nombrePlaque) {
        this.nombrePlaque = nombrePlaque;
        return this;
    }

    public void setNombrePlaque(Long nombrePlaque) {
        this.nombrePlaque = nombrePlaque;
    }

    public Boolean isEstCycleMoteur() {
        return estCycleMoteur;
    }

    public TypeVehicule estCycleMoteur(Boolean estCycleMoteur) {
        this.estCycleMoteur = estCycleMoteur;
        return this;
    }

    public void setEstCycleMoteur(Boolean estCycleMoteur) {
        this.estCycleMoteur = estCycleMoteur;
    }

    public Set<TypeCarnet> getTypeCarnets() {
        return typeCarnets;
    }

    public TypeVehicule typeCarnets(Set<TypeCarnet> typeCarnets) {
        this.typeCarnets = typeCarnets;
        return this;
    }

    public TypeVehicule addTypeCarnet(TypeCarnet typeCarnet) {
        this.typeCarnets.add(typeCarnet);
        typeCarnet.setTypeVehicule(this);
        return this;
    }

    public TypeVehicule removeTypeCarnet(TypeCarnet typeCarnet) {
        this.typeCarnets.remove(typeCarnet);
        typeCarnet.setTypeVehicule(null);
        return this;
    }

    public void setTypeCarnets(Set<TypeCarnet> typeCarnets) {
        this.typeCarnets = typeCarnets;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public TypeVehicule vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public TypeVehicule addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.setTypeVehicule(this);
        return this;
    }

    public TypeVehicule removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.setTypeVehicule(null);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeVehicule)) {
            return false;
        }
        return id != null && id.equals(((TypeVehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeVehicule{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", nombrePlaque=" + getNombrePlaque() +
            ", estCycleMoteur='" + isEstCycleMoteur() + "'" +
            "}";
    }
}
