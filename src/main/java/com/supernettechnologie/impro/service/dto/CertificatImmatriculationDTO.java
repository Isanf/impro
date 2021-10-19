package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.CertificatImmatriculation} entity.
 */
@ApiModel(description = "The CertificatImmatriculation entity.\n@author A true hipster")
public class CertificatImmatriculationDTO implements Serializable {

    private Long id;

    /**
     * numero
     */
    @ApiModelProperty(value = "numero")
    private String numero;

    private String codeQr;

    private Long carnetASoucheId;

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

    public String getCodeQr() {
        return codeQr;
    }

    public void setCodeQr(String codeQr) {
        this.codeQr = codeQr;
    }

    public Long getCarnetASoucheId() {
        return carnetASoucheId;
    }

    public void setCarnetASoucheId(Long carnetASoucheId) {
        this.carnetASoucheId = carnetASoucheId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CertificatImmatriculationDTO certificatImmatriculationDTO = (CertificatImmatriculationDTO) o;
        if (certificatImmatriculationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), certificatImmatriculationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CertificatImmatriculationDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", codeQr='" + getCodeQr() + "'" +
            ", carnetASoucheId=" + getCarnetASoucheId() +
            "}";
    }
}
