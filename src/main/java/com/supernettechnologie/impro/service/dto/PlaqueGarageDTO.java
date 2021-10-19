package com.supernettechnologie.impro.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.PlaqueGarage} entity.
 */
public class PlaqueGarageDTO implements Serializable {
    
    private Long id;

    private String numeroOrdre;

    private String numeroPlaque;

    private String codeQrPlaque;

    private ZonedDateTime createdAt;


    private Long carteWId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public String getNumeroPlaque() {
        return numeroPlaque;
    }

    public void setNumeroPlaque(String numeroPlaque) {
        this.numeroPlaque = numeroPlaque;
    }

    public String getCodeQrPlaque() {
        return codeQrPlaque;
    }

    public void setCodeQrPlaque(String codeQrPlaque) {
        this.codeQrPlaque = codeQrPlaque;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCarteWId() {
        return carteWId;
    }

    public void setCarteWId(Long carteWId) {
        this.carteWId = carteWId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaqueGarageDTO)) {
            return false;
        }

        return id != null && id.equals(((PlaqueGarageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaqueGarageDTO{" +
            "id=" + getId() +
            ", numeroOrdre='" + getNumeroOrdre() + "'" +
            ", numeroPlaque='" + getNumeroPlaque() + "'" +
            ", codeQrPlaque='" + getCodeQrPlaque() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", carteWId=" + getCarteWId() +
            "}";
    }
}
