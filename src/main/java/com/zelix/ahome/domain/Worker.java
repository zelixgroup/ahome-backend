package com.zelix.ahome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Worker.
 */
@Entity
@Table(name = "worker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Lob
    @Column(name = "work_certificate")
    private byte[] workCertificate;

    @Column(name = "work_certificate_content_type")
    private String workCertificateContentType;

    @OneToMany(mappedBy = "worker")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<IdDocument> idDocuments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "workers", allowSetters = true)
    private AppUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Worker picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Worker pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public byte[] getWorkCertificate() {
        return workCertificate;
    }

    public Worker workCertificate(byte[] workCertificate) {
        this.workCertificate = workCertificate;
        return this;
    }

    public void setWorkCertificate(byte[] workCertificate) {
        this.workCertificate = workCertificate;
    }

    public String getWorkCertificateContentType() {
        return workCertificateContentType;
    }

    public Worker workCertificateContentType(String workCertificateContentType) {
        this.workCertificateContentType = workCertificateContentType;
        return this;
    }

    public void setWorkCertificateContentType(String workCertificateContentType) {
        this.workCertificateContentType = workCertificateContentType;
    }

    public Set<IdDocument> getIdDocuments() {
        return idDocuments;
    }

    public Worker idDocuments(Set<IdDocument> idDocuments) {
        this.idDocuments = idDocuments;
        return this;
    }

    public Worker addIdDocument(IdDocument idDocument) {
        this.idDocuments.add(idDocument);
        idDocument.setWorker(this);
        return this;
    }

    public Worker removeIdDocument(IdDocument idDocument) {
        this.idDocuments.remove(idDocument);
        idDocument.setWorker(null);
        return this;
    }

    public void setIdDocuments(Set<IdDocument> idDocuments) {
        this.idDocuments = idDocuments;
    }

    public AppUser getUser() {
        return user;
    }

    public Worker user(AppUser appUser) {
        this.user = appUser;
        return this;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Worker)) {
            return false;
        }
        return id != null && id.equals(((Worker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Worker{" +
            "id=" + getId() +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", workCertificate='" + getWorkCertificate() + "'" +
            ", workCertificateContentType='" + getWorkCertificateContentType() + "'" +
            "}";
    }
}
