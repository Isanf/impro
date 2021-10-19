package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The Vehicule entity.\n@author A true hipster
 */
@Entity
@Table(name = "vehicule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroChassis
     */
    @Column(name = "numero_chassis")
    private String numeroChassis;

    @Column(name = "types")
    private String types;

    @Column(name = "model")
    private String model;

    @Column(name = "energie")
    private String energie;

    @Column(name = "puissance_reel")
    private String puissanceReel;

    @Column(name = "puissance_admin")
    private String puissanceAdmin;

    @Column(name = "couleur")
    private String couleur;

    @Column(name = "poids_vide")
    private Integer poidsVide;

    @Column(name = "charge_utile")
    private Integer chargeUtile;

    @Column(name = "ptac")
    private Integer ptac;

    @Column(name = "ptra")
    private Integer ptra;

    @Column(name = "nbr_place")
    private Integer nbrPlace;

    @Column(name = "capacite")
    private Integer capacite;

    @Column(name = "date_mise_circulation")
    private LocalDate dateMiseCirculation;

    @Column(name = "regime")
    private String regime;

    @Column(name = "no_dedouanement")
    private String noDedouanement;

    @Column(name = "date_dedouanement")
    private LocalDate dateDedouanement;

    @OneToMany(mappedBy = "vehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Immatriculation> immatriculations = new HashSet<>();

    @OneToMany(mappedBy = "vehicule")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlaqueImmatriculation> plaqueImmatriculations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("vehicules")
    private LivraisonVehicule livraisonVehicule;

    @ManyToOne
    @JsonIgnoreProperties("vehicules")
    private TypeVehicule typeVehicule;

    @ManyToOne
    @JsonIgnoreProperties("vehicules")
    private MarqueVehicule marqueVehicule;

    @ManyToOne
    @JsonIgnoreProperties("vehicules")
    private Vente vente;

    @ManyToOne
    @JsonIgnoreProperties("vehicules")
    private Stock stock;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroChassis() {
        return numeroChassis;
    }

    public Vehicule numeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
        return this;
    }

    public void setNumeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
    }

    public String getTypes() {
        return types;
    }

    public Vehicule types(String types) {
        this.types = types;
        return this;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getModel() {
        return model;
    }

    public Vehicule model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEnergie() {
        return energie;
    }

    public Vehicule energie(String energie) {
        this.energie = energie;
        return this;
    }

    public void setEnergie(String energie) {
        this.energie = energie;
    }

    public String getPuissanceReel() {
        return puissanceReel;
    }

    public Vehicule puissanceReel(String puissanceReel) {
        this.puissanceReel = puissanceReel;
        return this;
    }

    public void setPuissanceReel(String puissanceReel) {
        this.puissanceReel = puissanceReel;
    }

    public String getPuissanceAdmin() {
        return puissanceAdmin;
    }

    public Vehicule puissanceAdmin(String puissanceAdmin) {
        this.puissanceAdmin = puissanceAdmin;
        return this;
    }

    public void setPuissanceAdmin(String puissanceAdmin) {
        this.puissanceAdmin = puissanceAdmin;
    }

    public String getCouleur() {
        return couleur;
    }

    public Vehicule couleur(String couleur) {
        this.couleur = couleur;
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Integer getPoidsVide() {
        return poidsVide;
    }

    public Vehicule poidsVide(Integer poidsVide) {
        this.poidsVide = poidsVide;
        return this;
    }

    public void setPoidsVide(Integer poidsVide) {
        this.poidsVide = poidsVide;
    }

    public Integer getChargeUtile() {
        return chargeUtile;
    }

    public Vehicule chargeUtile(Integer chargeUtile) {
        this.chargeUtile = chargeUtile;
        return this;
    }

    public void setChargeUtile(Integer chargeUtile) {
        this.chargeUtile = chargeUtile;
    }

    public Integer getPtac() {
        return ptac;
    }

    public Vehicule ptac(Integer ptac) {
        this.ptac = ptac;
        return this;
    }

    public void setPtac(Integer ptac) {
        this.ptac = ptac;
    }

    public Integer getPtra() {
        return ptra;
    }

    public Vehicule ptra(Integer ptra) {
        this.ptra = ptra;
        return this;
    }

    public void setPtra(Integer ptra) {
        this.ptra = ptra;
    }

    public Integer getNbrPlace() {
        return nbrPlace;
    }

    public Vehicule nbrPlace(Integer nbrPlace) {
        this.nbrPlace = nbrPlace;
        return this;
    }

    public void setNbrPlace(Integer nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public Vehicule capacite(Integer capacite) {
        this.capacite = capacite;
        return this;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public LocalDate getDateMiseCirculation() {
        return dateMiseCirculation;
    }

    public Vehicule dateMiseCirculation(LocalDate dateMiseCirculation) {
        this.dateMiseCirculation = dateMiseCirculation;
        return this;
    }

    public void setDateMiseCirculation(LocalDate dateMiseCirculation) {
        this.dateMiseCirculation = dateMiseCirculation;
    }

    public String getRegime() {
        return regime;
    }

    public Vehicule regime(String regime) {
        this.regime = regime;
        return this;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getNoDedouanement() {
        return noDedouanement;
    }

    public Vehicule noDedouanement(String noDedouanement) {
        this.noDedouanement = noDedouanement;
        return this;
    }

    public void setNoDedouanement(String noDedouanement) {
        this.noDedouanement = noDedouanement;
    }

    public LocalDate getDateDedouanement() {
        return dateDedouanement;
    }

    public Vehicule dateDedouanement(LocalDate dateDedouanement) {
        this.dateDedouanement = dateDedouanement;
        return this;
    }

    public void setDateDedouanement(LocalDate dateDedouanement) {
        this.dateDedouanement = dateDedouanement;
    }

    public Set<Immatriculation> getImmatriculations() {
        return immatriculations;
    }

    public Vehicule immatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
        return this;
    }

    public Vehicule addImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.add(immatriculation);
        immatriculation.setVehicule(this);
        return this;
    }

    public Vehicule removeImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.remove(immatriculation);
        immatriculation.setVehicule(null);
        return this;
    }

    public void setImmatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
    }

    public Set<PlaqueImmatriculation> getPlaqueImmatriculations() {
        return plaqueImmatriculations;
    }

    public Vehicule plaqueImmatriculations(Set<PlaqueImmatriculation> plaqueImmatriculations) {
        this.plaqueImmatriculations = plaqueImmatriculations;
        return this;
    }

    public Vehicule addPlaqueImmatriculation(PlaqueImmatriculation plaqueImmatriculation) {
        this.plaqueImmatriculations.add(plaqueImmatriculation);
        plaqueImmatriculation.setVehicule(this);
        return this;
    }

    public Vehicule removePlaqueImmatriculation(PlaqueImmatriculation plaqueImmatriculation) {
        this.plaqueImmatriculations.remove(plaqueImmatriculation);
        plaqueImmatriculation.setVehicule(null);
        return this;
    }

    public void setPlaqueImmatriculations(Set<PlaqueImmatriculation> plaqueImmatriculations) {
        this.plaqueImmatriculations = plaqueImmatriculations;
    }

    public LivraisonVehicule getLivraisonVehicule() {
        return livraisonVehicule;
    }

    public Vehicule livraisonVehicule(LivraisonVehicule livraisonVehicule) {
        this.livraisonVehicule = livraisonVehicule;
        return this;
    }

    public void setLivraisonVehicule(LivraisonVehicule livraisonVehicule) {
        this.livraisonVehicule = livraisonVehicule;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public Vehicule typeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
        return this;
    }

    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public MarqueVehicule getMarqueVehicule() {
        return marqueVehicule;
    }

    public Vehicule marqueVehicule(MarqueVehicule marqueVehicule) {
        this.marqueVehicule = marqueVehicule;
        return this;
    }

    public void setMarqueVehicule(MarqueVehicule marqueVehicule) {
        this.marqueVehicule = marqueVehicule;
    }

    public Vente getVente() {
        return vente;
    }

    public Vehicule vente(Vente vente) {
        this.vente = vente;
        return this;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Stock getStock() {
        return stock;
    }

    public Vehicule stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicule)) {
            return false;
        }
        return id != null && id.equals(((Vehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
            "id=" + getId() +
            ", numeroChassis='" + getNumeroChassis() + "'" +
            ", types='" + getTypes() + "'" +
            ", model='" + getModel() + "'" +
            ", energie='" + getEnergie() + "'" +
            ", puissanceReel='" + getPuissanceReel() + "'" +
            ", puissanceAdmin='" + getPuissanceAdmin() + "'" +
            ", couleur='" + getCouleur() + "'" +
            ", poidsVide=" + getPoidsVide() +
            ", chargeUtile=" + getChargeUtile() +
            ", ptac=" + getPtac() +
            ", ptra=" + getPtra() +
            ", nbrPlace=" + getNbrPlace() +
            ", capacite=" + getCapacite() +
            ", dateMiseCirculation='" + getDateMiseCirculation() + "'" +
            ", regime='" + getRegime() + "'" +
            ", noDedouanement='" + getNoDedouanement() + "'" +
            ", dateDedouanement='" + getDateDedouanement() + "'" +
            "}";
    }
}
