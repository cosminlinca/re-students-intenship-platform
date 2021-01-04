package com.re.internship.platform.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.re.internship.platform.domain.Company} entity. This class is used
 * in {@link com.re.internship.platform.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable, Criteria {
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter domainOfActivity;

    private StringFilter technologies;

    private StringFilter contact;

    private StringFilter address;

    private StringFilter observations;

    public CompanyCriteria() {}

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.domainOfActivity = other.domainOfActivity == null ? null : other.domainOfActivity.copy();
        this.technologies = other.technologies == null ? null : other.technologies.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.observations = other.observations == null ? null : other.observations.copy();
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getDomainOfActivity() {
        return domainOfActivity;
    }

    public void setDomainOfActivity(StringFilter domainOfActivity) {
        this.domainOfActivity = domainOfActivity;
    }

    public StringFilter getTechnologies() {
        return technologies;
    }

    public void setTechnologies(StringFilter technologies) {
        this.technologies = technologies;
    }

    public StringFilter getContact() {
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getObservations() {
        return observations;
    }

    public void setObservations(StringFilter observations) {
        this.observations = observations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyCriteria that = (CompanyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(domainOfActivity, that.domainOfActivity) &&
            Objects.equals(technologies, that.technologies) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(address, that.address) &&
            Objects.equals(observations, that.observations)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, domainOfActivity, technologies, contact, address, observations);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (domainOfActivity != null ? "domainOfActivity=" + domainOfActivity + ", " : "") +
                (technologies != null ? "technologies=" + technologies + ", " : "") +
                (contact != null ? "contact=" + contact + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (observations != null ? "observations=" + observations + ", " : "") +
            "}";
    }
}
