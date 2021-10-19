package com.supernettechnologie.impro.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.PersonneMorale} entity.
 */
@ApiModel(description = "The PersonneMorale entity.\n@author A true hipster")
public class PersonneMoraleDTO implements Serializable {

    private Long id;

    /**
     * numeroIFU
     */
    @ApiModelProperty(value = "numeroIFU")
    private String numeroIFU;

    private String denomination;

    private LocalDate dateCreate;

    private String telephone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroIFU() {
        return numeroIFU;
    }

    public void setNumeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonneMoraleDTO personneMoraleDTO = (PersonneMoraleDTO) o;
        if (personneMoraleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personneMoraleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonneMoraleDTO{" +
            "id=" + getId() +
            ", numeroIFU='" + getNumeroIFU() + "'" +
            ", denomination='" + getDenomination() + "'" +
            ", dateCreate='" + getDateCreate() + "'" +
            "}";
    }
}
