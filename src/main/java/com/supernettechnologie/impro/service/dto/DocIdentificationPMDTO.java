package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.DocIdentificationPM} entity.
 */
@ApiModel(description = "The DocIdentificationPM entity.\n@author A true hipster")
public class DocIdentificationPMDTO implements Serializable {

    private Long id;

    /**
     * numero
     */
    @ApiModelProperty(value = "numero")
    private String numero;

    private String numeroIFU;

    private String numeroRCCM;

    private String telephone;

    private String siegeSocial;

    private String codePostal;

    private String email;

    private Long organisationId;

    private Long personneMoraleId;

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

    public String getNumeroIFU() {
        return numeroIFU;
    }

    public void setNumeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
    }

    public String getNumeroRCCM() {
        return numeroRCCM;
    }

    public void setNumeroRCCM(String numeroRCCM) {
        this.numeroRCCM = numeroRCCM;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSiegeSocial() {
        return siegeSocial;
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public Long getPersonneMoraleId() {
        return personneMoraleId;
    }

    public void setPersonneMoraleId(Long personneMoraleId) {
        this.personneMoraleId = personneMoraleId;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocIdentificationPMDTO docIdentificationPMDTO = (DocIdentificationPMDTO) o;
        if (docIdentificationPMDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), docIdentificationPMDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocIdentificationPMDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", numeroIFU='" + getNumeroIFU() + "'" +
            ", numeroRCCM='" + getNumeroRCCM() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", siegeSocial='" + getSiegeSocial() + "'" +
            ", organisationId=" + getOrganisationId() +
            ", personneMoraleId=" + getPersonneMoraleId() +
            "}";
    }
}
