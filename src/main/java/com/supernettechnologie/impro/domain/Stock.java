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
 * The Stock entity.\n@author A true hipster
 */
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * numeroStock
     */
    @Column(name = "numero_stock")
    private String numeroStock;

    @Lob
    @Column(name = "fichier_stock")
    private byte[] fichierStock;

    @Column(name = "fichier_stock_content_type")
    private String fichierStockContentType;

    @Column(name = "date_stock")
    private ZonedDateTime dateStock;

    @OneToMany(mappedBy = "stock")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vehicule> vehicules = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("stocks")
    private Organisation concessionnaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroStock() {
        return numeroStock;
    }

    public Stock numeroStock(String numeroStock) {
        this.numeroStock = numeroStock;
        return this;
    }

    public void setNumeroStock(String numeroStock) {
        this.numeroStock = numeroStock;
    }

    public byte[] getFichierStock() {
        return fichierStock;
    }

    public Stock fichierStock(byte[] fichierStock) {
        this.fichierStock = fichierStock;
        return this;
    }

    public void setFichierStock(byte[] fichierStock) {
        this.fichierStock = fichierStock;
    }

    public String getFichierStockContentType() {
        return fichierStockContentType;
    }

    public Stock fichierStockContentType(String fichierStockContentType) {
        this.fichierStockContentType = fichierStockContentType;
        return this;
    }

    public void setFichierStockContentType(String fichierStockContentType) {
        this.fichierStockContentType = fichierStockContentType;
    }

    public ZonedDateTime getDateStock() {
        return dateStock;
    }

    public Stock dateStock(ZonedDateTime dateStock) {
        this.dateStock = dateStock;
        return this;
    }

    public void setDateStock(ZonedDateTime dateStock) {
        this.dateStock = dateStock;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public Stock vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public Stock addVehicules(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.setStock(this);
        return this;
    }

    public Stock removeVehicules(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.setStock(null);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Organisation getConcessionnaire() {
        return concessionnaire;
    }

    public Stock concessionnaire(Organisation organisation) {
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
        if (!(o instanceof Stock)) {
            return false;
        }
        return id != null && id.equals(((Stock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", numeroStock='" + getNumeroStock() + "'" +
            ", fichierStock='" + getFichierStock() + "'" +
            ", fichierStockContentType='" + getFichierStockContentType() + "'" +
            ", dateStock='" + getDateStock() + "'" +
            "}";
    }
}
