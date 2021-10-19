package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Vehicule} entity.
 */
@ApiModel(description = "The Vehicule entity.\n@author A true hipster")
public class VehiculeDTO implements Serializable {

    private Long id;

    /**
     * numeroChassis
     */
    @ApiModelProperty(value = "numeroChassis")
    private String numeroChassis;

    private String types;

    private String model;

    private String energie;

    private String puissanceReel;

    private String puissanceAdmin;

    private String couleur;

    private Integer poidsVide;

    private Integer chargeUtile;

    private Integer ptac;

    private Integer ptra;

    private Integer nbrPlace;

    private Integer capacite;

    private LocalDate dateMiseCirculation;

    private String regime;

    private String noDedouanement;

    private LocalDate dateDedouanement;


    private Long livraisonVehiculeId;

    private Long typeVehiculeId;

    private Long marqueVehiculeId;

    private Long venteId;

    private Long stockId;

    private StockDTO stockDTO;

    private TypeVehiculeDTO typeVehiculeDTO;
    private MarqueVehiculeDTO marqueVehiculeDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroChassis() {
        return numeroChassis;
    }

    public void setNumeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEnergie() {
        return energie;
    }

    public void setEnergie(String energie) {
        this.energie = energie;
    }

    public String getPuissanceReel() {
        return puissanceReel;
    }

    public void setPuissanceReel(String puissanceReel) {
        this.puissanceReel = puissanceReel;
    }

    public String getPuissanceAdmin() {
        return puissanceAdmin;
    }

    public void setPuissanceAdmin(String puissanceAdmin) {
        this.puissanceAdmin = puissanceAdmin;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Integer getPoidsVide() {
        return poidsVide;
    }

    public void setPoidsVide(Integer poidsVide) {
        this.poidsVide = poidsVide;
    }

    public Integer getChargeUtile() {
        return chargeUtile;
    }

    public void setChargeUtile(Integer chargeUtile) {
        this.chargeUtile = chargeUtile;
    }

    public Integer getPtac() {
        return ptac;
    }

    public void setPtac(Integer ptac) {
        this.ptac = ptac;
    }

    public Integer getPtra() {
        return ptra;
    }

    public void setPtra(Integer ptra) {
        this.ptra = ptra;
    }

    public Integer getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(Integer nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public LocalDate getDateMiseCirculation() {
        return dateMiseCirculation;
    }

    public void setDateMiseCirculation(LocalDate dateMiseCirculation) {
        this.dateMiseCirculation = dateMiseCirculation;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getNoDedouanement() {
        return noDedouanement;
    }

    public void setNoDedouanement(String noDedouanement) {
        this.noDedouanement = noDedouanement;
    }

    public LocalDate getDateDedouanement() {
        return dateDedouanement;
    }

    public void setDateDedouanement(LocalDate dateDedouanement) {
        this.dateDedouanement = dateDedouanement;
    }

    public Long getLivraisonVehiculeId() {
        return livraisonVehiculeId;
    }

    public void setLivraisonVehiculeId(Long livraisonVehiculeId) {
        this.livraisonVehiculeId = livraisonVehiculeId;
    }

    public Long getTypeVehiculeId() {
        return typeVehiculeId;
    }

    public void setTypeVehiculeId(Long typeVehiculeId) {
        this.typeVehiculeId = typeVehiculeId;
    }

    public Long getMarqueVehiculeId() {
        return marqueVehiculeId;
    }

    public void setMarqueVehiculeId(Long marqueVehiculeId) {
        this.marqueVehiculeId = marqueVehiculeId;
    }

    public Long getVenteId() {
        return venteId;
    }

    public void setVenteId(Long venteId) {
        this.venteId = venteId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public StockDTO getStockDTO() {
        return stockDTO;
    }

    public void setStockDTO(StockDTO stockDTO) {
        this.stockDTO = stockDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehiculeDTO vehiculeDTO = (VehiculeDTO) o;
        if (vehiculeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehiculeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehiculeDTO{" +
            "id=" + getId() +
            ", numeroChassis='" + getNumeroChassis() + "'" +
            ", types='" + getTypes() + "'" +
            ", model='" + getModel() + "'" +
            ", energie='" + getEnergie() + "'" +
            ", puissanceReel='" + getPuissanceReel() + "'" +
            ", puissanceAdmin='" + getPuissanceAdmin() + "'" +
            ", couleur='" + getCouleur() + "'" +
            ", poidsVide=" + getPoidsVide() +
            ", chargeUtile=" + getChargeUtile() +
            ", ptac=" + getPtac() +
            ", ptra=" + getPtra() +
            ", nbrPlace=" + getNbrPlace() +
            ", capacite=" + getCapacite() +
            ", dateMiseCirculation='" + getDateMiseCirculation() + "'" +
            ", regime='" + getRegime() + "'" +
            ", noDedouanement='" + getNoDedouanement() + "'" +
            ", dateDedouanement='" + getDateDedouanement() + "'" +
            ", livraisonVehiculeId=" + getLivraisonVehiculeId() +
            ", typeVehiculeId=" + getTypeVehiculeId() +
            ", marqueVehiculeId=" + getMarqueVehiculeId() +
            ", venteId=" + getVenteId() +
            ", stockId=" + getStockId() +
            "}";
    }
}
