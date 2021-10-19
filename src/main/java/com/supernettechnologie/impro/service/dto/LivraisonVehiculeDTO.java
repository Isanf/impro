package com.supernettechnologie.impro.service.dto;

import com.supernettechnologie.impro.repository.LivraisonCarnetSoucheRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.LivraisonVehicule} entity.
 */
@ApiModel(description = "The LivraisonVehicule entity.\n@author A true hipster")
public class LivraisonVehiculeDTO implements Serializable {

    private Long id;

    /**
     * numeroLivraison
     */
    @ApiModelProperty(value = "numeroLivraison")
    private String numeroLivraison;

    private ZonedDateTime dateLivraison;


    private Long revendeurId;

    private Long concessionnaireId;

    private Long commandeVehiculeId;

    private List<VehiculeDTO> vehiculeDTOS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroLivraison() {
        return numeroLivraison;
    }

    public void setNumeroLivraison(String numeroLivraison) {
        this.numeroLivraison = numeroLivraison;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Long getRevendeurId() {
        return revendeurId;
    }

    public void setRevendeurId(Long organisationId) {
        this.revendeurId = organisationId;
    }

    public Long getConcessionnaireId() {
        return concessionnaireId;
    }

    public void setConcessionnaireId(Long organisationId) {
        this.concessionnaireId = organisationId;
    }

    public Long getCommandeVehiculeId() {
        return commandeVehiculeId;
    }

    public void setCommandeVehiculeId(Long commandeVehiculeId) {
        this.commandeVehiculeId = commandeVehiculeId;
    }

    public List<VehiculeDTO> getVehiculeDTOS() {
        return vehiculeDTOS;
    }

    public void setVehiculeDTOS(List<VehiculeDTO> vehiculeDTOS) {
        this.vehiculeDTOS = vehiculeDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LivraisonVehiculeDTO livraisonVehiculeDTO = (LivraisonVehiculeDTO) o;
        if (livraisonVehiculeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), livraisonVehiculeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LivraisonVehiculeDTO{" +
            "id=" + getId() +
            ", numeroLivraison='" + getNumeroLivraison() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", revendeurId=" + getRevendeurId() +
            ", concessionnaireId=" + getConcessionnaireId() +
            ", commandeVehiculeId=" + getCommandeVehiculeId() +
            "}";
    }
}
