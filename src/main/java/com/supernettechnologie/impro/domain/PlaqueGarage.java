package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PlaqueGarage.
 */
@Entity
@Table(name = "plaque_garage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlaqueGarage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_ordre")
    private String numeroOrdre;

    @Column(name = "numero_plaque")
    private String numeroPlaque;

    @Column(name = "code_qr_plaque")
    private String codeQrPlaque;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @ManyToOne
    @JsonIgnoreProperties(value = "plaqueGarages", allowSetters = true)
    private CarteW carteW;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public PlaqueGarage numeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
        return this;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public String getNumeroPlaque() {
        return numeroPlaque;
    }

    public PlaqueGarage numeroPlaque(String numeroPlaque) {
        this.numeroPlaque = numeroPlaque;
        return this;
    }

    public void setNumeroPlaque(String numeroPlaque) {
        this.numeroPlaque = numeroPlaque;
    }

    public String getCodeQrPlaque() {
        return codeQrPlaque;
    }

    public PlaqueGarage codeQrPlaque(String codeQrPlaque) {
        this.codeQrPlaque = codeQrPlaque;
        return this;
    }

    public void setCodeQrPlaque(String codeQrPlaque) {
        this.codeQrPlaque = codeQrPlaque;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public PlaqueGarage createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CarteW getCarteW() {
        return carteW;
    }

    public PlaqueGarage carteW(CarteW carteW) {
        this.carteW = carteW;
        return this;
    }

    public void setCarteW(CarteW carteW) {
        this.carteW = carteW;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlaqueGarage)) {
            return false;
        }
        return id != null && id.equals(((PlaqueGarage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaqueGarage{" +
            "id=" + getId() +
            ", numeroOrdre='" + getNumeroOrdre() + "'" +
            ", numeroPlaque='" + getNumeroPlaque() + "'" +
            ", codeQrPlaque='" + getCodeQrPlaque() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
