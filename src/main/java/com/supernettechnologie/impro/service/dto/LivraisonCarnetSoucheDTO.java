package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.LivraisonCarnetSouche} entity.
 */
@ApiModel(description = "The LivraisonCarnetSouche entity.\n@author A true hipster")
public class LivraisonCarnetSoucheDTO implements Serializable {

    private Long id;

    /**
     * numeroLivraisonCS
     */
    @ApiModelProperty(value = "numeroLivraisonCS")
    private String numeroLivraisonCS;

    private ZonedDateTime dateLivraison;


    private Long concessionnaireId;

    private Long supernetId;

    private Long commandeCarnetSoucheId;
    private Long infosId;

    private List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTO;

    private CommandeCarnetSoucheDTO commandeCarnetSoucheDTO;

    private List<CarnetASoucheDTO> carnetASoucheDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroLivraisonCS() {
        return numeroLivraisonCS;
    }

    public void setNumeroLivraisonCS(String numeroLivraisonCS) {
        this.numeroLivraisonCS = numeroLivraisonCS;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
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

    public Long getCommandeCarnetSoucheId() {
        return commandeCarnetSoucheId;
    }

    public List<InfoCommandeCarnetASoucheDTO> getInfoCommandeCarnetASoucheDTO() {
        return infoCommandeCarnetASoucheDTO;
    }

    public void setInfoCommandeCarnetASoucheDTO(List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTO) {
        this.infoCommandeCarnetASoucheDTO = infoCommandeCarnetASoucheDTO;
    }

    public Long getInfosId() {
        return infosId;
    }

    public void setInfosId(Long infosId) {
        this.infosId = infosId;
    }

    public CommandeCarnetSoucheDTO getCommandeCarnetSoucheDTO() {
        return commandeCarnetSoucheDTO;
    }

    public void setCommandeCarnetSoucheDTO(CommandeCarnetSoucheDTO commandeCarnetSoucheDTO) {
        this.commandeCarnetSoucheDTO = commandeCarnetSoucheDTO;
    }

    public void setCommandeCarnetSoucheId(Long commandeCarnetSoucheId) {
        this.commandeCarnetSoucheId = commandeCarnetSoucheId;
    }

    public List<CarnetASoucheDTO> getCarnetASoucheDTO() {
        return carnetASoucheDTO;
    }

    public void setCarnetASoucheDTO(List<CarnetASoucheDTO> carnetASoucheDTO) {
        this.carnetASoucheDTO = carnetASoucheDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = (LivraisonCarnetSoucheDTO) o;
        if (livraisonCarnetSoucheDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), livraisonCarnetSoucheDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LivraisonCarnetSoucheDTO{" +
            "id=" + getId() +
            ", numeroLivraisonCS='" + getNumeroLivraisonCS() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", concessionnaireId=" + getConcessionnaireId() +
            ", supernetId=" + getSupernetId() +
            ", commandeCarnetSoucheId=" + getCommandeCarnetSoucheId() +
            "}";
    }
}
