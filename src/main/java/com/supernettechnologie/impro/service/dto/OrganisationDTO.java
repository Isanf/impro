package com.supernettechnologie.impro.service.dto;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Organisation} entity.
 */
public class OrganisationDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @Size(max = 1000)
    private String description;


    private Integer numeroOrdre;

    private Long organisationLocaliteId;

    private Long pereId;

    private Long typeOrganisationId;

    private Long gerantId;

    private Long typeActeurId;

    private DocIdentificationPMDTO docIdentificationPMDTO;

    private PersonnePhysiqueDTO personnePhysiqueDTO;

    private DocIdentificationPPDTO docIdentificationPPDTO;

    private ProfilDTO profilDTO;

    private List<OrganisationDTO> organisationDTOS;

    private DocIdentificationPMDTO docIdPM;

    private String numeroPhone;

    @Lob
    private byte[] fichierSign;

    @Lob
    private byte[] fichierLogo;

    private String fichierSignContentType;

    private String fichierLogoContentType;

    private OrganisationLocaliteDTO organisationlocaliteDTO;
    private String acteur;

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

    public Integer getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(Integer numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public Long getOrganisationLocaliteId() {
        return organisationLocaliteId;
    }

    public void setOrganisationLocaliteId(Long organisationLocaliteId) {
        this.organisationLocaliteId = organisationLocaliteId;
    }

    public Long getPereId() {
        return pereId;
    }

    public void setPereId(Long organisationId) {
        this.pereId = organisationId;
    }

    public Long getTypeOrganisationId() {
        return typeOrganisationId;
    }

    public void setTypeOrganisationId(Long typeOrganisationId) {
        this.typeOrganisationId = typeOrganisationId;
    }

    public Long getGerantId() {
        return gerantId;
    }

    public void setGerantId(Long personnePhysiqueId) {
        this.gerantId = personnePhysiqueId;
    }

    public Long getTypeActeurId() {
        return typeActeurId;
    }

    public void setTypeActeurId(Long typeActeurId) {
        this.typeActeurId = typeActeurId;
    }

    public DocIdentificationPMDTO getDocIdentificationPMDTO() {
        return docIdentificationPMDTO;
    }

    public void setDocIdentificationPMDTO(DocIdentificationPMDTO docIdentificationPMDTO) {
        this.docIdentificationPMDTO = docIdentificationPMDTO;
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

    public List<OrganisationDTO> getOrganisationDTOS() {
        return organisationDTOS;
    }

    public void setOrganisationDTOS(List<OrganisationDTO> organisationDTOS) {
        this.organisationDTOS = organisationDTOS;
    }

    public DocIdentificationPMDTO getDocIdPM() {
        return docIdPM;
    }

    public void setDocIdPM(DocIdentificationPMDTO docIdPM) {
        this.docIdPM = docIdPM;
    }

    public String getNumeroPhone() {
        return numeroPhone;
    }

    public void setNumeroPhone(String numeroPhone) {
        this.numeroPhone = numeroPhone;
    }

    public byte[] getFichierSign() {
        return fichierSign;
    }

    public void setFichierSign(byte[] fichierSign) {
        this.fichierSign = fichierSign;
    }

    public byte[] getFichierLogo() {
        return fichierLogo;
    }

    public void setFichierLogo(byte[] fichierLogo) {
        this.fichierLogo = fichierLogo;
    }

    public String getFichierSignContentType() {
        return fichierSignContentType;
    }

    public void setFichierSignContentType(String fichierSignContentType) {
        this.fichierSignContentType = fichierSignContentType;
    }

    public String getFichierLogoContentType() {
        return fichierLogoContentType;
    }

    public void setFichierLogoContentType(String fichierLogoContentType) {
        this.fichierLogoContentType = fichierLogoContentType;
    }

    public OrganisationLocaliteDTO getOrganisationlocaliteDTO() {
        return organisationlocaliteDTO;
    }

    public void setOrganisationlocaliteDTO(OrganisationLocaliteDTO organisationlocaliteDTO) {
        this.organisationlocaliteDTO = organisationlocaliteDTO;
    }

    public String getActeur() {
        return acteur;
    }

    public void setActeur(String acteur) {
        this.acteur = acteur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganisationDTO organisationDTO = (OrganisationDTO) o;
        if (organisationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organisationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganisationDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", numeroOrdre=" + getNumeroOrdre() +
            ", organisationLocaliteId=" + getOrganisationLocaliteId() +
            ", pereId=" + getPereId() +
            ", typeOrganisationId=" + getTypeOrganisationId() +
            ", gerantId=" + getGerantId() +
            ", typeActeurId=" + getTypeActeurId() +
            "}";
    }
}
