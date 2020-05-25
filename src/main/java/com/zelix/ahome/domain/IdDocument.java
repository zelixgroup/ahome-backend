package com.zelix.ahome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A IdDocument.
 */
@Entity
@Table(name = "id_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IdDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "id_document_recto")
    private byte[] idDocumentRecto;

    @Column(name = "id_document_recto_content_type")
    private String idDocumentRectoContentType;

    @Lob
    @Column(name = "id_document_verso")
    private byte[] idDocumentVerso;

    @Column(name = "id_document_verso_content_type")
    private String idDocumentVersoContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "idDocuments", allowSetters = true)
    private IdDocumentType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "idDocuments", allowSetters = true)
    private Worker worker;

    @ManyToOne
    @JsonIgnoreProperties(value = "idDocuments", allowSetters = true)
    private Worker worker;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getIdDocumentRecto() {
        return idDocumentRecto;
    }

    public IdDocument idDocumentRecto(byte[] idDocumentRecto) {
        this.idDocumentRecto = idDocumentRecto;
        return this;
    }

    public void setIdDocumentRecto(byte[] idDocumentRecto) {
        this.idDocumentRecto = idDocumentRecto;
    }

    public String getIdDocumentRectoContentType() {
        return idDocumentRectoContentType;
    }

    public IdDocument idDocumentRectoContentType(String idDocumentRectoContentType) {
        this.idDocumentRectoContentType = idDocumentRectoContentType;
        return this;
    }

    public void setIdDocumentRectoContentType(String idDocumentRectoContentType) {
        this.idDocumentRectoContentType = idDocumentRectoContentType;
    }

    public byte[] getIdDocumentVerso() {
        return idDocumentVerso;
    }

    public IdDocument idDocumentVerso(byte[] idDocumentVerso) {
        this.idDocumentVerso = idDocumentVerso;
        return this;
    }

    public void setIdDocumentVerso(byte[] idDocumentVerso) {
        this.idDocumentVerso = idDocumentVerso;
    }

    public String getIdDocumentVersoContentType() {
        return idDocumentVersoContentType;
    }

    public IdDocument idDocumentVersoContentType(String idDocumentVersoContentType) {
        this.idDocumentVersoContentType = idDocumentVersoContentType;
        return this;
    }

    public void setIdDocumentVersoContentType(String idDocumentVersoContentType) {
        this.idDocumentVersoContentType = idDocumentVersoContentType;
    }

    public IdDocumentType getType() {
        return type;
    }

    public IdDocument type(IdDocumentType idDocumentType) {
        this.type = idDocumentType;
        return this;
    }

    public void setType(IdDocumentType idDocumentType) {
        this.type = idDocumentType;
    }

    public Worker getWorker() {
        return worker;
    }

    public IdDocument worker(Worker worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public IdDocument worker(Worker worker) {
        this.worker = worker;
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdDocument)) {
            return false;
        }
        return id != null && id.equals(((IdDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdDocument{" +
            "id=" + getId() +
            ", idDocumentRecto='" + getIdDocumentRecto() + "'" +
            ", idDocumentRectoContentType='" + getIdDocumentRectoContentType() + "'" +
            ", idDocumentVerso='" + getIdDocumentVerso() + "'" +
            ", idDocumentVersoContentType='" + getIdDocumentVersoContentType() + "'" +
            "}";
    }
}
