package com.supernettechnologie.impro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.supernettechnologie.impro.domain.PlaqueGarage} entity. This class is used
 * in {@link com.supernettechnologie.impro.web.rest.PlaqueGarageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plaque-garages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlaqueGarageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numeroOrdre;

    private StringFilter numeroPlaque;

    private StringFilter codeQrPlaque;

    private LongFilter carteWId;

    public PlaqueGarageCriteria() {
    }

    public PlaqueGarageCriteria(PlaqueGarageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numeroOrdre = other.numeroOrdre == null ? null : other.numeroOrdre.copy();
        this.numeroPlaque = other.numeroPlaque == null ? null : other.numeroPlaque.copy();
        this.codeQrPlaque = other.codeQrPlaque == null ? null : other.codeQrPlaque.copy();
        this.carteWId = other.carteWId == null ? null : other.carteWId.copy();
    }

    @Override
    public PlaqueGarageCriteria copy() {
        return new PlaqueGarageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(StringFilter numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public StringFilter getNumeroPlaque() {
        return numeroPlaque;
    }

    public void setNumeroPlaque(StringFilter numeroPlaque) {
        this.numeroPlaque = numeroPlaque;
    }

    public StringFilter getCodeQrPlaque() {
        return codeQrPlaque;
    }

    public void setCodeQrPlaque(StringFilter codeQrPlaque) {
        this.codeQrPlaque = codeQrPlaque;
    }

    public LongFilter getCarteWId() {
        return carteWId;
    }

    public void setCarteWId(LongFilter carteWId) {
        this.carteWId = carteWId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlaqueGarageCriteria that = (PlaqueGarageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numeroOrdre, that.numeroOrdre) &&
            Objects.equals(numeroPlaque, that.numeroPlaque) &&
            Objects.equals(codeQrPlaque, that.codeQrPlaque) &&
            Objects.equals(carteWId, that.carteWId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numeroOrdre,
        numeroPlaque,
        codeQrPlaque,
        carteWId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlaqueGarageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numeroOrdre != null ? "numeroOrdre=" + numeroOrdre + ", " : "") +
                (numeroPlaque != null ? "numeroPlaque=" + numeroPlaque + ", " : "") +
                (codeQrPlaque != null ? "codeQrPlaque=" + codeQrPlaque + ", " : "") +
                (carteWId != null ? "carteWId=" + carteWId + ", " : "") +
            "}";
    }

}
