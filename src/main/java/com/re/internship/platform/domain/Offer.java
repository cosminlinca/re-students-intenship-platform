package com.re.internship.platform.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_name")
    private String positionName;

    @Column(name = "program_duration_in_weeks")
    private Integer programDurationInWeeks;

    @Column(name = "required_skills")
    private String requiredSkills;

    @Column(name = "technologies")
    private String technologies;

    @Column(name = "details")
    private String details;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "observations")
    private String observations;

    @Column(name = "domain")
    private String domain;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "cover_image_path")
    private String coverImagePath;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public Offer positionName(String positionName) {
        this.positionName = positionName;
        return this;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getProgramDurationInWeeks() {
        return programDurationInWeeks;
    }

    public Offer programDurationInWeeks(Integer programDurationInWeeks) {
        this.programDurationInWeeks = programDurationInWeeks;
        return this;
    }

    public void setProgramDurationInWeeks(Integer programDurationInWeeks) {
        this.programDurationInWeeks = programDurationInWeeks;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public Offer requiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
        return this;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getTechnologies() {
        return technologies;
    }

    public Offer technologies(String technologies) {
        this.technologies = technologies;
        return this;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getDetails() {
        return details;
    }

    public Offer details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean isPaid() {
        return paid;
    }

    public Offer paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getObservations() {
        return observations;
    }

    public Offer observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDomain() {
        return domain;
    }

    public Offer domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Offer companyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public Offer coverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
        return this;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", positionName='" + getPositionName() + "'" +
            ", programDurationInWeeks=" + getProgramDurationInWeeks() +
            ", requiredSkills='" + getRequiredSkills() + "'" +
            ", technologies='" + getTechnologies() + "'" +
            ", details='" + getDetails() + "'" +
            ", paid='" + isPaid() + "'" +
            ", observations='" + getObservations() + "'" +
            ", domain='" + getDomain() + "'" +
            ", companyId=" + getCompanyId() +
            ", coverImagePath='" + getCoverImagePath() + "'" +
            "}";
    }
}
