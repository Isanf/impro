package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Vente} entity.
 */
@ApiModel(description = "The Vente entity.\n@author A true hipster")
public class VenteDTO implements Serializable {

    private Long id;

    /**
     * numeroVente
     */
    @ApiModelProperty(value = "numeroVente")
    private String numeroVente;
    private ZonedDateTime dateVente;
    private Integer quantiteVendue;
    private Long revendeurId;
    private Long personnePhysiqueId;
    private Long personneMoraleId;
    private PersonnePhysiqueDTO personnePhysiqueDTO;
    private PersonneMoraleDTO personneMoraleDTO;
    private DocIdentificationPPDTO docIdentificationPPDTO;
    private DocIdentificationPMDTO docIdentificationPMDTO;
    private OrganisationDTO organisationDTO;
    private VehiculeDTO vehiculeDTOStock;
    private VehiculeDTO vehiculeDTO;
    private ImmatriculationDTO immatriculationDTO;
    private PlaqueImmatriculationDTO plaqueImmatriculationDTO;
    private NationDTO nationDTO;

    public VenteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroVente() {
        return numeroVente;
    }

    public void setNumeroVente(String numeroVente) {
        this.numeroVente = numeroVente;
    }

    public ZonedDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(ZonedDateTime dateVente) {
        this.dateVente = dateVente;
    }

    public Integer getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(Integer quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public Long getRevendeurId() {
        return revendeurId;
    }

    public void setRevendeurId(Long organisationId) {
        this.revendeurId = organisationId;
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

    public OrganisationDTO getOrganisationDTO() {
        return organisationDTO;
    }

    public void setOrganisationDTO(OrganisationDTO organisationDTO) {
        this.organisationDTO = organisationDTO;
    }

    public ImmatriculationDTO getImmatriculationDTO() {
        return immatriculationDTO;
    }

    public void setImmatriculationDTO(ImmatriculationDTO immatriculationDTO) {
        this.immatriculationDTO = immatriculationDTO;
    }

    public PlaqueImmatriculationDTO getPlaqueImmatriculationDTO() {
        return plaqueImmatriculationDTO;
    }

    public void setPlaqueImmatriculationDTO(PlaqueImmatriculationDTO plaqueImmatriculationDTO) {
        this.plaqueImmatriculationDTO = plaqueImmatriculationDTO;
    }

    public VehiculeDTO getVehiculeDTOStock() {
        return vehiculeDTOStock;
    }

    public void setVehiculeDTOStock(VehiculeDTO vehiculeDTOStock) {
        this.vehiculeDTOStock = vehiculeDTOStock;
    }

    public VehiculeDTO getVehiculeDTO() {
        return vehiculeDTO;
    }

    public void setVehiculeDTO(VehiculeDTO vehiculeDTO) {
        this.vehiculeDTO = vehiculeDTO;
    }

    public NationDTO getNationDTO() {
        return nationDTO;
    }

    public void setNationDTO(NationDTO nationDTO) {
        this.nationDTO = nationDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VenteDTO venteDTO = (VenteDTO) o;
        if (venteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), venteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VenteDTO{" +
            "id=" + getId() +
            ", numeroVente='" + getNumeroVente() + "'" +
            ", dateVente='" + getDateVente() + "'" +
            ", quantiteVendue=" + getQuantiteVendue() +
            ", revendeurId=" + getRevendeurId() +
            ", personnePhysiqueId=" + getPersonnePhysiqueId() +
            ", personneMoraleId=" + getPersonneMoraleId() +
            "}";
    }
}
