package com.supernettechnologie.impro.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The PersonneMorale entity.\n@author A true hipster
 */
@Entity
@Table(name = "personne_morale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonneMorale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroIFU
     */
    @Column(name = "numero_ifu")
    private String numeroIFU;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "date_create")
    private LocalDate dateCreate;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(mappedBy = "personneMorale")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Immatriculation> immatriculations = new HashSet<>();

    @OneToMany(mappedBy = "personneMorale")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vente> ventes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroIFU() {
        return numeroIFU;
    }

    public PersonneMorale numeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
        return this;
    }

    public void setNumeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
    }

    public String getDenomination() {
        return denomination;
    }

    public PersonneMorale denomination(String denomination) {
        this.denomination = denomination;
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public PersonneMorale dateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
        return this;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Set<Immatriculation> getImmatriculations() {
        return immatriculations;
    }

    public PersonneMorale immatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
        return this;
    }

    public PersonneMorale addImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.add(immatriculation);
        immatriculation.setPersonneMorale(this);
        return this;
    }

    public PersonneMorale removeImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.remove(immatriculation);
        immatriculation.setPersonneMorale(null);
        return this;
    }

    public void setImmatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
    }

    public Set<Vente> getVentes() {
        return ventes;
    }

    public PersonneMorale ventes(Set<Vente> ventes) {
        this.ventes = ventes;
        return this;
    }

    public PersonneMorale addVente(Vente vente) {
        this.ventes.add(vente);
        vente.setPersonneMorale(this);
        return this;
    }

    public PersonneMorale removeVente(Vente vente) {
        this.ventes.remove(vente);
        vente.setPersonneMorale(null);
        return this;
    }

    public void setVentes(Set<Vente> ventes) {
        this.ventes = ventes;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonneMorale)) {
            return false;
        }
        return id != null && id.equals(((PersonneMorale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonneMorale{" +
            "id=" + getId() +
            ", numeroIFU='" + getNumeroIFU() + "'" +
            ", denomination='" + getDenomination() + "'" +
            ", dateCreate='" + getDateCreate() + "'" +
            "}";
    }
}
