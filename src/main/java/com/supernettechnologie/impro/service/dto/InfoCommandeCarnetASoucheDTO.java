package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.InfoCommandeCarnetASouche} entity.
 */
@ApiModel(description = "The InfoCommandeCarnetASouche entity.\n@author A true hipster")
public class InfoCommandeCarnetASoucheDTO implements Serializable {

    private Long id;

    /**
     * numeroCommande
     */
    @ApiModelProperty(value = "numeroCommande")
    private String numeroCommande;

    private ZonedDateTime dateCommande;

    private Long quantiteCommande;

    private Boolean estDeliver;

    private Boolean estTransiter;


    private Long commandeCarnetSoucheId;

    private Long typeCarnetId;

    private String libelle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public ZonedDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Long getQuantiteCommande() {
        return quantiteCommande;
    }

    public void setQuantiteCommande(Long quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public Boolean isEstDeliver() {
        return estDeliver;
    }

    public void setEstDeliver(Boolean estDeliver) {
        this.estDeliver = estDeliver;
    }

    public Boolean isEstTransiter() {
        return estTransiter;
    }

    public void setEstTransiter(Boolean estTransiter) {
        this.estTransiter = estTransiter;
    }

    public Long getCommandeCarnetSoucheId() {
        return commandeCarnetSoucheId;
    }

    public void setCommandeCarnetSoucheId(Long commandeCarnetSoucheId) {
        this.commandeCarnetSoucheId = commandeCarnetSoucheId;
    }


    public Long getTypeCarnetId() {
        return typeCarnetId;
    }

    public void setTypeCarnetId(Long typeCarnetId) {
        this.typeCarnetId = typeCarnetId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoCommandeCarnetASoucheDTO)) {
            return false;
        }

        return id != null && id.equals(((InfoCommandeCarnetASoucheDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoCommandeCarnetASoucheDTO{" +
            "id=" + getId() +
            ", numeroCommande='" + getNumeroCommande() + "'" +
            ", dateCommande='" + getDateCommande() + "'" +
            ", quantiteCommande=" + getQuantiteCommande() +
            ", estDeliver='" + isEstDeliver() + "'" +
            ", estTransiter='" + isEstTransiter() + "'" +
            ", commandeCarnetSoucheId=" + getCommandeCarnetSoucheId() +
            ", typeCarnetId=" + getTypeCarnetId() +
            "}";
    }
}
