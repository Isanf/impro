package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeOrganisation.
 */
@Entity
@Table(name = "type_organisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeOrganisation implements Serializable {

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

    @NotNull
    @Column(name = "niveau", nullable = false)
    private Integer niveau;

    @OneToMany(mappedBy = "typeOrganisation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Organisation> organisations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("types")
    private CategorieOrganisation categorieOrganisation;

    @ManyToMany(mappedBy = "typeOrganisations")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TypeActeur> typeActeurs = new HashSet<>();

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

    public TypeOrganisation nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public TypeOrganisation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public TypeOrganisation niveau(Integer niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    public TypeOrganisation organisations(Set<Organisation> organisations) {
        this.organisations = organisations;
        return this;
    }

    public TypeOrganisation addOrganisations(Organisation organisation) {
        this.organisations.add(organisation);
        organisation.setTypeOrganisation(this);
        return this;
    }

    public TypeOrganisation removeOrganisations(Organisation organisation) {
        this.organisations.remove(organisation);
        organisation.setTypeOrganisation(null);
        return this;
    }

    public void setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }

    public CategorieOrganisation getCategorieOrganisation() {
        return categorieOrganisation;
    }

    public TypeOrganisation categorieOrganisation(CategorieOrganisation categorieOrganisation) {
        this.categorieOrganisation = categorieOrganisation;
        return this;
    }

    public void setCategorieOrganisation(CategorieOrganisation categorieOrganisation) {
        this.categorieOrganisation = categorieOrganisation;
    }

    public Set<TypeActeur> getTypeActeurs() {
        return typeActeurs;
    }

    public TypeOrganisation typeActeurs(Set<TypeActeur> typeActeurs) {
        this.typeActeurs = typeActeurs;
        return this;
    }

    public TypeOrganisation addTypeActeurs(TypeActeur typeActeur) {
        this.typeActeurs.add(typeActeur);
        typeActeur.getTypeOrganisations().add(this);
        return this;
    }

    public TypeOrganisation removeTypeActeurs(TypeActeur typeActeur) {
        this.typeActeurs.remove(typeActeur);
        typeActeur.getTypeOrganisations().remove(this);
        return this;
    }

    public void setTypeActeurs(Set<TypeActeur> typeActeurs) {
        this.typeActeurs = typeActeurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeOrganisation)) {
            return false;
        }
        return id != null && id.equals(((TypeOrganisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeOrganisation{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", niveau=" + getNiveau() +
            "}";
    }
}
