package com.supernettechnologie.impro.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.VehiculeOccasional} entity.
 */
public class VehiculeOccasionalDTO implements Serializable {

    private Long id;

    private String chassis;

    private String marque;

    private String model;

    private ZonedDateTime createdAt;

    private Long personnePhysiqueId;
    private Long personneMoraleId;
    private Long carteWId;
    private PersonnePhysiqueDTO personnePhysiqueDTO;
    private PersonneMoraleDTO personneMoraleDTO;
    private DocIdentificationPPDTO docIdentificationPPDTO;
    private DocIdentificationPMDTO docIdentificationPMDTO;
    private NationDTO nationDTO;
    private CarteWDTO carteWDTO;


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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
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

    public CarteWDTO getCarteWDTO() {
        return carteWDTO;
    }

    public void setCarteWDTO(CarteWDTO carteWDTO) {
        this.carteWDTO = carteWDTO;
    }

    public Long getCarteWId() {
        return carteWId;
    }

    public void setCarteWId(Long carteWId) {
        this.carteWId = carteWId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehiculeOccasionalDTO)) {
            return false;
        }

        return id != null && id.equals(((VehiculeOccasionalDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehiculeOccasionalDTO{" +
            "id=" + getId() +
            ", chassis='" + getChassis() + "'" +
            ", marque='" + getMarque() + "'" +
            ", model='" + getModel() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
