package com.re.internship.platform.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "domain_of_activity")
    private String domainOfActivity;

    @Column(name = "technologies")
    private String technologies;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "observations")
    private String observations;

    @Lob
    @Column(name = "presentation")
    private byte[] presentation;

    @Column(name = "presentation_content_type")
    private String presentationContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomainOfActivity() {
        return domainOfActivity;
    }

    public Company domainOfActivity(String domainOfActivity) {
        this.domainOfActivity = domainOfActivity;
        return this;
    }

    public void setDomainOfActivity(String domainOfActivity) {
        this.domainOfActivity = domainOfActivity;
    }

    public String getTechnologies() {
        return technologies;
    }

    public Company technologies(String technologies) {
        this.technologies = technologies;
        return this;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getContact() {
        return contact;
    }

    public Company contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public Company address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getObservations() {
        return observations;
    }

    public Company observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public byte[] getPresentation() {
        return presentation;
    }

    public Company presentation(byte[] presentation) {
        this.presentation = presentation;
        return this;
    }

    public void setPresentation(byte[] presentation) {
        this.presentation = presentation;
    }

    public String getPresentationContentType() {
        return presentationContentType;
    }

    public Company presentationContentType(String presentationContentType) {
        this.presentationContentType = presentationContentType;
        return this;
    }

    public void setPresentationContentType(String presentationContentType) {
        this.presentationContentType = presentationContentType;
    }

    public User getUser() {
        return user;
    }

    public Company user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", domainOfActivity='" + getDomainOfActivity() + "'" +
            ", technologies='" + getTechnologies() + "'" +
            ", contact='" + getContact() + "'" +
            ", address='" + getAddress() + "'" +
            ", observations='" + getObservations() + "'" +
            ", presentation='" + getPresentation() + "'" +
            ", presentationContentType='" + getPresentationContentType() + "'" +
            "}";
    }
}
