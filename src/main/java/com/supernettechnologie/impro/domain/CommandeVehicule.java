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
 * The CommandeVehicule entity.\n@author A true hipster
 */
@Entity
@Table(name = "commande_vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommandeVehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroCommandeVehicule
     */
    @Column(name = "numero_commande_vehicule")
    private String numeroCommandeVehicule;

    @Column(name = "date_commande")
    private ZonedDateTime dateCommande;

    @Column(name = "est_livree")
    private Boolean estLivree;

    @OneToMany(mappedBy = "commandeVehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InfoCommandeVehicule> infoCommandeVehicules = new HashSet<>();

    @OneToMany(mappedBy = "commandeVehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LivraisonVehicule> livraisonVehicules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("commandeVRevendeurs")
    private Organisation revendeur;

    @ManyToOne
    @JsonIgnoreProperties("commandeVConcessionnaires")
    private Organisation concessionnaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommandeVehicule() {
        return numeroCommandeVehicule;
    }

    public CommandeVehicule numeroCommandeVehicule(String numeroCommandeVehicule) {
        this.numeroCommandeVehicule = numeroCommandeVehicule;
        return this;
    }

    public void setNumeroCommandeVehicule(String numeroCommandeVehicule) {
        this.numeroCommandeVehicule = numeroCommandeVehicule;
    }

    public ZonedDateTime getDateCommande() {
        return dateCommande;
    }

    public CommandeVehicule dateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Boolean isEstLivree() {
        return estLivree;
    }

    public CommandeVehicule estLivree(Boolean estLivree) {
        this.estLivree = estLivree;
        return this;
    }

    public void setEstLivree(Boolean estLivree) {
        this.estLivree = estLivree;
    }

    public Set<InfoCommandeVehicule> getInfoCommandeVehicules() {
        return infoCommandeVehicules;
    }

    public CommandeVehicule infoCommandeVehicules(Set<InfoCommandeVehicule> infoCommandeVehicules) {
        this.infoCommandeVehicules = infoCommandeVehicules;
        return this;
    }

    public CommandeVehicule addInfoCommandeVehicule(InfoCommandeVehicule infoCommandeVehicule) {
        this.infoCommandeVehicules.add(infoCommandeVehicule);
        infoCommandeVehicule.setCommandeVehicule(this);
        return this;
    }

    public CommandeVehicule removeInfoCommandeVehicule(InfoCommandeVehicule infoCommandeVehicule) {
        this.infoCommandeVehicules.remove(infoCommandeVehicule);
        infoCommandeVehicule.setCommandeVehicule(null);
        return this;
    }

    public void setInfoCommandeVehicules(Set<InfoCommandeVehicule> infoCommandeVehicules) {
        this.infoCommandeVehicules = infoCommandeVehicules;
    }

    public Set<LivraisonVehicule> getLivraisonVehicules() {
        return livraisonVehicules;
    }

    public CommandeVehicule livraisonVehicules(Set<LivraisonVehicule> livraisonVehicules) {
        this.livraisonVehicules = livraisonVehicules;
        return this;
    }

    public CommandeVehicule addLivraisonVehicule(LivraisonVehicule livraisonVehicule) {
        this.livraisonVehicules.add(livraisonVehicule);
        livraisonVehicule.setCommandeVehicule(this);
        return this;
    }

    public CommandeVehicule removeLivraisonVehicule(LivraisonVehicule livraisonVehicule) {
        this.livraisonVehicules.remove(livraisonVehicule);
        livraisonVehicule.setCommandeVehicule(null);
        return this;
    }

    public void setLivraisonVehicules(Set<LivraisonVehicule> livraisonVehicules) {
        this.livraisonVehicules = livraisonVehicules;
    }

    public Organisation getRevendeur() {
        return revendeur;
    }

    public CommandeVehicule revendeur(Organisation organisation) {
        this.revendeur = organisation;
        return this;
    }

    public void setRevendeur(Organisation organisation) {
        this.revendeur = organisation;
    }

    public Organisation getConcessionnaire() {
        return concessionnaire;
    }

    public CommandeVehicule concessionnaire(Organisation organisation) {
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
        if (!(o instanceof CommandeVehicule)) {
            return false;
        }
        return id != null && id.equals(((CommandeVehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommandeVehicule{" +
            "id=" + getId() +
            ", numeroCommandeVehicule='" + getNumeroCommandeVehicule() + "'" +
            ", dateCommande='" + getDateCommande() + "'" +
            ", estLivree='" + isEstLivree() + "'" +
            "}";
    }
}
