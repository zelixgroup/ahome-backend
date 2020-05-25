package com.zelix.ahome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.zelix.ahome.domain.enumeration.WorkRequestMagnitude;

import com.zelix.ahome.domain.enumeration.WorkRequestStatus;

/**
 * A WorkRequest.
 */
@Entity
@Table(name = "work_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date_time")
    private ZonedDateTime creationDateTime;

    @Column(name = "for_mysef")
    private Boolean forMysef;

    @Column(name = "construction_site")
    private Boolean constructionSite;

    @Column(name = "mediator_percentage")
    private Integer mediatorPercentage;

    @Column(name = "detailed_description")
    private String detailedDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "magnitude")
    private WorkRequestMagnitude magnitude;

    @Column(name = "estimated_work_fees", precision = 21, scale = 2)
    private BigDecimal estimatedWorkFees;

    @Column(name = "planned_start_date_time")
    private ZonedDateTime plannedStartDateTime;

    @Column(name = "planned_end_date_time")
    private ZonedDateTime plannedEndDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WorkRequestStatus status;

    @OneToMany(mappedBy = "workRequest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<WorkRequestStatusChange> statusChanges = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "workRequests", allowSetters = true)
    private Work work;

    @ManyToOne
    @JsonIgnoreProperties(value = "workRequests", allowSetters = true)
    private AppUser user;

    @ManyToOne
    @JsonIgnoreProperties(value = "workRequests", allowSetters = true)
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public WorkRequest creationDateTime(ZonedDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
        return this;
    }

    public void setCreationDateTime(ZonedDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Boolean isForMysef() {
        return forMysef;
    }

    public WorkRequest forMysef(Boolean forMysef) {
        this.forMysef = forMysef;
        return this;
    }

    public void setForMysef(Boolean forMysef) {
        this.forMysef = forMysef;
    }

    public Boolean isConstructionSite() {
        return constructionSite;
    }

    public WorkRequest constructionSite(Boolean constructionSite) {
        this.constructionSite = constructionSite;
        return this;
    }

    public void setConstructionSite(Boolean constructionSite) {
        this.constructionSite = constructionSite;
    }

    public Integer getMediatorPercentage() {
        return mediatorPercentage;
    }

    public WorkRequest mediatorPercentage(Integer mediatorPercentage) {
        this.mediatorPercentage = mediatorPercentage;
        return this;
    }

    public void setMediatorPercentage(Integer mediatorPercentage) {
        this.mediatorPercentage = mediatorPercentage;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public WorkRequest detailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
        return this;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public WorkRequestMagnitude getMagnitude() {
        return magnitude;
    }

    public WorkRequest magnitude(WorkRequestMagnitude magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    public void setMagnitude(WorkRequestMagnitude magnitude) {
        this.magnitude = magnitude;
    }

    public BigDecimal getEstimatedWorkFees() {
        return estimatedWorkFees;
    }

    public WorkRequest estimatedWorkFees(BigDecimal estimatedWorkFees) {
        this.estimatedWorkFees = estimatedWorkFees;
        return this;
    }

    public void setEstimatedWorkFees(BigDecimal estimatedWorkFees) {
        this.estimatedWorkFees = estimatedWorkFees;
    }

    public ZonedDateTime getPlannedStartDateTime() {
        return plannedStartDateTime;
    }

    public WorkRequest plannedStartDateTime(ZonedDateTime plannedStartDateTime) {
        this.plannedStartDateTime = plannedStartDateTime;
        return this;
    }

    public void setPlannedStartDateTime(ZonedDateTime plannedStartDateTime) {
        this.plannedStartDateTime = plannedStartDateTime;
    }

    public ZonedDateTime getPlannedEndDateTime() {
        return plannedEndDateTime;
    }

    public WorkRequest plannedEndDateTime(ZonedDateTime plannedEndDateTime) {
        this.plannedEndDateTime = plannedEndDateTime;
        return this;
    }

    public void setPlannedEndDateTime(ZonedDateTime plannedEndDateTime) {
        this.plannedEndDateTime = plannedEndDateTime;
    }

    public WorkRequestStatus getStatus() {
        return status;
    }

    public WorkRequest status(WorkRequestStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WorkRequestStatus status) {
        this.status = status;
    }

    public Set<WorkRequestStatusChange> getStatusChanges() {
        return statusChanges;
    }

    public WorkRequest statusChanges(Set<WorkRequestStatusChange> workRequestStatusChanges) {
        this.statusChanges = workRequestStatusChanges;
        return this;
    }

    public WorkRequest addStatusChanges(WorkRequestStatusChange workRequestStatusChange) {
        this.statusChanges.add(workRequestStatusChange);
        workRequestStatusChange.setWorkRequest(this);
        return this;
    }

    public WorkRequest removeStatusChanges(WorkRequestStatusChange workRequestStatusChange) {
        this.statusChanges.remove(workRequestStatusChange);
        workRequestStatusChange.setWorkRequest(null);
        return this;
    }

    public void setStatusChanges(Set<WorkRequestStatusChange> workRequestStatusChanges) {
        this.statusChanges = workRequestStatusChanges;
    }

    public Work getWork() {
        return work;
    }

    public WorkRequest work(Work work) {
        this.work = work;
        return this;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public AppUser getUser() {
        return user;
    }

    public WorkRequest user(AppUser appUser) {
        this.user = appUser;
        return this;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
    }

    public Address getAddress() {
        return address;
    }

    public WorkRequest address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkRequest)) {
            return false;
        }
        return id != null && id.equals(((WorkRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkRequest{" +
            "id=" + getId() +
            ", creationDateTime='" + getCreationDateTime() + "'" +
            ", forMysef='" + isForMysef() + "'" +
            ", constructionSite='" + isConstructionSite() + "'" +
            ", mediatorPercentage=" + getMediatorPercentage() +
            ", detailedDescription='" + getDetailedDescription() + "'" +
            ", magnitude='" + getMagnitude() + "'" +
            ", estimatedWorkFees=" + getEstimatedWorkFees() +
            ", plannedStartDateTime='" + getPlannedStartDateTime() + "'" +
            ", plannedEndDateTime='" + getPlannedEndDateTime() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
