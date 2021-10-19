package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.InfoCommandeVehicule} entity.
 */
@ApiModel(description = "The InfoCommandeVehicule entity.\n@author A true hipster")
public class InfoCommandeVehiculeDTO implements Serializable {

    private Long id;

    /**
     * numeroCommande
     */
    @ApiModelProperty(value = "numeroCommande")
    private String numeroCommande;

    private ZonedDateTime dateCommande;

    private Long quantiteCommande;


    private Long commandeVehiculeId;

    private Long marqueVehiculeId;

  /*  private CommandeVehiculeDTO commandeVehiculeDTO;*/

    Long typeVehiculeId;

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

    public Long getCommandeVehiculeId() {
        return commandeVehiculeId;
    }

    public void setCommandeVehiculeId(Long commandeVehiculeId) {
        this.commandeVehiculeId = commandeVehiculeId;
    }

    public Long getMarqueVehiculeId() {
        return marqueVehiculeId;
    }

    public void setMarqueVehiculeId(Long marqueVehiculeId) {
        this.marqueVehiculeId = marqueVehiculeId;
    }

    public Long getTypeVehiculeId() {
        return typeVehiculeId;
    }

   /* public CommandeVehiculeDTO getCommandeVehiculeDTO() {
        return commandeVehiculeDTO;
    }

    public void setCommandeVehiculeDTO(CommandeVehiculeDTO commandeVehiculeDTO) {
        this.commandeVehiculeDTO = commandeVehiculeDTO;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InfoCommandeVehiculeDTO infoCommandeVehiculeDTO = (InfoCommandeVehiculeDTO) o;
        if (infoCommandeVehiculeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), infoCommandeVehiculeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InfoCommandeVehiculeDTO{" +
            "id=" + getId() +
            ", numeroCommande='" + getNumeroCommande() + "'" +
            ", dateCommande='" + getDateCommande() + "'" +
            ", quantiteCommande=" + getQuantiteCommande() +
            ", commandeVehiculeId=" + getCommandeVehiculeId() +
            ", marqueVehiculeId=" + getMarqueVehiculeId() +
            "}";
    }
}
