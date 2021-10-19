package com.supernettechnologie.impro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.TypeActeur} entity.
 */
public class TypeActeurDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @Size(max = 1000)
    private String description;


    private Set<TypeOrganisationDTO> typeOrganisations = new HashSet<>();

    private OrganisationDTO organisationDTO;

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

    public Set<TypeOrganisationDTO> getTypeOrganisations() {
        return typeOrganisations;
    }

    public void setTypeOrganisations(Set<TypeOrganisationDTO> typeOrganisations) {
        this.typeOrganisations = typeOrganisations;
    }

    public OrganisationDTO getOrganisationDTO() {
        return organisationDTO;
    }

    public void setOrganisationDTO(OrganisationDTO organisationDTO) {
        this.organisationDTO = organisationDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeActeurDTO typeActeurDTO = (TypeActeurDTO) o;
        if (typeActeurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeActeurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeActeurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
