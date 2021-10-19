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
 * The LivraisonCarnetSouche entity.\n@author A true hipster
 */
@Entity
@Table(name = "livraison_carnet_souche")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LivraisonCarnetSouche implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroLivraisonCS
     */
    @Column(name = "numero_livraison_cs")
    private String numeroLivraisonCS;

    @Column(name = "date_livraison")
    private ZonedDateTime dateLivraison;

    @OneToMany(mappedBy = "livraisonCarnetSouche")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CarnetASouche> carnetASouches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("livraisonCSConcessionnaires")
    private Organisation concessionnaire;

    @ManyToOne
    @JsonIgnoreProperties("livraisonCSSupernets")
    private Organisation supernet;

    @ManyToOne
    @JsonIgnoreProperties("livraisonCarnetSouches")
    private CommandeCarnetSouche commandeCarnetSouche;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroLivraisonCS() {
        return numeroLivraisonCS;
    }

    public LivraisonCarnetSouche numeroLivraisonCS(String numeroLivraisonCS) {
        this.numeroLivraisonCS = numeroLivraisonCS;
        return this;
    }

    public void setNumeroLivraisonCS(String numeroLivraisonCS) {
        this.numeroLivraisonCS = numeroLivraisonCS;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public LivraisonCarnetSouche dateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
        return this;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Set<CarnetASouche> getCarnetASouches() {
        return carnetASouches;
    }

    public LivraisonCarnetSouche carnetASouches(Set<CarnetASouche> carnetASouches) {
        this.carnetASouches = carnetASouches;
        return this;
    }

    public LivraisonCarnetSouche addCarnetASouche(CarnetASouche carnetASouche) {
        this.carnetASouches.add(carnetASouche);
        carnetASouche.setLivraisonCarnetSouche(this);
        return this;
    }

    public LivraisonCarnetSouche removeCarnetASouche(CarnetASouche carnetASouche) {
        this.carnetASouches.remove(carnetASouche);
        carnetASouche.setLivraisonCarnetSouche(null);
        return this;
    }

    public void setCarnetASouches(Set<CarnetASouche> carnetASouches) {
        this.carnetASouches = carnetASouches;
    }

    public Organisation getConcessionnaire() {
        return concessionnaire;
    }

    public LivraisonCarnetSouche concessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
        return this;
    }

    public void setConcessionnaire(Organisation organisation) {
        this.concessionnaire = organisation;
    }

    public Organisation getSupernet() {
        return supernet;
    }

    public LivraisonCarnetSouche supernet(Organisation organisation) {
        this.supernet = organisation;
        return this;
    }

    public void setSupernet(Organisation organisation) {
        this.supernet = organisation;
    }

    public CommandeCarnetSouche getCommandeCarnetSouche() {
        return commandeCarnetSouche;
    }

    public LivraisonCarnetSouche commandeCarnetSouche(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCarnetSouche = commandeCarnetSouche;
        return this;
    }

    public void setCommandeCarnetSouche(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCarnetSouche = commandeCarnetSouche;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivraisonCarnetSouche)) {
            return false;
        }
        return id != null && id.equals(((LivraisonCarnetSouche) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LivraisonCarnetSouche{" +
            "id=" + getId() +
            ", numeroLivraisonCS='" + getNumeroLivraisonCS() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            "}";
    }
}
