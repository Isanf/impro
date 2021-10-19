package com.supernettechnologie.impro.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.VehiculeTraversant} entity.
 */
public class VehiculeTraversantDTO implements Serializable {

    private Long id;

    private String chassis;

    private String marque;

    private String model;

    private ZonedDateTime dateEntre;

    private ZonedDateTime dateSortie;

    private String provenance;

    private String destination;

    private ZonedDateTime createdAt;

    private Long personnePhysiqueId;
    private Long personneMoraleId;
    private PersonnePhysiqueDTO personnePhysiqueDTO;
    private PersonneMoraleDTO personneMoraleDTO;
    private DocIdentificationPPDTO docIdentificationPPDTO;
    private DocIdentificationPMDTO docIdentificationPMDTO;
    private NationDTO nationDTO;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ZonedDateTime getDateEntre() {
        return dateEntre;
    }

    public void setDateEntre(ZonedDateTime dateEntre) {
        this.dateEntre = dateEntre;
    }

    public ZonedDateTime getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(ZonedDateTime dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
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

    public DocIdentificationPPDTO getDocIdentificationPPDTO() {
        return docIdentificationPPDTO;
    }

    public void setDocIdentificationPPDTO(DocIdentificationPPDTO docIdentificationPPDTO) {
        this.docIdentificationPPDTO = docIdentificationPPDTO;
    }

    public DocIdentificationPMDTO getDocIdentificationPMDTO() {
        return docIdentificationPMDTO;
    }

    public void setDocIdentificationPMDTO(DocIdentificationPMDTO docIdentificationPMDTO) {
        this.docIdentificationPMDTO = docIdentificationPMDTO;
    }

    public NationDTO getNationDTO() {
        return nationDTO;
    }

    public void setNationDTO(NationDTO nationDTO) {
        this.nationDTO = nationDTO;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehiculeTraversantDTO)) {
            return false;
        }

        return id != null && id.equals(((VehiculeTraversantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehiculeTraversantDTO{" +
            "id=" + getId() +
            ", chassis='" + getChassis() + "'" +
            ", marque='" + getMarque() + "'" +
            ", model='" + getModel() + "'" +
            ", dateEntre='" + getDateEntre() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            ", provenance='" + getProvenance() + "'" +
            ", destination='" + getDestination() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
