package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.PlaqueImmatriculation} entity.
 */
@ApiModel(description = "The PlaqueImmatriculation entity.\n@author A true hipster")
public class PlaqueImmatriculationDTO implements Serializable {

    private Long id;

    /**
     * numeroSerie
     */
    @ApiModelProperty(value = "numeroSerie")
    private String numeroSerie;

    private String numeroImmatriculation;

    private String codeQR;

    private Long certificatImmatriculationId;

    private Long vehiculeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroImmatriculation() {
        return numeroImmatriculation;
    }

    public void setNumeroImmatriculation(String numeroImmatriculation) {
        this.numeroImmatriculation = numeroImmatriculation;
    }

    public String getCodeQR() {
        return codeQR;
    }

    public void setCodeQR(String codeQR) {
        this.codeQR = codeQR;
    }

    public Long getCertificatImmatriculationId() {
        return certificatImmatriculationId;
    }

    public void setCertificatImmatriculationId(Long certificatImmatriculationId) {
        this.certificatImmatriculationId = certificatImmatriculationId;
    }

    public Long getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Long vehiculeId) {
        this.vehiculeId = vehiculeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlaqueImmatriculationDTO plaqueImmatriculationDTO = (PlaqueImmatriculationDTO) o;
        if (plaqueImmatriculationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plaqueImmatriculationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlaqueImmatriculationDTO{" +
            "id=" + getId() +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", numeroImmatriculation='" + getNumeroImmatriculation() + "'" +
            ", codeQR='" + getCodeQR() + "'" +
            ", certificatImmatriculationId=" + getCertificatImmatriculationId() +
            ", vehiculeId=" + getVehiculeId() +
            "}";
    }
}
