package com.re.internship.platform.service.dto;

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
 * Criteria class for the {@link com.re.internship.platform.domain.Offer} entity. This class is used
 * in {@link com.re.internship.platform.web.rest.OfferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OfferCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter positionName;

    private IntegerFilter programDurationInWeeks;

    private StringFilter requiredSkills;

    private StringFilter technologies;

    private StringFilter details;

    private BooleanFilter paid;

    private StringFilter observations;

    private StringFilter domain;

    private LongFilter companyId;

    private StringFilter coverImagePath;

    public OfferCriteria() {
    }

    public OfferCriteria(OfferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.positionName = other.positionName == null ? null : other.positionName.copy();
        this.programDurationInWeeks = other.programDurationInWeeks == null ? null : other.programDurationInWeeks.copy();
        this.requiredSkills = other.requiredSkills == null ? null : other.requiredSkills.copy();
        this.technologies = other.technologies == null ? null : other.technologies.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.paid = other.paid == null ? null : other.paid.copy();
        this.observations = other.observations == null ? null : other.observations.copy();
        this.domain = other.domain == null ? null : other.domain.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.coverImagePath = other.coverImagePath == null ? null : other.coverImagePath.copy();
    }

    @Override
    public OfferCriteria copy() {
        return new OfferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPositionName() {
        return positionName;
    }

    public void setPositionName(StringFilter positionName) {
        this.positionName = positionName;
    }

    public IntegerFilter getProgramDurationInWeeks() {
        return programDurationInWeeks;
    }

    public void setProgramDurationInWeeks(IntegerFilter programDurationInWeeks) {
        this.programDurationInWeeks = programDurationInWeeks;
    }

    public StringFilter getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(StringFilter requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public StringFilter getTechnologies() {
        return technologies;
    }

    public void setTechnologies(StringFilter technologies) {
        this.technologies = technologies;
    }

    public StringFilter getDetails() {
        return details;
    }

    public void setDetails(StringFilter details) {
        this.details = details;
    }

    public BooleanFilter getPaid() {
        return paid;
    }

    public void setPaid(BooleanFilter paid) {
        this.paid = paid;
    }

    public StringFilter getObservations() {
        return observations;
    }

    public void setObservations(StringFilter observations) {
        this.observations = observations;
    }

    public StringFilter getDomain() {
        return domain;
    }

    public void setDomain(StringFilter domain) {
        this.domain = domain;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public StringFilter getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(StringFilter coverImagePath) {
        this.coverImagePath = coverImagePath;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OfferCriteria that = (OfferCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(positionName, that.positionName) &&
            Objects.equals(programDurationInWeeks, that.programDurationInWeeks) &&
            Objects.equals(requiredSkills, that.requiredSkills) &&
            Objects.equals(technologies, that.technologies) &&
            Objects.equals(details, that.details) &&
            Objects.equals(paid, that.paid) &&
            Objects.equals(observations, that.observations) &&
            Objects.equals(domain, that.domain) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(coverImagePath, that.coverImagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        positionName,
        programDurationInWeeks,
        requiredSkills,
        technologies,
        details,
        paid,
        observations,
        domain,
        companyId,
        coverImagePath
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (positionName != null ? "positionName=" + positionName + ", " : "") +
                (programDurationInWeeks != null ? "programDurationInWeeks=" + programDurationInWeeks + ", " : "") +
                (requiredSkills != null ? "requiredSkills=" + requiredSkills + ", " : "") +
                (technologies != null ? "technologies=" + technologies + ", " : "") +
                (details != null ? "details=" + details + ", " : "") +
                (paid != null ? "paid=" + paid + ", " : "") +
                (observations != null ? "observations=" + observations + ", " : "") +
                (domain != null ? "domain=" + domain + ", " : "") +
                (companyId != null ? "companyId=" + companyId + ", " : "") +
                (coverImagePath != null ? "coverImagePath=" + coverImagePath + ", " : "") +
            "}";
    }

}
