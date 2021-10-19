package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The InfoCommandeCarnetASouche entity.\n@author A true hipster
 */
@Entity
@Table(name = "info_commande_carnet_a_souche")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InfoCommandeCarnetASouche implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroCommande
     */
    @Column(name = "numero_commande")
    private String numeroCommande;

    @Column(name = "date_commande")
    private ZonedDateTime dateCommande;

    @Column(name = "quantite_commande")
    private Long quantiteCommande;

    @Column(name = "est_deliver")
    private Boolean estDeliver;

    @Column(name = "est_transiter")
    private Boolean estTransiter;

    @ManyToOne
    @JsonIgnoreProperties(value = "infoCommandeCarnetASouches", allowSetters = true)
    private CommandeCarnetSouche commandeCarnetSouche;

    @ManyToOne
    @JsonIgnoreProperties(value = "infoCommandeCarnetASouches", allowSetters = true)
    private TypeCarnet typeCarnet;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public InfoCommandeCarnetASouche numeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
        return this;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public ZonedDateTime getDateCommande() {
        return dateCommande;
    }

    public InfoCommandeCarnetASouche dateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Long getQuantiteCommande() {
        return quantiteCommande;
    }

    public InfoCommandeCarnetASouche quantiteCommande(Long quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
        return this;
    }

    public void setQuantiteCommande(Long quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public Boolean isEstDeliver() {
        return estDeliver;
    }

    public InfoCommandeCarnetASouche estDeliver(Boolean estDeliver) {
        this.estDeliver = estDeliver;
        return this;
    }

    public void setEstDeliver(Boolean estDeliver) {
        this.estDeliver = estDeliver;
    }

    public Boolean isEstTransiter() {
        return estTransiter;
    }

    public InfoCommandeCarnetASouche estTransiter(Boolean estTransiter) {
        this.estTransiter = estTransiter;
        return this;
    }

    public void setEstTransiter(Boolean estTransiter) {
        this.estTransiter = estTransiter;
    }

    public CommandeCarnetSouche getCommandeCarnetSouche() {
        return commandeCarnetSouche;
    }

    public InfoCommandeCarnetASouche commandeCarnetSouche(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCarnetSouche = commandeCarnetSouche;
        return this;
    }

    public void setCommandeCarnetSouche(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCarnetSouche = commandeCarnetSouche;
    }

    public TypeCarnet getTypeCarnet() {
        return typeCarnet;
    }

    public InfoCommandeCarnetASouche typeCarnet(TypeCarnet typeCarnet) {
        this.typeCarnet = typeCarnet;
        return this;
    }

    public void setTypeCarnet(TypeCarnet typeCarnet) {
        this.typeCarnet = typeCarnet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoCommandeCarnetASouche)) {
            return false;
        }
        return id != null && id.equals(((InfoCommandeCarnetASouche) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InfoCommandeCarnetASouche{" +
            "id=" + getId() +
            ", numeroCommande='" + getNumeroCommande() + "'" +
            ", dateCommande='" + getDateCommande() + "'" +
            ", quantiteCommande=" + getQuantiteCommande() +
            ", estDeliver='" + isEstDeliver() + "'" +
            ", estTransiter='" + isEstTransiter() + "'" +
            "}";
    }
}
