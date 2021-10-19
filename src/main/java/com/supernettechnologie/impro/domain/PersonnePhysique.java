package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The PersonnePhysique entity.\n@author A true hipster
 */
@Entity
@Table(name = "personne_physique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonnePhysique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * nom
     */
    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "residence")
    private String residence;

    @Column(name = "flogin")
    private String flogin;

    @Column(name = "fpassword")
    private String fpassword;

    @Column(name = "fotp")
    private Long fotp;

    @OneToOne
    @JoinColumn(unique = true)
    private DocIdentificationPP docIdentification;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "personnePhysique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Immatriculation> immatriculations = new HashSet<>();

    @OneToMany(mappedBy = "gerant")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Organisation> organisations = new HashSet<>();

    @OneToMany(mappedBy = "personnePhysique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vente> ventes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("personnePhysiques")
    private Organisation organisation;

    @ManyToOne
    @JsonIgnoreProperties("personnePhysiques")
    private Profil profil;

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

    public PersonnePhysique nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public PersonnePhysique prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public PersonnePhysique dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public PersonnePhysique lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public PersonnePhysique telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getResidence() {
        return residence;
    }

    public PersonnePhysique residence(String residence) {
        this.residence = residence;
        return this;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public DocIdentificationPP getDocIdentification() {
        return docIdentification;
    }

    public PersonnePhysique docIdentification(DocIdentificationPP docIdentificationPP) {
        this.docIdentification = docIdentificationPP;
        return this;
    }

    public void setDocIdentification(DocIdentificationPP docIdentificationPP) {
        this.docIdentification = docIdentificationPP;
    }

    public User getUser() {
        return user;
    }

    public PersonnePhysique user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Immatriculation> getImmatriculations() {
        return immatriculations;
    }

    public PersonnePhysique immatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
        return this;
    }

    public PersonnePhysique addImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.add(immatriculation);
        immatriculation.setPersonnePhysique(this);
        return this;
    }

    public PersonnePhysique removeImmatriculation(Immatriculation immatriculation) {
        this.immatriculations.remove(immatriculation);
        immatriculation.setPersonnePhysique(null);
        return this;
    }

    public void setImmatriculations(Set<Immatriculation> immatriculations) {
        this.immatriculations = immatriculations;
    }

    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    public PersonnePhysique organisations(Set<Organisation> organisations) {
        this.organisations = organisations;
        return this;
    }

    public PersonnePhysique addOrganisation(Organisation organisation) {
        this.organisations.add(organisation);
        organisation.setGerant(this);
        return this;
    }

    public PersonnePhysique removeOrganisation(Organisation organisation) {
        this.organisations.remove(organisation);
        organisation.setGerant(null);
        return this;
    }

    public void setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }

    public Set<Vente> getVentes() {
        return ventes;
    }

    public PersonnePhysique ventes(Set<Vente> ventes) {
        this.ventes = ventes;
        return this;
    }

    public PersonnePhysique addVente(Vente vente) {
        this.ventes.add(vente);
        vente.setPersonnePhysique(this);
        return this;
    }

    public PersonnePhysique removeVente(Vente vente) {
        this.ventes.remove(vente);
        vente.setPersonnePhysique(null);
        return this;
    }

    public void setVentes(Set<Vente> ventes) {
        this.ventes = ventes;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public PersonnePhysique organisation(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Profil getProfil() {
        return profil;
    }

    public PersonnePhysique profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public String getFlogin() {
        return flogin;
    }

    public void setFlogin(String flogin) {
        this.flogin = flogin;
    }

    public String getFpassword() {
        return fpassword;
    }

    public void setFpassword(String fpassword) {
        this.fpassword = fpassword;
    }

    public Long getFotp() {
        return fotp;
    }

    public void setFotp(Long fotp) {
        this.fotp = fotp;
    }

// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonnePhysique)) {
            return false;
        }
        return id != null && id.equals(((PersonnePhysique) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonnePhysique{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", residence='" + getResidence() + "'" +
            "}";
    }
}
