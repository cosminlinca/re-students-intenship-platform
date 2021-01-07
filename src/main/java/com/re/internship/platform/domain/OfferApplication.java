package com.re.internship.platform.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A OfferApplication.
 */
@Entity
@Table(name = "offer_application")
public class OfferApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_student")
    private Long idStudent;

    @Column(name = "id_offer")
    private Long idOffer;

    @Lob
    @Column(name = "attached_cv")
    private byte[] attachedCV;

    @Column(name = "attached_cv_content_type")
    private String attachedCVContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdStudent() {
        return idStudent;
    }

    public OfferApplication idStudent(Long idStudent) {
        this.idStudent = idStudent;
        return this;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public Long getIdOffer() {
        return idOffer;
    }

    public OfferApplication idOffer(Long idOffer) {
        this.idOffer = idOffer;
        return this;
    }

    public void setIdOffer(Long idOffer) {
        this.idOffer = idOffer;
    }

    public byte[] getAttachedCV() {
        return attachedCV;
    }

    public OfferApplication attachedCV(byte[] attachedCV) {
        this.attachedCV = attachedCV;
        return this;
    }

    public void setAttachedCV(byte[] attachedCV) {
        this.attachedCV = attachedCV;
    }

    public String getAttachedCVContentType() {
        return attachedCVContentType;
    }

    public OfferApplication attachedCVContentType(String attachedCVContentType) {
        this.attachedCVContentType = attachedCVContentType;
        return this;
    }

    public void setAttachedCVContentType(String attachedCVContentType) {
        this.attachedCVContentType = attachedCVContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferApplication)) {
            return false;
        }
        return id != null && id.equals(((OfferApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfferApplication{" +
            "id=" + getId() +
            ", idStudent=" + getIdStudent() +
            ", idOffer=" + getIdOffer() +
            ", attachedCV='" + getAttachedCV() + "'" +
            ", attachedCVContentType='" + getAttachedCVContentType() + "'" +
            "}";
    }
}
