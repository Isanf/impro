package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.supernettechnologie.impro.domain.enumeration.TypeCategorieOrganisation;

/**
 * A CategorieOrganisation.
 */
@Entity
@Table(name = "categorie_organisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategorieOrganisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_categorie_organisation")
    private TypeCategorieOrganisation typeCategorieOrganisation;

    @OneToMany(mappedBy = "categorieOrganisation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TypeOrganisation> types = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public CategorieOrganisation libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public CategorieOrganisation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeCategorieOrganisation getTypeCategorieOrganisation() {
        return typeCategorieOrganisation;
    }

    public CategorieOrganisation typeCategorieOrganisation(TypeCategorieOrganisation typeCategorieOrganisation) {
        this.typeCategorieOrganisation = typeCategorieOrganisation;
        return this;
    }

    public void setTypeCategorieOrganisation(TypeCategorieOrganisation typeCategorieOrganisation) {
        this.typeCategorieOrganisation = typeCategorieOrganisation;
    }

    public Set<TypeOrganisation> getTypes() {
        return types;
    }

    public CategorieOrganisation types(Set<TypeOrganisation> typeOrganisations) {
        this.types = typeOrganisations;
        return this;
    }

    public CategorieOrganisation addType(TypeOrganisation typeOrganisation) {
        this.types.add(typeOrganisation);
        typeOrganisation.setCategorieOrganisation(this);
        return this;
    }

    public CategorieOrganisation removeType(TypeOrganisation typeOrganisation) {
        this.types.remove(typeOrganisation);
        typeOrganisation.setCategorieOrganisation(null);
        return this;
    }

    public void setTypes(Set<TypeOrganisation> typeOrganisations) {
        this.types = typeOrganisations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieOrganisation)) {
            return false;
        }
        return id != null && id.equals(((CategorieOrganisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategorieOrganisation{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", typeCategorieOrganisation='" + getTypeCategorieOrganisation() + "'" +
            "}";
    }
}
