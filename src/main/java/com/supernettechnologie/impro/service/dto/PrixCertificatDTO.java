package com.supernettechnologie.impro.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.PrixCertificat} entity.
 */
public class PrixCertificatDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long prix;

    private Boolean activated;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrixCertificatDTO)) {
            return false;
        }

        return id != null && id.equals(((PrixCertificatDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrixCertificatDTO{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
