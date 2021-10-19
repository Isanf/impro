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
 * The LivraisonVehicule entity.\n@author A true hipster
 */
@Entity
@Table(name = "livraison_vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LivraisonVehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroLivraison
     */
    @Column(name = "numero_livraison")
    private String numeroLivraison;

    @Column(name = "date_livraison")
    private ZonedDateTime dateLivraison;

    @OneToMany(mappedBy = "livraisonVehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicule> vehicules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("livraisonVRevendeurs")
    private Organisation revendeur;

    @ManyToOne
    @JsonIgnoreProperties("livraisonVConcessionnaires")
    private Organisation concessionnaire;

    @ManyToOne
    @JsonIgnoreProperties("livraisonVehicules")
    private CommandeVehicule commandeVehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroLivraison() {
        return numeroLivraison;
    }

    public LivraisonVehicule numeroLivraison(String numeroLivraison) {
        this.numeroLivraison = numeroLivraison;
        return this;
    }

    public void setNumeroLivraison(String numeroLivraison) {
        this.numeroLivraison = numeroLivraison;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public LivraisonVehicule dateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
        return this;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public LivraisonVehicule vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public LivraisonVehicule addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.setLivraisonVehicule(this);
        return this;
    }

    public LivraisonVehicule removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.setLivraisonVehicule(null);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Organisation getRevendeur() {
        return revendeur;
    }

    public LivraisonVehicule revendeur(Organisation organisation) {
        this.revendeur = organisation;
        return this;
    }

    public void setRevendeur(Organisation organisation) {
        this.revendeur = organisation;
    }

    public Organisation getConcessionnaire() {
        return concessionnaire;
    }

    public LivraisonVehicule concessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
        return this;
    }

    public void setConcessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
    }

    public CommandeVehicule getCommandeVehicule() {
        return commandeVehicule;
    }

    public LivraisonVehicule commandeVehicule(CommandeVehicule commandeVehicule) {
        this.commandeVehicule = commandeVehicule;
        return this;
    }

    public void setCommandeVehicule(CommandeVehicule commandeVehicule) {
        this.commandeVehicule = commandeVehicule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivraisonVehicule)) {
            return false;
        }
        return id != null && id.equals(((LivraisonVehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LivraisonVehicule{" +
            "id=" + getId() +
            ", numeroLivraison='" + getNumeroLivraison() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            "}";
    }
}
