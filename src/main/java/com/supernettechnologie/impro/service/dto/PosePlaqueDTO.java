package com.supernettechnologie.impro.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.PosePlaque} entity.
 */
@ApiModel(description = "The PosePlaque entity.\n@author A true hipster")
public class PosePlaqueDTO implements Serializable {

    private Long id;

    /**
     * numeroPose
     */
    @ApiModelProperty(value = "numeroPose")
    private String numeroPose;

    private ZonedDateTime datePosePlaque;


    private Long revendeurId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPose() {
        return numeroPose;
    }

    public void setNumeroPose(String numeroPose) {
        this.numeroPose = numeroPose;
    }

    public ZonedDateTime getDatePosePlaque() {
        return datePosePlaque;
    }

    public void setDatePosePlaque(ZonedDateTime datePosePlaque) {
        this.datePosePlaque = datePosePlaque;
    }

    public Long getRevendeurId() {
        return revendeurId;
    }

    public void setRevendeurId(Long organisationId) {
        this.revendeurId = organisationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PosePlaqueDTO posePlaqueDTO = (PosePlaqueDTO) o;
        if (posePlaqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), posePlaqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PosePlaqueDTO{" +
            "id=" + getId() +
            ", numeroPose='" + getNumeroPose() + "'" +
            ", datePosePlaque='" + getDatePosePlaque() + "'" +
            ", revendeur=" + getRevendeurId() +
            "}";
    }
}
