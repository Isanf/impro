package com.supernettechnologie.impro.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Statistique} entity.
 */
public class StatistiqueDTO implements Serializable {
    
    private Long id;

    private String nom;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatistiqueDTO)) {
            return false;
        }

        return id != null && id.equals(((StatistiqueDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatistiqueDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
