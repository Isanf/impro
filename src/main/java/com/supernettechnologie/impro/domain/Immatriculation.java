package com.supernettechnologie.impro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Immatriculation.
 */
@Entity
@Table(name = "immatriculation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Immatriculation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "date_immatriculation")
    private ZonedDateTime dateImmatriculation;

    @OneToOne
    @JoinColumn(unique = true)
    private CertificatImmatriculation certificatImmatriculation;

    @ManyToOne
    @JsonIgnoreProperties("immatriculations")
    private Organisation organisation;

    @ManyToOne
    @JsonIgnoreProperties("immatriculations")
    private PersonnePhysique personnePhysique;

    @ManyToOne
    @JsonIgnoreProperties("immatriculations")
    private PersonneMorale personneMorale;

    @ManyToOne
    @JsonIgnoreProperties("immatriculations")
    private Vehicule vehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Immatriculation numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ZonedDateTime getDateImmatriculation() {
        return dateImmatriculation;
    }

    public Immatriculation dateImmatriculation(ZonedDateTime dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
        return this;
    }

    public void setDateImmatriculation(ZonedDateTime dateImmatriculation) {
        this.dateImmatriculation = dateImmatriculation;
    }

    public CertificatImmatriculation getCertificatImmatriculation() {
        return certificatImmatriculation;
    }

    public Immatriculation certificatImmatriculation(CertificatImmatriculation certificatImmatriculation) {
        this.certificatImmatriculation = certificatImmatriculation;
        return this;
    }

    public void setCertificatImmatriculation(CertificatImmatriculation certificatImmatriculation) {
        this.certificatImmatriculation = certificatImmatriculation;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public Immatriculation organisation(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public PersonnePhysique getPersonnePhysique() {
        return personnePhysique;
    }

    public Immatriculation personnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
        return this;
    }

    public void setPersonnePhysique(PersonnePhysique personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    public PersonneMorale getPersonneMorale() {
        return personneMorale;
    }

    public Immatriculation personneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
        return this;
    }

    public void setPersonneMorale(PersonneMorale personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public Immatriculation vehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
        return this;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Immatriculation)) {
            return false;
        }
        return id != null && id.equals(((Immatriculation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Immatriculation{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", dateImmatriculation='" + getDateImmatriculation() + "'" +
            "}";
    }
}
