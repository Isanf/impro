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
 * Criteria class for the {@link com.supernettechnologie.impro.domain.Pay} entity. This class is used
 * in {@link com.supernettechnologie.impro.web.rest.PayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter iso;

    private StringFilter name;

    private StringFilter nicename;

    private StringFilter iso3;

    private IntegerFilter numcode;

    private IntegerFilter phonecode;

    private LongFilter docIdentificationPPId;

    private LongFilter docIdentificationPMId;

    public PayCriteria() {
    }

    public PayCriteria(PayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.iso = other.iso == null ? null : other.iso.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.nicename = other.nicename == null ? null : other.nicename.copy();
        this.iso3 = other.iso3 == null ? null : other.iso3.copy();
        this.numcode = other.numcode == null ? null : other.numcode.copy();
        this.phonecode = other.phonecode == null ? null : other.phonecode.copy();
        this.docIdentificationPPId = other.docIdentificationPPId == null ? null : other.docIdentificationPPId.copy();
        this.docIdentificationPMId = other.docIdentificationPMId == null ? null : other.docIdentificationPMId.copy();
    }

    @Override
    public PayCriteria copy() {
        return new PayCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIso() {
        return iso;
    }

    public void setIso(StringFilter iso) {
        this.iso = iso;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getNicename() {
        return nicename;
    }

    public void setNicename(StringFilter nicename) {
        this.nicename = nicename;
    }

    public StringFilter getIso3() {
        return iso3;
    }

    public void setIso3(StringFilter iso3) {
        this.iso3 = iso3;
    }

    public IntegerFilter getNumcode() {
        return numcode;
    }

    public void setNumcode(IntegerFilter numcode) {
        this.numcode = numcode;
    }

    public IntegerFilter getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(IntegerFilter phonecode) {
        this.phonecode = phonecode;
    }

    public LongFilter getDocIdentificationPPId() {
        return docIdentificationPPId;
    }

    public void setDocIdentificationPPId(LongFilter docIdentificationPPId) {
        this.docIdentificationPPId = docIdentificationPPId;
    }

    public LongFilter getDocIdentificationPMId() {
        return docIdentificationPMId;
    }

    public void setDocIdentificationPMId(LongFilter docIdentificationPMId) {
        this.docIdentificationPMId = docIdentificationPMId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PayCriteria that = (PayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(iso, that.iso) &&
            Objects.equals(name, that.name) &&
            Objects.equals(nicename, that.nicename) &&
            Objects.equals(iso3, that.iso3) &&
            Objects.equals(numcode, that.numcode) &&
            Objects.equals(phonecode, that.phonecode) &&
            Objects.equals(docIdentificationPPId, that.docIdentificationPPId) &&
            Objects.equals(docIdentificationPMId, that.docIdentificationPMId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        iso,
        name,
        nicename,
        iso3,
        numcode,
        phonecode,
        docIdentificationPPId,
        docIdentificationPMId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (iso != null ? "iso=" + iso + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (nicename != null ? "nicename=" + nicename + ", " : "") +
                (iso3 != null ? "iso3=" + iso3 + ", " : "") +
                (numcode != null ? "numcode=" + numcode + ", " : "") +
                (phonecode != null ? "phonecode=" + phonecode + ", " : "") +
                (docIdentificationPPId != null ? "docIdentificationPPId=" + docIdentificationPPId + ", " : "") +
                (docIdentificationPMId != null ? "docIdentificationPMId=" + docIdentificationPMId + ", " : "") +
            "}";
    }

}
