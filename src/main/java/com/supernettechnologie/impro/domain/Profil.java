package com.supernettechnologie.impro.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profil.
 */
@Entity
@Table(name = "profil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profil implements Serializable {

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
    @Column(name = "createdby", nullable = false)
    private String createdby;

    @OneToMany(mappedBy = "profil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonnePhysique> personnePhysiques = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("profils")
    private Organisation organisation;

    @JsonIgnore
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "profil_authority",
        joinColumns = @JoinColumn(name="profils_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="authority_name", referencedColumnName="name"))
    private Set<Authority> authorities = new HashSet<>();

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

    public Profil nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Profil description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PersonnePhysique> getPersonnePhysiques() {
        return personnePhysiques;
    }

    public Profil personnePhysiques(Set<PersonnePhysique> personnePhysiques) {
        this.personnePhysiques = personnePhysiques;
        return this;
    }

    public Profil addPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysiques.add(personnePhysique);
        personnePhysique.setProfil(this);
        return this;
    }

    public Profil removePersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysiques.remove(personnePhysique);
        personnePhysique.setProfil(null);
        return this;
    }

    public void setPersonnePhysiques(Set<PersonnePhysique> personnePhysiques) {
        this.personnePhysiques = personnePhysiques;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public Profil organisation(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> roles) {
        this.authorities = roles;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profil)) {
            return false;
        }
        return id != null && id.equals(((Profil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profil{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
