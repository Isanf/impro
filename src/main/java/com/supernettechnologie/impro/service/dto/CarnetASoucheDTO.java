package com.supernettechnologie.impro.service.dto;

import com.supernettechnologie.impro.domain.TypeCarnet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.CarnetASouche} entity.
 */
@ApiModel(description = "The CarnetASouche entity.\n@author A true hipster")
public class CarnetASoucheDTO implements Serializable {

    private Long id;

    /**
     * numero
     */
    @ApiModelProperty(value = "numero")
    private String numero;

    private ZonedDateTime dateImpression;
    private ZonedDateTime dateLivraison;

    private Long concessionnaireId;

    private Long livraisonCarnetSoucheId;

    private Long typeCarnetId;

    private TypeCarnetDTO typeCarnetDTO;

    private OrganisationDTO organisationDTO;

    private LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO;

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

    public ZonedDateTime getDateImpression() {
        return dateImpression;
    }

    public void setDateImpression(ZonedDateTime dateImpression) {
        this.dateImpression = dateImpression;
    }

    public Long getConcessionnaireId() {
        return concessionnaireId;
    }

    public void setConcessionnaireId(Long organisationId) {
        this.concessionnaireId = organisationId;
    }

    public Long getLivraisonCarnetSoucheId() {
        return livraisonCarnetSoucheId;
    }

    public void setLivraisonCarnetSoucheId(Long livraisonCarnetSoucheId) {
        this.livraisonCarnetSoucheId = livraisonCarnetSoucheId;
    }

    public Long getTypeCarnetId() {
        return typeCarnetId;
    }

    public void setTypeCarnetId(Long typeCarnetId) {
        this.typeCarnetId = typeCarnetId;
    }

    public TypeCarnetDTO getTypeCarnetDTO() {
        return typeCarnetDTO;
    }

    public void setTypeCarnetDTO(TypeCarnetDTO typeCarnetDTO) {
        this.typeCarnetDTO = typeCarnetDTO;
    }

    public OrganisationDTO getOrganisationDTO() {
        return organisationDTO;
    }

    public void setOrganisationDTO(OrganisationDTO organisationDTO) {
        this.organisationDTO = organisationDTO;
    }

    public LivraisonCarnetSoucheDTO getLivraisonCarnetSoucheDTO() {
        return livraisonCarnetSoucheDTO;
    }

    public void setLivraisonCarnetSoucheDTO(LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO) {
        this.livraisonCarnetSoucheDTO = livraisonCarnetSoucheDTO;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarnetASoucheDTO)) {
            return false;
        }

        return id != null && id.equals(((CarnetASoucheDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarnetASoucheDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateImpression='" + getDateImpression() + "'" +
            ", concessionnaireId=" + getConcessionnaireId() +
            ", livraisonCarnetSoucheId=" + getLivraisonCarnetSoucheId() +
            ", typeCarnetId=" + getTypeCarnetId() +
            "}";
    }
}
