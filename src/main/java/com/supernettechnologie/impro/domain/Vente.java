package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The Vente entity.\n@author A true hipster
 */
@Entity
@Table(name = "vente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroVente
     */
    @Column(name = "numero_vente")
    private String numeroVente;

    @Column(name = "date_vente")
    private ZonedDateTime dateVente;

    @Column(name = "quantite_vendue")
    private Integer quantiteVendue;

    @OneToMany(mappedBy = "vente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicule> vehicules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ventes")
    private Organisation revendeur;

    @ManyToOne
    @JsonIgnoreProperties("ventes")
    private PersonnePhysique personnePhysique;

    @ManyToOne
    @JsonIgnoreProperties("ventes")
    private PersonneMorale personneMorale;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroVente() {
        return numeroVente;
    }

    public Vente numeroVente(String numeroVente) {
        this.numeroVente = numeroVente;
        return this;
    }

    public void setNumeroVente(String numeroVente) {
        this.numeroVente = numeroVente;
    }

    public ZonedDateTime getDateVente() {
        return dateVente;
    }

    public Vente dateVente(ZonedDateTime dateVente) {
        this.dateVente = dateVente;
        return this;
    }

    public void setDateVente(ZonedDateTime dateVente) {
        this.dateVente = dateVente;
    }

    public Integer getQuantiteVendue() {
        return quantiteVendue;
    }

    public Vente quantiteVendue(Integer quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
        return this;
    }

    public void setQuantiteVendue(Integer quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public Vente vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public Vente addVehicules(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.setVente(this);
        return this;
    }

    public Vente removeVehicules(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.setVente(null);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Organisation getRevendeur() {
        return revendeur;
    }

    public Vente revendeur(Organisation organisation) {
        this.revendeur = organisation;
        return this;
    }

    public void setRevendeur(Organisation organisation) {
        this.revendeur = organisation;
    }

    public PersonnePhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public Vente personnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
        return this;
    }

    public void setPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    public PersonneMorale getPersonneMorale() {
        return personneMorale;
    }

    public Vente personneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
        return this;
    }

    public void setPersonneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vente)) {
            return false;
        }
        return id != null && id.equals(((Vente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vente{" +
            "id=" + getId() +
            ", numeroVente='" + getNumeroVente() + "'" +
            ", dateVente='" + getDateVente() + "'" +
            ", quantiteVendue=" + getQuantiteVendue() +
            "}";
    }
}
