package com.zelix.ahome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.zelix.ahome.domain.enumeration.WorkRequestStatus;

/**
 * A WorkRequestStatusChange.
 */
@Entity
@Table(name = "work_request_status_change")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkRequestStatusChange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status")
    private WorkRequestStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private WorkRequestStatus newStatus;

    @Column(name = "date_time_of_status_change")
    private ZonedDateTime dateTimeOfStatusChange;

    @ManyToOne
    @JsonIgnoreProperties(value = "statusChanges", allowSetters = true)
    private WorkRequest workRequest;

    @ManyToOne
    @JsonIgnoreProperties(value = "statusChanges", allowSetters = true)
    private WorkRequest workRequest;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkRequestStatus getOldStatus() {
        return oldStatus;
    }

    public WorkRequestStatusChange oldStatus(WorkRequestStatus oldStatus) {
        this.oldStatus = oldStatus;
        return this;
    }

    public void setOldStatus(WorkRequestStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public WorkRequestStatus getNewStatus() {
        return newStatus;
    }

    public WorkRequestStatusChange newStatus(WorkRequestStatus newStatus) {
        this.newStatus = newStatus;
        return this;
    }

    public void setNewStatus(WorkRequestStatus newStatus) {
        this.newStatus = newStatus;
    }

    public ZonedDateTime getDateTimeOfStatusChange() {
        return dateTimeOfStatusChange;
    }

    public WorkRequestStatusChange dateTimeOfStatusChange(ZonedDateTime dateTimeOfStatusChange) {
        this.dateTimeOfStatusChange = dateTimeOfStatusChange;
        return this;
    }

    public void setDateTimeOfStatusChange(ZonedDateTime dateTimeOfStatusChange) {
        this.dateTimeOfStatusChange = dateTimeOfStatusChange;
    }

    public WorkRequest getWorkRequest() {
        return workRequest;
    }

    public WorkRequestStatusChange workRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
        return this;
    }

    public void setWorkRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
    }

    public WorkRequest getWorkRequest() {
        return workRequest;
    }

    public WorkRequestStatusChange workRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
        return this;
    }

    public void setWorkRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkRequestStatusChange)) {
            return false;
        }
        return id != null && id.equals(((WorkRequestStatusChange) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkRequestStatusChange{" +
            "id=" + getId() +
            ", oldStatus='" + getOldStatus() + "'" +
            ", newStatus='" + getNewStatus() + "'" +
            ", dateTimeOfStatusChange='" + getDateTimeOfStatusChange() + "'" +
            "}";
    }
}
