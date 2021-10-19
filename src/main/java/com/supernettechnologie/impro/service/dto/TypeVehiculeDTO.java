package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Lob;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.TypeVehicule} entity.
 */
@ApiModel(description = "The TypeVehicule entity.\n@author A true hipster")
public class TypeVehiculeDTO implements Serializable {

    private Long id;

    /**
     * code
     */
    @ApiModelProperty(value = "code")
    private String code;

    private String libelle;

    private Long nombrePlaque;

    private Boolean estCycleMoteur;

    @Lob
    private byte[] fichierType;

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

    public Long getNombrePlaque() {
        return nombrePlaque;
    }

    public void setNombrePlaque(Long nombrePlaque) {
        this.nombrePlaque = nombrePlaque;
    }

    public Boolean isEstCycleMoteur() {
        return estCycleMoteur;
    }

    public void setEstCycleMoteur(Boolean estCycleMoteur) {
        this.estCycleMoteur = estCycleMoteur;
    }

    public Boolean getEstCycleMoteur() {
        return estCycleMoteur;
    }

    public byte[] getFichierType() {
        return fichierType;
    }

    public void setFichierType(byte[] fichierType) {
        this.fichierType = fichierType;
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

        TypeVehiculeDTO typeVehiculeDTO = (TypeVehiculeDTO) o;
        if (typeVehiculeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeVehiculeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeVehiculeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", nombrePlaque=" + getNombrePlaque() +
            ", estCycleMoteur='" + isEstCycleMoteur() + "'" +
            "}";
    }
}
