package com.zelix.ahome.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A IdDocumentType.
 */
@Entity
@Table(name = "id_document_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IdDocumentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    @Column(name = "need_verso")
    private Boolean needVerso;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public IdDocumentType label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public IdDocumentType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isNeedVerso() {
        return needVerso;
    }

    public IdDocumentType needVerso(Boolean needVerso) {
        this.needVerso = needVerso;
        return this;
    }

    public void setNeedVerso(Boolean needVerso) {
        this.needVerso = needVerso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdDocumentType)) {
            return false;
        }
        return id != null && id.equals(((IdDocumentType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IdDocumentType{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", needVerso='" + isNeedVerso() + "'" +
            "}";
    }
}
