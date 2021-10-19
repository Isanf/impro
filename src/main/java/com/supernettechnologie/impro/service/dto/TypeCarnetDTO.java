package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.TypeCarnet} entity.
 */
@ApiModel(description = "The TypeCarnet entity.\n@author A true hipster")
public class TypeCarnetDTO implements Serializable {

    private Long id;

    /**
     * code
     */
    @ApiModelProperty(value = "code")
    private String code;

    private String libelle;

    private Long quantiteCertificat;


    private Long typeVehiculeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getQuantiteCertificat() {
        return quantiteCertificat;
    }

    public void setQuantiteCertificat(Long quantiteCertificat) {
        this.quantiteCertificat = quantiteCertificat;
    }

    public Long getTypeVehiculeId() {
        return typeVehiculeId;
    }

    public void setTypeVehiculeId(Long typeVehiculeId) {
        this.typeVehiculeId = typeVehiculeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeCarnetDTO typeCarnetDTO = (TypeCarnetDTO) o;
        if (typeCarnetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeCarnetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeCarnetDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", quantiteCertificat=" + getQuantiteCertificat() +
            ", typeVehiculeId=" + getTypeVehiculeId() +
            "}";
    }
}
