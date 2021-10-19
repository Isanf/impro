package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Organisation.
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Column(name = "numero_ordre", nullable = false)
    private Integer numeroOrdre;

    @Column(name = "numero_phone")
    private String numeroPhone;

    @Column(name = "signnom")
    private String signnom;

    @OneToMany(mappedBy = "concessionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CarnetASouche> carnetASouches = new HashSet<>();

    @OneToMany(mappedBy = "revendeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborationsRevendeurs = new HashSet<>();

    @OneToMany(mappedBy = "concessionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Collaboration> collaborationsConcessionnaires = new HashSet<>();

    @OneToMany(mappedBy = "concessionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommandeCarnetSouche> commandeCSConcessionnaires = new HashSet<>();

    @OneToMany(mappedBy = "supernet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommandeCarnetSouche> commandeCSRevendeurs = new HashSet<>();

    @OneToMany(mappedBy = "revendeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommandeVehicule> commandeVRevendeurs = new HashSet<>();

    @OneToMany(mappedBy = "concessionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommandeVehicule> commandeVConcessionnaires = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Immatriculation> immatriculations = new HashSet<>();

    @OneToMany(mappedBy = "concessionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LivraisonCarnetSouche> livraisonCSConcessionnaires = new HashSet<>();

    @OneToMany(mappedBy = "supernet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LivraisonCarnetSouche> livraisonCSSupernets = new HashSet<>();

    @OneToMany(mappedBy = "revendeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LivraisonVehicule> livraisonVRevendeurs = new HashSet<>();

    @OneToMany(mappedBy = "concessionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<LivraisonVehicule> livraisonVConcessionnaires = new HashSet<>();

    @OneToMany(mappedBy = "pere")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Organisation> fils = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PersonnePhysique> personnePhysiques = new HashSet<>();

    @OneToMany(mappedBy = "revendeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PosePlaque> posePlaques = new HashSet<>();

    @OneToMany(mappedBy = "organisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Profil> profils = new HashSet<>();

    @OneToMany(mappedBy = "concessionnaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "revendeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Vente> ventes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "organisations", allowSetters = true)
    private OrganisationLocalite organisationLocalite;

    @ManyToOne
    @JsonIgnoreProperties(value = "fils", allowSetters = true)
    private Organisation pere;

    @ManyToOne
    @JsonIgnoreProperties(value = "organisations", allowSetters = true)
    private TypeOrganisation typeOrganisation;

    @ManyToOne
    @JsonIgnoreProperties(value = "organisations", allowSetters = true)
    private PersonnePhysique gerant;

    @ManyToOne
    @JsonIgnoreProperties(value = "organisations", allowSetters = true)
    private TypeActeur typeActeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    /*@OneToOne
    private ImageModel imageModel;*/

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Organisation nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Organisation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumeroOrdre() {
        return numeroOrdre;
    }

    public Organisation numeroOrdre(Integer numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
        return this;
    }

    public void setNumeroOrdre(Integer numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public String getNumeroPhone() {
        return numeroPhone;
    }

    public void setNumeroPhone(String numeroPhone) {
        this.numeroPhone = numeroPhone;
    }

    public Organisation numeroPhone(String numeroPhone) {
        this.numeroPhone = numeroPhone;
        return this;
    }

    public Set<CarnetASouche> getCarnetASouches() {
        return carnetASouches;
    }

    public Organisation carnetASouches(Set<CarnetASouche> carnetASouches) {
        this.carnetASouches = carnetASouches;
        return this;
    }

    public Organisation addCarnetASouche(CarnetASouche carnetASouche) {
        this.carnetASouches.add(carnetASouche);
        carnetASouche.setConcessionnaire(this);
        return this;
    }

    public Organisation removeCarnetASouche(CarnetASouche carnetASouche) {
        this.carnetASouches.remove(carnetASouche);
        carnetASouche.setConcessionnaire(null);
        return this;
    }

    public void setCarnetASouches(Set<CarnetASouche> carnetASouches) {
        this.carnetASouches = carnetASouches;
    }

    public Set<Collaboration> getCollaborationsRevendeurs() {
        return collaborationsRevendeurs;
    }

    public Organisation collaborationsRevendeurs(Set<Collaboration> collaborations) {
        this.collaborationsRevendeurs = collaborations;
        return this;
    }

    public Organisation addCollaborationsRevendeur(Collaboration collaboration) {
        this.collaborationsRevendeurs.add(collaboration);
        collaboration.setRevendeur(this);
        return this;
    }

    public Organisation removeCollaborationsRevendeur(Collaboration collaboration) {
        this.collaborationsRevendeurs.remove(collaboration);
        collaboration.setRevendeur(null);
        return this;
    }

    public void setCollaborationsRevendeurs(Set<Collaboration> collaborations) {
        this.collaborationsRevendeurs = collaborations;
    }

    public Set<Collaboration> getCollaborationsConcessionnaires() {
        return collaborationsConcessionnaires;
    }

    public Organisation collaborationsConcessionnaires(Set<Collaboration> collaborations) {
        this.collaborationsConcessionnaires = collaborations;
        return this;
    }

    public Organisation addCollaborationsConcessionnaire(Collaboration collaboration) {
        this.collaborationsConcessionnaires.add(collaboration);
        collaboration.setConcessionnaire(this);
        return this;
    }

    public Organisation removeCollaborationsConcessionnaire(Collaboration collaboration) {
        this.collaborationsConcessionnaires.remove(collaboration);
        collaboration.setConcessionnaire(null);
        return this;
    }

    public void setCollaborationsConcessionnaires(Set<Collaboration> collaborations) {
        this.collaborationsConcessionnaires = collaborations;
    }

    public Set<CommandeCarnetSouche> getCommandeCSConcessionnaires() {
        return commandeCSConcessionnaires;
    }

    public Organisation commandeCSConcessionnaires(Set<CommandeCarnetSouche> commandeCarnetSouches) {
        this.commandeCSConcessionnaires = commandeCarnetSouches;
        return this;
    }

    public Organisation addCommandeCSConcessionnaire(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCSConcessionnaires.add(commandeCarnetSouche);
        commandeCarnetSouche.setConcessionnaire(this);
        return this;
    }

    public Organisation removeCommandeCSConcessionnaire(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCSConcessionnaires.remove(commandeCarnetSouche);
        commandeCarnetSouche.setConcessionnaire(null);
        return this;
    }

    public void setCommandeCSConcessionnaires(Set<CommandeCarnetSouche> commandeCarnetSouches) {
        this.commandeCSConcessionnaires = commandeCarnetSouches;
    }

    public Set<CommandeCarnetSouche> getCommandeCSRevendeurs() {
        return commandeCSRevendeurs;
    }

    public Organisation commandeCSRevendeurs(Set<CommandeCarnetSouche> commandeCarnetSouches) {
        this.commandeCSRevendeurs = commandeCarnetSouches;
        return this;
    }

    public Organisation addCommandeCSRevendeur(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCSRevendeurs.add(commandeCarnetSouche);
        commandeCarnetSouche.setSupernet(this);
        return this;
    }

    public Organisation removeCommandeCSRevendeur(CommandeCarnetSouche commandeCarnetSouche) {
        this.commandeCSRevendeurs.remove(commandeCarnetSouche);
        commandeCarnetSouche.setSupernet(null);
        return this;
    }

    public void setCommandeCSRevendeurs(Set<CommandeCarnetSouche> commandeCarnetSouches) {
        this.commandeCSRevendeurs = commandeCarnetSouches;
    }

    public Set<CommandeVehicule> getCommandeVRevendeurs() {
        return commandeVRevendeurs;
    }

    public Organisation commandeVRevendeurs(Set<CommandeVehicule> commandeVehicules) {
        this.commandeVRevendeurs = commandeVehicules;
        return this;
    }

    public Organisation addCommandeVRevendeur(CommandeVehicule commandeVehicule) {
        this.commandeVRevendeurs.add(commandeVehicule);
        commandeVehicule.setRevendeur(this);
        return this;
    }

    public Organisation removeCommandeVRevendeur(CommandeVehicule commandeVehicule) {
        this.commandeVRevendeurs.remove(commandeVehicule);
        commandeVehicule.setRevendeur(null);
        return this;
    }

    public void setCommandeVRevendeurs(Set<CommandeVehicule> commandeVehicules) {
        this.commandeVRevendeurs = commandeVehicules;
    }

    public Set<CommandeVehicule> getCommandeVConcessionnaires() {
        return commandeVConcessionnaires;
    }

    public Organisation commandeVConcessionnaires(Set<CommandeVehicule> commandeVehicules) {
        this.commandeVConcessionnaires = commandeVehicules;
        return this;
    }

    public Organisation addCommandeVConcessionnaire(CommandeVehicule commandeVehicule) {
        this.commandeVConcessionnaires.add(commandeVehicule);
        commandeVehicule.setConcessionnaire(this);
        return this;
    }

    public Organisation removeCommandeVConcessionnaire(CommandeVehicule commandeVehicule) {
        this.commandeVConcessionnaires.remove(commandeVehicule);
        commandeVehicule.setConcessionnaire(null);
        return this;
    }

    public void setCommandeVConcessionnaires(Set<CommandeVehicule> commandeVehicules) {
        this.commandeVConcessionnaires = commandeVehicules;
    }

    public Set<Immatriculation> getImmatriculations() {
        return immatriculations;
    }

    public Organisation immatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
        return this;
    }

    public Organisation addImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.add(immatriculation);
        immatriculation.setOrganisation(this);
        return this;
    }

    public Organisation removeImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.remove(immatriculation);
        immatriculation.setOrganisation(null);
        return this;
    }

    public void setImmatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
    }

    public Set<LivraisonCarnetSouche> getLivraisonCSConcessionnaires() {
        return livraisonCSConcessionnaires;
    }

    public Organisation livraisonCSConcessionnaires(Set<LivraisonCarnetSouche> livraisonCarnetSouches) {
        this.livraisonCSConcessionnaires = livraisonCarnetSouches;
        return this;
    }

    public Organisation addLivraisonCSConcessionnaire(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCSConcessionnaires.add(livraisonCarnetSouche);
        livraisonCarnetSouche.setConcessionnaire(this);
        return this;
    }

    public Organisation removeLivraisonCSConcessionnaire(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCSConcessionnaires.remove(livraisonCarnetSouche);
        livraisonCarnetSouche.setConcessionnaire(null);
        return this;
    }

    public void setLivraisonCSConcessionnaires(Set<LivraisonCarnetSouche> livraisonCarnetSouches) {
        this.livraisonCSConcessionnaires = livraisonCarnetSouches;
    }

    public Set<LivraisonCarnetSouche> getLivraisonCSSupernets() {
        return livraisonCSSupernets;
    }

    public Organisation livraisonCSSupernets(Set<LivraisonCarnetSouche> livraisonCarnetSouches) {
        this.livraisonCSSupernets = livraisonCarnetSouches;
        return this;
    }

    public Organisation addLivraisonCSSupernet(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCSSupernets.add(livraisonCarnetSouche);
        livraisonCarnetSouche.setSupernet(this);
        return this;
    }

    public Organisation removeLivraisonCSSupernet(LivraisonCarnetSouche livraisonCarnetSouche) {
        this.livraisonCSSupernets.remove(livraisonCarnetSouche);
        livraisonCarnetSouche.setSupernet(null);
        return this;
    }

    public void setLivraisonCSSupernets(Set<LivraisonCarnetSouche> livraisonCarnetSouches) {
        this.livraisonCSSupernets = livraisonCarnetSouches;
    }

    public Set<LivraisonVehicule> getLivraisonVRevendeurs() {
        return livraisonVRevendeurs;
    }

    public Organisation livraisonVRevendeurs(Set<LivraisonVehicule> livraisonVehicules) {
        this.livraisonVRevendeurs = livraisonVehicules;
        return this;
    }

    public Organisation addLivraisonVRevendeur(LivraisonVehicule livraisonVehicule) {
        this.livraisonVRevendeurs.add(livraisonVehicule);
        livraisonVehicule.setRevendeur(this);
        return this;
    }

    public Organisation removeLivraisonVRevendeur(LivraisonVehicule livraisonVehicule) {
        this.livraisonVRevendeurs.remove(livraisonVehicule);
        livraisonVehicule.setRevendeur(null);
        return this;
    }

    public void setLivraisonVRevendeurs(Set<LivraisonVehicule> livraisonVehicules) {
        this.livraisonVRevendeurs = livraisonVehicules;
    }

    public Set<LivraisonVehicule> getLivraisonVConcessionnaires() {
        return livraisonVConcessionnaires;
    }

    public Organisation livraisonVConcessionnaires(Set<LivraisonVehicule> livraisonVehicules) {
        this.livraisonVConcessionnaires = livraisonVehicules;
        return this;
    }

    public Organisation addLivraisonVConcessionnaire(LivraisonVehicule livraisonVehicule) {
        this.livraisonVConcessionnaires.add(livraisonVehicule);
        livraisonVehicule.setConcessionnaire(this);
        return this;
    }

    public Organisation removeLivraisonVConcessionnaire(LivraisonVehicule livraisonVehicule) {
        this.livraisonVConcessionnaires.remove(livraisonVehicule);
        livraisonVehicule.setConcessionnaire(null);
        return this;
    }

    public void setLivraisonVConcessionnaires(Set<LivraisonVehicule> livraisonVehicules) {
        this.livraisonVConcessionnaires = livraisonVehicules;
    }

    public Set<Organisation> getFils() {
        return fils;
    }

    public Organisation fils(Set<Organisation> organisations) {
        this.fils = organisations;
        return this;
    }

    public Organisation addFils(Organisation organisation) {
        this.fils.add(organisation);
        organisation.setPere(this);
        return this;
    }

    public Organisation removeFils(Organisation organisation) {
        this.fils.remove(organisation);
        organisation.setPere(null);
        return this;
    }

    public void setFils(Set<Organisation> organisations) {
        this.fils = organisations;
    }

    public Set<PersonnePhysique> getPersonnePhysiques() {
        return personnePhysiques;
    }

    public Organisation personnePhysiques(Set<PersonnePhysique> personnePhysiques) {
        this.personnePhysiques = personnePhysiques;
        return this;
    }

    public Organisation addPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysiques.add(personnePhysique);
        personnePhysique.setOrganisation(this);
        return this;
    }

    public Organisation removePersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysiques.remove(personnePhysique);
        personnePhysique.setOrganisation(null);
        return this;
    }

    public void setPersonnePhysiques(Set<PersonnePhysique> personnePhysiques) {
        this.personnePhysiques = personnePhysiques;
    }

    public Set<PosePlaque> getPosePlaques() {
        return posePlaques;
    }

    public Organisation posePlaques(Set<PosePlaque> posePlaques) {
        this.posePlaques = posePlaques;
        return this;
    }

    public Organisation addPosePlaque(PosePlaque posePlaque) {
        this.posePlaques.add(posePlaque);
        posePlaque.setRevendeur(this);
        return this;
    }

    public Organisation removePosePlaque(PosePlaque posePlaque) {
        this.posePlaques.remove(posePlaque);
        posePlaque.setRevendeur(null);
        return this;
    }

    public void setPosePlaques(Set<PosePlaque> posePlaques) {
        this.posePlaques = posePlaques;
    }

    public Set<Profil> getProfils() {
        return profils;
    }

    public Organisation profils(Set<Profil> profils) {
        this.profils = profils;
        return this;
    }

    public Organisation addProfils(Profil profil) {
        this.profils.add(profil);
        profil.setOrganisation(this);
        return this;
    }

    public Organisation removeProfils(Profil profil) {
        this.profils.remove(profil);
        profil.setOrganisation(null);
        return this;
    }

    public void setProfils(Set<Profil> profils) {
        this.profils = profils;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Organisation stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Organisation addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setConcessionnaire(this);
        return this;
    }

    public Organisation removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setConcessionnaire(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<Vente> getVentes() {
        return ventes;
    }

    public Organisation ventes(Set<Vente> ventes) {
        this.ventes = ventes;
        return this;
    }

    public Organisation addVente(Vente vente) {
        this.ventes.add(vente);
        vente.setRevendeur(this);
        return this;
    }

    public Organisation removeVente(Vente vente) {
        this.ventes.remove(vente);
        vente.setRevendeur(null);
        return this;
    }

    public void setVentes(Set<Vente> ventes) {
        this.ventes = ventes;
    }

    public OrganisationLocalite getOrganisationLocalite() {
        return organisationLocalite;
    }

    public Organisation organisationLocalite(OrganisationLocalite organisationLocalite) {
        this.organisationLocalite = organisationLocalite;
        return this;
    }

    public void setOrganisationLocalite(OrganisationLocalite organisationLocalite) {
        this.organisationLocalite = organisationLocalite;
    }

    public Organisation getPere() {
        return pere;
    }

    public Organisation pere(Organisation organisation) {
        this.pere = organisation;
        return this;
    }

    public void setPere(Organisation organisation) {
        this.pere = organisation;
    }

    public TypeOrganisation getTypeOrganisation() {
        return typeOrganisation;
    }

    public Organisation typeOrganisation(TypeOrganisation typeOrganisation) {
        this.typeOrganisation = typeOrganisation;
        return this;
    }

    public void setTypeOrganisation(TypeOrganisation typeOrganisation) {
        this.typeOrganisation = typeOrganisation;
    }

    public PersonnePhysique getGerant() {
        return gerant;
    }

    public Organisation gerant(PersonnePhysique personnePhysique) {
        this.gerant = personnePhysique;
        return this;
    }

    public void setGerant(PersonnePhysique personnePhysique) {
        this.gerant = personnePhysique;
    }

    public TypeActeur getTypeActeur() {
        return typeActeur;
    }

    public Organisation typeActeur(TypeActeur typeActeur) {
        this.typeActeur = typeActeur;
        return this;
    }

    public void setTypeActeur(TypeActeur typeActeur) {
        this.typeActeur = typeActeur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    /*public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }*/

    public String getSignnom() {
        return signnom;
    }

    public void setSignnom(String signnom) {
        this.signnom = signnom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisation)) {
            return false;
        }
        return id != null && id.equals(((Organisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", numeroOrdre=" + getNumeroOrdre() +
            ", numeroPhone='" + getNumeroPhone() + "'" +
            "}";
    }
}
