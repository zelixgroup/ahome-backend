package com.zelix.ahome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.zelix.ahome.domain.enumeration.AddressType;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AddressType type;

    @Column(name = "location")
    private String location;

    @Column(name = "geolocation")
    private String geolocation;

    @Column(name = "primary_phone_number")
    private String primaryPhoneNumber;

    @Column(name = "secondary_phone_number")
    private String secondaryPhoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private City city;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getType() {
        return type;
    }

    public Address type(AddressType type) {
        this.type = type;
        return this;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public Address location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public Address geolocation(String geolocation) {
        this.geolocation = geolocation;
        return this;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public Address primaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
        return this;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public Address secondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        return this;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Address emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public City getCity() {
        return city;
    }

    public Address city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Address appUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", location='" + getLocation() + "'" +
            ", geolocation='" + getGeolocation() + "'" +
            ", primaryPhoneNumber='" + getPrimaryPhoneNumber() + "'" +
            ", secondaryPhoneNumber='" + getSecondaryPhoneNumber() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            "}";
    }
}
