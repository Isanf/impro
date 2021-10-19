package com.supernettechnologie.impro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.TypeOrganisation} entity.
 */
public class TypeOrganisationDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @Size(max = 1000)
    private String description;

    @NotNull
    private Integer niveau;


    private Long categorieOrganisationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Long getCategorieOrganisationId() {
        return categorieOrganisationId;
    }

    public void setCategorieOrganisationId(Long categorieOrganisationId) {
        this.categorieOrganisationId = categorieOrganisationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeOrganisationDTO typeOrganisationDTO = (TypeOrganisationDTO) o;
        if (typeOrganisationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeOrganisationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeOrganisationDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", niveau=" + getNiveau() +
            ", categorieOrganisationId=" + getCategorieOrganisationId() +
            "}";
    }
}
