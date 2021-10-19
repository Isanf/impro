package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The TypeCarnet entity.\n@author A true hipster
 */
@Entity
@Table(name = "type_carnet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeCarnet implements Serializable {

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

    @Column(name = "quantite_certificat")
    private Long quantiteCertificat;

    @OneToMany(mappedBy = "typeCarnet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CarnetASouche> carnetSouches = new HashSet<>();

    @OneToMany(mappedBy = "typeCarnet")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InfoCommandeCarnetASouche> infoCommandeCarnetASouches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("typeCarnets")
    private TypeVehicule typeVehicule;

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

    public TypeCarnet code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeCarnet libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getQuantiteCertificat() {
        return quantiteCertificat;
    }

    public TypeCarnet quantiteCertificat(Long quantiteCertificat) {
        this.quantiteCertificat = quantiteCertificat;
        return this;
    }

    public void setQuantiteCertificat(Long quantiteCertificat) {
        this.quantiteCertificat = quantiteCertificat;
    }

    public Set<CarnetASouche> getCarnetSouches() {
        return carnetSouches;
    }

    public TypeCarnet carnetSouches(Set<CarnetASouche> carnetASouches) {
        this.carnetSouches = carnetASouches;
        return this;
    }

    public TypeCarnet addCarnetSouche(CarnetASouche carnetASouche) {
        this.carnetSouches.add(carnetASouche);
        carnetASouche.setTypeCarnet(this);
        return this;
    }

    public TypeCarnet removeCarnetSouche(CarnetASouche carnetASouche) {
        this.carnetSouches.remove(carnetASouche);
        carnetASouche.setTypeCarnet(null);
        return this;
    }

    public void setCarnetSouches(Set<CarnetASouche> carnetASouches) {
        this.carnetSouches = carnetASouches;
    }

    public Set<InfoCommandeCarnetASouche> getInfoCommandeCarnetASouches() {
        return infoCommandeCarnetASouches;
    }

    public TypeCarnet infoCommandeCarnetASouches(Set<InfoCommandeCarnetASouche> infoCommandeCarnetASouches) {
        this.infoCommandeCarnetASouches = infoCommandeCarnetASouches;
        return this;
    }

    public TypeCarnet addInfoCommandeCarnetASouche(InfoCommandeCarnetASouche infoCommandeCarnetASouche) {
        this.infoCommandeCarnetASouches.add(infoCommandeCarnetASouche);
        infoCommandeCarnetASouche.setTypeCarnet(this);
        return this;
    }

    public TypeCarnet removeInfoCommandeCarnetASouche(InfoCommandeCarnetASouche infoCommandeCarnetASouche) {
        this.infoCommandeCarnetASouches.remove(infoCommandeCarnetASouche);
        infoCommandeCarnetASouche.setTypeCarnet(null);
        return this;
    }

    public void setInfoCommandeCarnetASouches(Set<InfoCommandeCarnetASouche> infoCommandeCarnetASouches) {
        this.infoCommandeCarnetASouches = infoCommandeCarnetASouches;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public TypeCarnet typeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
        return this;
    }

    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeCarnet)) {
            return false;
        }
        return id != null && id.equals(((TypeCarnet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeCarnet{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", quantiteCertificat=" + getQuantiteCertificat() +
            "}";
    }
}
