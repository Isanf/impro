package com.supernettechnologie.impro.service.dto;

import com.supernettechnologie.impro.domain.Organisation;
import com.supernettechnologie.impro.domain.TypeCarnet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.CommandeCarnetSouche} entity.
 */
@ApiModel(description = "The CommandeCarnetSouche entity.\n@author A true hipster")
public class CommandeCarnetSoucheDTO implements Serializable {

    private Long id;

    /**
     * numeroCommandeCS
     */
    @ApiModelProperty(value = "numeroCommandeCS")
    private String numeroCommandeCS;

    private ZonedDateTime dateCommandeCS;
    private String typePaiement;

    private Boolean estValide;

    private Boolean estTraitee;

    private Boolean estLivree;

    private String prixCommande;

    private Long concessionnaireId;

    private Long supernetId;

    private List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASouches;
    private List<TypeCarnetDTO> typeCarnetDTOS;

    private OrganisationDTO organisationDTO;
    private String delaiLivraison;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommandeCS() {
        return numeroCommandeCS;
    }

    public void setNumeroCommandeCS(String numeroCommandeCS) {
        this.numeroCommandeCS = numeroCommandeCS;
    }

    public ZonedDateTime getDateCommandeCS() {
        return dateCommandeCS;
    }

    public void setDateCommandeCS(ZonedDateTime dateCommandeCS) {
        this.dateCommandeCS = dateCommandeCS;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public Boolean isEstValide() {
        return estValide;
    }

    public void setEstValide(Boolean estValide) {
        this.estValide = estValide;
    }

    public Boolean isEstTraitee() {
        return estTraitee;
    }

    public void setEstTraitee(Boolean estTraitee) {
        this.estTraitee = estTraitee;
    }

    public Boolean isEstLivree() {
        return estLivree;
    }

    public void setEstLivree(Boolean estLivree) {
        this.estLivree = estLivree;
    }

    public Long getConcessionnaireId() {
        return concessionnaireId;
    }

    public void setConcessionnaireId(Long organisationId) {
        this.concessionnaireId = organisationId;
    }

    public Long getSupernetId() {
        return supernetId;
    }

    public void setSupernetId(Long organisationId) {
        this.supernetId = organisationId;
    }

    public List<InfoCommandeCarnetASoucheDTO> getInfoCommandeCarnetASouches() {
        return infoCommandeCarnetASouches;
    }

    public void setInfoCommandeCarnetASouches(List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASouches) {
        this.infoCommandeCarnetASouches = infoCommandeCarnetASouches;
    }

    public String getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(String prixCommande) {
        this.prixCommande = prixCommande;
    }

    public OrganisationDTO getOrganisationDTO() {
        return organisationDTO;
    }

    public void setOrganisationDTO(OrganisationDTO organisationDTO) {
        this.organisationDTO = organisationDTO;
    }

    public List<TypeCarnetDTO> getTypeCarnetDTOS() {
        return typeCarnetDTOS;
    }

    public void setTypeCarnetDTOS(List<TypeCarnetDTO> typeCarnetDTOS) {
        this.typeCarnetDTOS = typeCarnetDTOS;
    }

    public String getDelaiLivraison() {
        return delaiLivraison;
    }

    public void setDelaiLivraison(String delaiLivraison) {
        this.delaiLivraison = delaiLivraison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO = (CommandeCarnetSoucheDTO) o;
        if (commandeCarnetSoucheDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commandeCarnetSoucheDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommandeCarnetSoucheDTO{" +
            "id=" + getId() +
            ", numeroCommandeCS='" + getNumeroCommandeCS() + "'" +
            ", dateCommandeCS='" + getDateCommandeCS() + "'" +
            ", estValide='" + isEstValide() + "'" +
            ", estTraitee='" + isEstTraitee() + "'" +
            ", estLivree='" + isEstLivree() + "'" +
            ", concessionnaireId=" + getConcessionnaireId() +
            ", supernetId=" + getSupernetId() +
            "}";
    }
}
