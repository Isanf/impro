package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeActeur.
 */
@Entity
@Table(name = "type_acteur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeActeur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(mappedBy = "typeActeur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Organisation> organisations = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "type_acteur_type_organisations",
               joinColumns = @JoinColumn(name = "type_acteur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "type_organisations_id", referencedColumnName = "id"))
    private Set<TypeOrganisation> typeOrganisations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public TypeActeur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public TypeActeur description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    public TypeActeur organisations(Set<Organisation> organisations) {
        this.organisations = organisations;
        return this;
    }

    public TypeActeur addOrganisations(Organisation organisation) {
        this.organisations.add(organisation);
        organisation.setTypeActeur(this);
        return this;
    }

    public TypeActeur removeOrganisations(Organisation organisation) {
        this.organisations.remove(organisation);
        organisation.setTypeActeur(null);
        return this;
    }

    public void setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }

    public Set<TypeOrganisation> getTypeOrganisations() {
        return typeOrganisations;
    }

    public TypeActeur typeOrganisations(Set<TypeOrganisation> typeOrganisations) {
        this.typeOrganisations = typeOrganisations;
        return this;
    }

    public TypeActeur addTypeOrganisations(TypeOrganisation typeOrganisation) {
        this.typeOrganisations.add(typeOrganisation);
        typeOrganisation.getTypeActeurs().add(this);
        return this;
    }

    public TypeActeur removeTypeOrganisations(TypeOrganisation typeOrganisation) {
        this.typeOrganisations.remove(typeOrganisation);
        typeOrganisation.getTypeActeurs().remove(this);
        return this;
    }

    public void setTypeOrganisations(Set<TypeOrganisation> typeOrganisations) {
        this.typeOrganisations = typeOrganisations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeActeur)) {
            return false;
        }
        return id != null && id.equals(((TypeActeur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeActeur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
