package com.supernettechnologie.impro.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Immatriculation} entity.
 */
public class ImmatriculationDTO implements Serializable {

    private Long id;

    private String numero;

    private ZonedDateTime dateImmatriculation;

    private Long certificatImmatriculationId;

    private Long organisationId;

    private Long personnePhysiqueId;

    private Long personneMoraleId;

    private Long vehiculeId;
    private PersonnePhysiqueDTO personnePhysiqueDTO;
    private PersonneMoraleDTO personneMoraleDTO;
    private VehiculeDTO vehiculeDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getDateImmatriculation() {
        return dateImmatriculation;
    }

    public void setDateImmatriculation(ZonedDateTime dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
    }

    public Long getCertificatImmatriculationId() {
        return certificatImmatriculationId;
    }

    public void setCertificatImmatriculationId(Long certificatImmatriculationId) {
        this.certificatImmatriculationId = certificatImmatriculationId;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public Long getPersonnePhysiqueId() {
        return personnePhysiqueId;
    }

    public void setPersonnePhysiqueId(Long personnePhysiqueId) {
        this.personnePhysiqueId = personnePhysiqueId;
    }

    public Long getPersonneMoraleId() {
        return personneMoraleId;
    }

    public void setPersonneMoraleId(Long personneMoraleId) {
        this.personneMoraleId = personneMoraleId;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    public PersonnePhysiqueDTO getPersonnePhysiqueDTO() {
        return personnePhysiqueDTO;
    }

    public void setPersonnePhysiqueDTO(PersonnePhysiqueDTO personnePhysiqueDTO) {
        this.personnePhysiqueDTO = personnePhysiqueDTO;
    }

    public PersonneMoraleDTO getPersonneMoraleDTO() {
        return personneMoraleDTO;
    }

    public void setPersonneMoraleDTO(PersonneMoraleDTO personneMoraleDTO) {
        this.personneMoraleDTO = personneMoraleDTO;
    }

    public VehiculeDTO getVehiculeDTO() {
        return vehiculeDTO;
    }

    public void setVehiculeDTO(VehiculeDTO vehiculeDTO) {
        this.vehiculeDTO = vehiculeDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImmatriculationDTO immatriculationDTO = (ImmatriculationDTO) o;
        if (immatriculationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), immatriculationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImmatriculationDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateImmatriculation='" + getDateImmatriculation() + "'" +
            ", certificatImmatriculationId=" + getCertificatImmatriculationId() +
            ", organisationId=" + getOrganisationId() +
            ", personnePhysiqueId=" + getPersonnePhysiqueId() +
            ", personneMoraleId=" + getPersonneMoraleId() +
            ", vehiculeId=" + getVehiculeId() +
            "}";
    }
}
