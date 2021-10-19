package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The InfoCommandeVehicule entity.\n@author A true hipster
 */
@Entity
@Table(name = "info_commande_vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InfoCommandeVehicule implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("infoCommandeVehicules")
    private CommandeVehicule commandeVehicule;

    @ManyToOne
    @JsonIgnoreProperties("infoCommandeVehicules")
    private MarqueVehicule marqueVehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public TypeVehicule getTypeVehicule() {
        TypeVehicule typeVehicule = new TypeVehicule();
        return typeVehicule;
    }

    public InfoCommandeVehicule numeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
        return this;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public ZonedDateTime getDateCommande() {
        return dateCommande;
    }

    public InfoCommandeVehicule dateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Long getQuantiteCommande() {
        return quantiteCommande;
    }

    public InfoCommandeVehicule quantiteCommande(Long quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
        return this;
    }

    public void setQuantiteCommande(Long quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public CommandeVehicule getCommandeVehicule() {
        return commandeVehicule;
    }

    public InfoCommandeVehicule commandeVehicule(CommandeVehicule commandeVehicule) {
        this.commandeVehicule = commandeVehicule;
        return this;
    }

    public void setCommandeVehicule(CommandeVehicule commandeVehicule) {
        this.commandeVehicule = commandeVehicule;
    }

    public MarqueVehicule getMarqueVehicule() {
        return marqueVehicule;
    }

    public InfoCommandeVehicule marqueVehicule(MarqueVehicule marqueVehicule) {
        this.marqueVehicule = marqueVehicule;
        return this;
    }

    public void setMarqueVehicule(MarqueVehicule marqueVehicule) {
        this.marqueVehicule = marqueVehicule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InfoCommandeVehicule)) {
            return false;
        }
        return id != null && id.equals(((InfoCommandeVehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InfoCommandeVehicule{" +
            "id=" + getId() +
            ", numeroCommande='" + getNumeroCommande() + "'" +
            ", dateCommande='" + getDateCommande() + "'" +
            ", quantiteCommande=" + getQuantiteCommande() +
            "}";
    }
}
