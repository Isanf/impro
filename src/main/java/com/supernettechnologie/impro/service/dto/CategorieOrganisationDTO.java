package com.supernettechnologie.impro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.supernettechnologie.impro.domain.enumeration.TypeCategorieOrganisation;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.CategorieOrganisation} entity.
 */
public class CategorieOrganisationDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private String description;

    private TypeCategorieOrganisation typeCategorieOrganisation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeCategorieOrganisation getTypeCategorieOrganisation() {
        return typeCategorieOrganisation;
    }

    public void setTypeCategorieOrganisation(TypeCategorieOrganisation typeCategorieOrganisation) {
        this.typeCategorieOrganisation = typeCategorieOrganisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategorieOrganisationDTO categorieOrganisationDTO = (CategorieOrganisationDTO) o;
        if (categorieOrganisationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorieOrganisationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategorieOrganisationDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", typeCategorieOrganisation='" + getTypeCategorieOrganisation() + "'" +
            "}";
    }
}
