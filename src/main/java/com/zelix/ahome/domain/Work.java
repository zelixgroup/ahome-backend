package com.zelix.ahome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Work.
 */
@Entity
@Table(name = "work")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "picture")
    private String picture;

    @Column(name = "icone")
    private String icone;

    @Column(name = "price_per_hour", precision = 21, scale = 2)
    private BigDecimal pricePerHour;

    @ManyToOne
    @JsonIgnoreProperties(value = "works", allowSetters = true)
    private WorkCategory category;

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

    public Work name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Work description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public Work picture(String picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIcone() {
        return icone;
    }

    public Work icone(String icone) {
        this.icone = icone;
        return this;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public Work pricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
        return this;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public WorkCategory getCategory() {
        return category;
    }

    public Work category(WorkCategory workCategory) {
        this.category = workCategory;
        return this;
    }

    public void setCategory(WorkCategory workCategory) {
        this.category = workCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Work)) {
            return false;
        }
        return id != null && id.equals(((Work) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Work{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", picture='" + getPicture() + "'" +
            ", icone='" + getIcone() + "'" +
            ", pricePerHour=" + getPricePerHour() +
            "}";
    }
}
