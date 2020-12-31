package com.re.internship.platform.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "university")
    private String university;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "profile")
    private String profile;

    @Column(name = "year_of_study")
    private Integer yearOfStudy;

    @Column(name = "cv_path")
    private String cvPath;

    @Column(name = "observations")
    private String observations;

    @Lob
    @Column(name = "cv_document")
    private byte[] cvDocument;

    @Column(name = "cv_document_content_type")
    private String cvDocumentContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Student() {}

    public Student(String university, String faculty, String profile, Integer yearOfStudy, String cvPath, String observations, byte[] cvDocument, String cvDocumentContentType, User user) {
        this.university = university;
        this.faculty = faculty;
        this.profile = profile;
        this.yearOfStudy = yearOfStudy;
        this.cvPath = cvPath;
        this.observations = observations;
        this.cvDocument = cvDocument;
        this.cvDocumentContentType = cvDocumentContentType;
        this.user = user;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public Student university(String university) {
        this.university = university;
        return this;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public Student faculty(String faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProfile() {
        return profile;
    }

    public Student profile(String profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getYearOfStudy() {
        return yearOfStudy;
    }

    public Student yearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
        return this;
    }

    public void setYearOfStudy(Integer yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getCvPath() {
        return cvPath;
    }

    public Student cvPath(String cvPath) {
        this.cvPath = cvPath;
        return this;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }

    public String getObservations() {
        return observations;
    }

    public Student observations(String observations) {
        this.observations = observations;
        return this;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public byte[] getCvDocument() {
        return cvDocument;
    }

    public Student cvDocument(byte[] cvDocument) {
        this.cvDocument = cvDocument;
        return this;
    }

    public void setCvDocument(byte[] cvDocument) {
        this.cvDocument = cvDocument;
    }

    public String getCvDocumentContentType() {
        return cvDocumentContentType;
    }

    public Student cvDocumentContentType(String cvDocumentContentType) {
        this.cvDocumentContentType = cvDocumentContentType;
        return this;
    }

    public void setCvDocumentContentType(String cvDocumentContentType) {
        this.cvDocumentContentType = cvDocumentContentType;
    }

    public User getUser() {
        return user;
    }

    public Student user(User user) {
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
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", university='" + getUniversity() + "'" +
            ", faculty='" + getFaculty() + "'" +
            ", profile='" + getProfile() + "'" +
            ", yearOfStudy=" + getYearOfStudy() +
            ", cvPath='" + getCvPath() + "'" +
            ", observations='" + getObservations() + "'" +
            ", cvDocument='" + getCvDocument() + "'" +
            ", cvDocumentContentType='" + getCvDocumentContentType() + "'" +
            "}";
    }
}
