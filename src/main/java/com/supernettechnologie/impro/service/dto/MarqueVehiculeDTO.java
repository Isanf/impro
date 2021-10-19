package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Lob;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.MarqueVehicule} entity.
 */
@ApiModel(description = "The MarqueVehicule entity.\n@author A true hipster")
public class MarqueVehiculeDTO implements Serializable {

    private Long id;

    /**
     * code
     */
    @ApiModelProperty(value = "code")
    private String code;

    private String libelle;

    @Lob
    private byte[] fichierMarque;

    private File file;


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

    public byte[] getFichierMarque() {
        return fichierMarque;
    }

    public void setFichierMarque(byte[] fichierMarque) {
        this.fichierMarque = fichierMarque;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MarqueVehiculeDTO marqueVehiculeDTO = (MarqueVehiculeDTO) o;
        if (marqueVehiculeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marqueVehiculeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarqueVehiculeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
