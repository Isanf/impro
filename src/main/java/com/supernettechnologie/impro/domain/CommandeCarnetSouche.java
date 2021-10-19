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
 * The CommandeCarnetSouche entity.\n@author A true hipster
 */
@Entity
@Table(name = "commande_carnet_souche")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommandeCarnetSouche implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroCommandeCS
     */
    @Column(name = "numero_commande_cs")
    private String numeroCommandeCS;

    @Column(name = "date_commande_cs")
    private ZonedDateTime dateCommandeCS;

    @Column(name = "type_paiement")
    private String typePaiement;

    @Column(name = "est_valide")
    private Boolean estValide;

    @Column(name = "est_traitee")
    private Boolean estTraitee;

    @Column(name = "est_livree")
    private Boolean estLivree;

    @Column(name = "prix_commande")
    private String prixCommande;

    @OneToMany(mappedBy = "commandeCarnetSouche")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LivraisonCarnetSouche> livraisonCarnetSouches = new HashSet<>();

    @OneToMany(mappedBy = "commandeCarnetSouche")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<InfoCommandeCarnetASouche> infoCommandeCarnetASouches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "commandeCSConcessionnaires", allowSetters = true)
    private Organisation concessionnaire;

    @ManyToOne
    @JsonIgnoreProperties(value = "commandeCSRevendeurs", allowSetters = true)
    private Organisation supernet;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommandeCS() {
        return numeroCommandeCS;
    }

    public CommandeCarnetSouche numeroCommandeCS(String numeroCommandeCS) {
        this.numeroCommandeCS = numeroCommandeCS;
        return this;
    }

    public void setNumeroCommandeCS(String numeroCommandeCS) {
        this.numeroCommandeCS = numeroCommandeCS;
    }

    public ZonedDateTime getDateCommandeCS() {
        return dateCommandeCS;
    }

    public CommandeCarnetSouche dateCommandeCS(ZonedDateTime dateCommandeCS) {
        this.dateCommandeCS = dateCommandeCS;
        return this;
    }

    public void setDateCommandeCS(ZonedDateTime dateCommandeCS) {
        this.dateCommandeCS = dateCommandeCS;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public CommandeCarnetSouche typePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
        return this;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public Boolean isEstValide() {
        return estValide;
    }

    public CommandeCarnetSouche estValide(Boolean estValide) {
        this.estValide = estValide;
        return this;
    }

    public void setEstValide(Boolean estValide) {
        this.estValide = estValide;
    }

    public Boolean isEstTraitee() {
        return estTraitee;
    }

    public CommandeCarnetSouche estTraitee(Boolean estTraitee) {
        this.estTraitee = estTraitee;
        return this;
    }

    public void setEstTraitee(Boolean estTraitee) {
        this.estTraitee = estTraitee;
    }

    public Boolean isEstLivree() {
        return estLivree;
    }

    public CommandeCarnetSouche estLivree(Boolean estLivree) {
        this.estLivree = estLivree;
        return this;
    }

    public void setEstLivree(Boolean estLivree) {
        this.estLivree = estLivree;
    }

    public String getPrixCommande() {
        return prixCommande;
    }

    public CommandeCarnetSouche prixCommande(String prixCommande) {
        this.prixCommande = prixCommande;
        return this;
    }

    public void setPrixCommande(String prixCommande) {
        this.prixCommande = prixCommande;
    }

    public Set<LivraisonCarnetSouche> getLivraisonCarnetSouches() {
        return livraisonCarnetSouches;
    }

    public CommandeCarnetSouche livraisonCarnetSouches(Set<LivraisonCarnetSouche> livraisonCarnetSouches) {
        this.livraisonCarnetSouches = livraisonCarnetSouches;
        return this;
    }

    public CommandeCarnetSouche addLivraisonCarnetSouche(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCarnetSouches.add(livraisonCarnetSouche);
        livraisonCarnetSouche.setCommandeCarnetSouche(this);
        return this;
    }

    public CommandeCarnetSouche removeLivraisonCarnetSouche(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCarnetSouches.remove(livraisonCarnetSouche);
        livraisonCarnetSouche.setCommandeCarnetSouche(null);
        return this;
    }

    public void setLivraisonCarnetSouches(Set<LivraisonCarnetSouche> livraisonCarnetSouches) {
        this.livraisonCarnetSouches = livraisonCarnetSouches;
    }

    public Set<InfoCommandeCarnetASouche> getInfoCommandeCarnetASouches() {
        return infoCommandeCarnetASouches;
    }

    public CommandeCarnetSouche infoCommandeCarnetASouches(Set<InfoCommandeCarnetASouche> infoCommandeCarnetASouches) {
        this.infoCommandeCarnetASouches = infoCommandeCarnetASouches;
        return this;
    }

    public CommandeCarnetSouche addInfoCommandeCarnetASouche(InfoCommandeCarnetASouche infoCommandeCarnetASouche) {
        this.infoCommandeCarnetASouches.add(infoCommandeCarnetASouche);
        infoCommandeCarnetASouche.setCommandeCarnetSouche(this);
        return this;
    }

    public CommandeCarnetSouche removeInfoCommandeCarnetASouche(InfoCommandeCarnetASouche infoCommandeCarnetASouche) {
        this.infoCommandeCarnetASouches.remove(infoCommandeCarnetASouche);
        infoCommandeCarnetASouche.setCommandeCarnetSouche(null);
        return this;
    }

    public void setInfoCommandeCarnetASouches(Set<InfoCommandeCarnetASouche> infoCommandeCarnetASouches) {
        this.infoCommandeCarnetASouches = infoCommandeCarnetASouches;
    }

    public Organisation getConcessionnaire() {
        return concessionnaire;
    }

    public CommandeCarnetSouche concessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
        return this;
    }

    public void setConcessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
    }

    public Organisation getSupernet() {
        return supernet;
    }

    public CommandeCarnetSouche supernet(Organisation organisation) {
        this.supernet = organisation;
        return this;
    }

    public void setSupernet(Organisation organisation) {
        this.supernet = organisation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeCarnetSouche)) {
            return false;
        }
        return id != null && id.equals(((CommandeCarnetSouche) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeCarnetSouche{" +
            "id=" + getId() +
            ", numeroCommandeCS='" + getNumeroCommandeCS() + "'" +
            ", dateCommandeCS='" + getDateCommandeCS() + "'" +
            ", typePaiement='" + getTypePaiement() + "'" +
            ", estValide='" + isEstValide() + "'" +
            ", estTraitee='" + isEstTraitee() + "'" +
            ", estLivree='" + isEstLivree() + "'" +
            ", prixCommande='" + getPrixCommande() + "'" +
            "}";
    }
}
