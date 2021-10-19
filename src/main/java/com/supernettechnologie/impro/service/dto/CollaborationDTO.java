package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Collaboration} entity.
 */
@ApiModel(description = "The Collaboration entity.\n@author A true hipster")
public class CollaborationDTO implements Serializable {

    private Long id;

    /**
     * dateDebut
     */
    @ApiModelProperty(value = "dateDebut")
    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String numeroCollaboration;

    private Long revendeurId;

    private Long concessionnaireId;

    private OrganisationDTO concessionnaires;
    private OrganisationDTO revendeurs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getNumeroCollaboration() {
        return numeroCollaboration;
    }

    public void setNumeroCollaboration(String numeroCollaboration) {
        this.numeroCollaboration = numeroCollaboration;
    }

    public Long getRevendeurId() {
        return revendeurId;
    }

    public void setRevendeurId(Long organisationId) {
        this.revendeurId = organisationId;
    }

    public Long getConcessionnaireId() {
        return concessionnaireId;
    }

    public void setConcessionnaireId(Long organisationId) {
        this.concessionnaireId = organisationId;
    }

    public OrganisationDTO getConcessionnaires() {
        return concessionnaires;
    }

    public void setConcessionnaires(OrganisationDTO concessionnaires) {
        this.concessionnaires = concessionnaires;
    }

    public OrganisationDTO getRevendeurs() {
        return revendeurs;
    }

    public void setRevendeurs(OrganisationDTO revendeurs) {
        this.revendeurs = revendeurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollaborationDTO collaborationDTO = (CollaborationDTO) o;
        if (collaborationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collaborationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollaborationDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", numeroCollaboration='" + getNumeroCollaboration() + "'" +
            ", revendeurId=" + getRevendeurId() +
            ", concessionnaireId=" + getConcessionnaireId() +
            "}";
    }
}
