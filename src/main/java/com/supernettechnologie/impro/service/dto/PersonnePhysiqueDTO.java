package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.PersonnePhysique} entity.
 */
@ApiModel(description = "The PersonnePhysique entity.\n@author A true hipster")
public class PersonnePhysiqueDTO implements Serializable {

    private Long id;

    /**
     * nom
     */
    @ApiModelProperty(value = "nom")
    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private String lieuNaissance;

    private String telephone;

    private String residence;

    private boolean gerant;

    private Long docIdentificationId;

    private Long userId;

    private Long organisationId;

    private Long profilId;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public boolean isGerant() {
        return gerant;
    }

    public void setGerant(boolean gerant) {
        this.gerant = gerant;
    }

    public Long getDocIdentificationId() {
        return docIdentificationId;
    }

    public void setDocIdentificationId(Long docIdentificationPPId) {
        this.docIdentificationId = docIdentificationPPId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public Long getProfilId() {
        return profilId;
    }

    public void setProfilId(Long profilId) {
        this.profilId = profilId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonnePhysiqueDTO personnePhysiqueDTO = (PersonnePhysiqueDTO) o;
        if (personnePhysiqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personnePhysiqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonnePhysiqueDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", residence='" + getResidence() + "'" +
            ", docIdentificationId=" + getDocIdentificationId() +
            ", userId=" + getUserId() +
            ", organisationId=" + getOrganisationId() +
            ", profilId=" + getProfilId() +
            "}";
    }
}
