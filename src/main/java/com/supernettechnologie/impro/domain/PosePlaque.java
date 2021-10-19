package com.supernettechnologie.impro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The PosePlaque entity.\n@author A true hipster
 */
@Entity
@Table(name = "pose_plaque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PosePlaque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroPose
     */
    @Column(name = "numero_pose")
    private String numeroPose;

    @Column(name = "date_pose_plaque")
    private ZonedDateTime datePosePlaque;

    @ManyToOne
    @JsonIgnoreProperties("posePlaques")
    private Organisation revendeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPose() {
        return numeroPose;
    }

    public PosePlaque numeroPose(String numeroPose) {
        this.numeroPose = numeroPose;
        return this;
    }

    public void setNumeroPose(String numeroPose) {
        this.numeroPose = numeroPose;
    }

    public ZonedDateTime getDatePosePlaque() {
        return datePosePlaque;
    }

    public PosePlaque datePosePlaque(ZonedDateTime datePosePlaque) {
        this.datePosePlaque = datePosePlaque;
        return this;
    }

    public void setDatePosePlaque(ZonedDateTime datePosePlaque) {
        this.datePosePlaque = datePosePlaque;
    }

    public Organisation getRevendeur() {
        return revendeur;
    }

    public PosePlaque revendeur(Organisation organisation) {
        this.revendeur = organisation;
        return this;
    }

    public void setRevendeur(Organisation organisation) {
        this.revendeur = organisation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PosePlaque)) {
            return false;
        }
        return id != null && id.equals(((PosePlaque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PosePlaque{" +
            "id=" + getId() +
            ", numeroPose='" + getNumeroPose() + "'" +
            ", datePosePlaque='" + getDatePosePlaque() + "'" +
            "}";
    }
}
