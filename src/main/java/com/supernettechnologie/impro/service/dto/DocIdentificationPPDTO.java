package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;
import com.supernettechnologie.impro.domain.enumeration.TypeDocIdentification;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.DocIdentificationPP} entity.
 */
@ApiModel(description = "The DocIdentification entity.\n@author A true hipster")
public class DocIdentificationPPDTO implements Serializable {
    
    private Long id;

    /**
     * numeroDoc
     */
    @ApiModelProperty(value = "numeroDoc")
    private String numeroDoc;

    private String nip;

    private LocalDate dateEtablissement;

    private String lieuEtablissement;

    private String autoriteEmettrice;

    private TypeDocIdentification typeDocIdentification;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public LocalDate getDateEtablissement() {
        return dateEtablissement;
    }

    public void setDateEtablissement(LocalDate dateEtablissement) {
        this.dateEtablissement = dateEtablissement;
    }

    public String getLieuEtablissement() {
        return lieuEtablissement;
    }

    public void setLieuEtablissement(String lieuEtablissement) {
        this.lieuEtablissement = lieuEtablissement;
    }

    public String getAutoriteEmettrice() {
        return autoriteEmettrice;
    }

    public void setAutoriteEmettrice(String autoriteEmettrice) {
        this.autoriteEmettrice = autoriteEmettrice;
    }

    public TypeDocIdentification getTypeDocIdentification() {
        return typeDocIdentification;
    }

    public void setTypeDocIdentification(TypeDocIdentification typeDocIdentification) {
        this.typeDocIdentification = typeDocIdentification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocIdentificationPPDTO)) {
            return false;
        }

        return id != null && id.equals(((DocIdentificationPPDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocIdentificationPPDTO{" +
            "id=" + getId() +
            ", numeroDoc='" + getNumeroDoc() + "'" +
            ", nip='" + getNip() + "'" +
            ", dateEtablissement='" + getDateEtablissement() + "'" +
            ", lieuEtablissement='" + getLieuEtablissement() + "'" +
            ", autoriteEmettrice='" + getAutoriteEmettrice() + "'" +
            ", typeDocIdentification='" + getTypeDocIdentification() + "'" +
            "}";
    }
}
