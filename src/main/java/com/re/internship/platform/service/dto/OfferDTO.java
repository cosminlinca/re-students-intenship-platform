package com.re.internship.platform.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.re.internship.platform.domain.Offer} entity.
 */
public class OfferDTO implements Serializable {
    
    private Long id;

    private String positionName;

    private Integer programDurationInWeeks;

    private String requiredSkills;

    private String technologies;

    private String details;

    private Boolean paid;

    private String observations;

    private String domain;

    private Long companyId;

    private String coverImagePath;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getProgramDurationInWeeks() {
        return programDurationInWeeks;
    }

    public void setProgramDurationInWeeks(Integer programDurationInWeeks) {
        this.programDurationInWeeks = programDurationInWeeks;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferDTO)) {
            return false;
        }

        return id != null && id.equals(((OfferDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferDTO{" +
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
