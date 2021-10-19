package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The Collaboration entity.\n@author A true hipster
 */
@Entity
@Table(name = "collaboration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Collaboration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * dateDebut
     */
    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "numero_collaboration")
    private String numeroCollaboration;

    @ManyToOne
    @JsonIgnoreProperties("collaborationsRevendeurs")
    private Organisation revendeur;

    @ManyToOne
    @JsonIgnoreProperties("collaborationsConcessionnaires")
    private Organisation concessionnaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Collaboration dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Collaboration dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getNumeroCollaboration() {
        return numeroCollaboration;
    }

    public Collaboration numeroCollaboration(String numeroCollaboration) {
        this.numeroCollaboration = numeroCollaboration;
        return this;
    }

    public void setNumeroCollaboration(String numeroCollaboration) {
        this.numeroCollaboration = numeroCollaboration;
    }

    public Organisation getRevendeur() {
        return revendeur;
    }

    public Collaboration revendeur(Organisation organisation) {
        this.revendeur = organisation;
        return this;
    }

    public void setRevendeur(Organisation organisation) {
        this.revendeur = organisation;
    }

    public Organisation getConcessionnaire() {
        return concessionnaire;
    }

    public Collaboration concessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
        return this;
    }

    public void setConcessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaboration)) {
            return false;
        }
        return id != null && id.equals(((Collaboration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Collaboration{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", numeroCollaboration='" + getNumeroCollaboration() + "'" +
            "}";
    }
}
