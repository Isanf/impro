package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PrixCertificat.
 */
@Entity
@Table(name = "prix_certificat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrixCertificat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Long prix;

    @Column(name = "activated")
    private Boolean activated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrix() {
        return prix;
    }

    public PrixCertificat prix(Long prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Boolean isActivated() {
        return activated;
    }

    public PrixCertificat activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrixCertificat)) {
            return false;
        }
        return id != null && id.equals(((PrixCertificat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrixCertificat{" +
            "id=" + getId() +
            ", prix=" + getPrix() +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
