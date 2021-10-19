package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.CarteW} entity.
 */
@ApiModel(description = "The CarteW entity.\n@author A true hipster")
public class CarteWDTO implements Serializable {

    private Long id;

    /**
     * numeroCarteW
     */
    @ApiModelProperty(value = "numeroCarteW")
    private String numeroCarteW;

    private LocalDate dateEtablissementCarteW;

    private LocalDate dateExpirationCarteW;

    private String lieuEtablissement;
    private String codeQr;


    private Long organisationId;

    private OrganisationDTO organisationDTO;

    private DocIdentificationPMDTO docIdentificationPMDTO;

    private PersonnePhysiqueDTO personnePhysiqueDTO;

    private DocIdentificationPPDTO docIdentificationPPDTO;

    private ProfilDTO profilDTO;

    private UserDTO userDTO;
    private PlaqueGarageDTO plaqueGarageDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCarteW() {
        return numeroCarteW;
    }

    public void setNumeroCarteW(String numeroCarteW) {
        this.numeroCarteW = numeroCarteW;
    }

    public LocalDate getDateEtablissementCarteW() {
        return dateEtablissementCarteW;
    }

    public void setDateEtablissementCarteW(LocalDate dateEtablissementCarteW) {
        this.dateEtablissementCarteW = dateEtablissementCarteW;
    }

    public LocalDate getDateExpirationCarteW() {
        return dateExpirationCarteW;
    }

    public void setDateExpirationCarteW(LocalDate dateExpirationCarteW) {
        this.dateExpirationCarteW = dateExpirationCarteW;
    }

    public String getLieuEtablissement() {
        return lieuEtablissement;
    }

    public void setLieuEtablissement(String lieuEtablissement) {
        this.lieuEtablissement = lieuEtablissement;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public DocIdentificationPMDTO getDocIdentificationPMDTO() {
        return docIdentificationPMDTO;
    }

    public void setDocIdentificationPMDTO(DocIdentificationPMDTO docIdentificationPMDTO) {
        this.docIdentificationPMDTO = docIdentificationPMDTO;
    }

    public String getCodeQr() {
        return codeQr;
    }

    public void setCodeQr(String codeQr) {
        this.codeQr = codeQr;
    }

    public OrganisationDTO getOrganisationDTO() {
        return organisationDTO;
    }

    public void setOrganisationDTO(OrganisationDTO organisationDTO) {
        this.organisationDTO = organisationDTO;
    }


    public PersonnePhysiqueDTO getPersonnePhysiqueDTO() {
        return personnePhysiqueDTO;
    }

    public void setPersonnePhysiqueDTO(PersonnePhysiqueDTO personnePhysiqueDTO) {
        this.personnePhysiqueDTO = personnePhysiqueDTO;
    }

    public DocIdentificationPPDTO getDocIdentificationPPDTO() {
        return docIdentificationPPDTO;
    }

    public void setDocIdentificationPPDTO(DocIdentificationPPDTO docIdentificationPPDTO) {
        this.docIdentificationPPDTO = docIdentificationPPDTO;
    }

    public ProfilDTO getProfilDTO() {
        return profilDTO;
    }

    public void setProfilDTO(ProfilDTO profilDTO) {
        this.profilDTO = profilDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public PlaqueGarageDTO getPlaqueGarageDTO() {
        return plaqueGarageDTO;
    }

    public void setPlaqueGarageDTO(PlaqueGarageDTO plaqueGarageDTO) {
        this.plaqueGarageDTO = plaqueGarageDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarteWDTO carteWDTO = (CarteWDTO) o;
        if (carteWDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carteWDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarteWDTO{" +
            "id=" + getId() +
            ", numeroCarteW='" + getNumeroCarteW() + "'" +
            ", dateEtablissementCarteW='" + getDateEtablissementCarteW() + "'" +
            ", dateExpirationCarteW='" + getDateExpirationCarteW() + "'" +
            ", lieuEtablissement='" + getLieuEtablissement() + "'" +
            ", organisationId=" + getOrganisationId() +
            "}";
    }
}
