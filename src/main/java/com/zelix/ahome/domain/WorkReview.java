package com.zelix.ahome.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A WorkReview.
 */
@Entity
@Table(name = "work_review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_date_time")
    private ZonedDateTime reviewDateTime;

    @Column(name = "stars_number")
    private Integer starsNumber;

    @Column(name = "notes")
    private String notes;

    @OneToOne
    @JoinColumn(unique = true)
    private WorkRequest workRequest;

    @OneToMany(mappedBy = "workReview")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<WorkReviewComment> workReviewComments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getReviewDateTime() {
        return reviewDateTime;
    }

    public WorkReview reviewDateTime(ZonedDateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
        return this;
    }

    public void setReviewDateTime(ZonedDateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }

    public Integer getStarsNumber() {
        return starsNumber;
    }

    public WorkReview starsNumber(Integer starsNumber) {
        this.starsNumber = starsNumber;
        return this;
    }

    public void setStarsNumber(Integer starsNumber) {
        this.starsNumber = starsNumber;
    }

    public String getNotes() {
        return notes;
    }

    public WorkReview notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public WorkRequest getWorkRequest() {
        return workRequest;
    }

    public WorkReview workRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
        return this;
    }

    public void setWorkRequest(WorkRequest workRequest) {
        this.workRequest = workRequest;
    }

    public Set<WorkReviewComment> getWorkReviewComments() {
        return workReviewComments;
    }

    public WorkReview workReviewComments(Set<WorkReviewComment> workReviewComments) {
        this.workReviewComments = workReviewComments;
        return this;
    }

    public WorkReview addWorkReviewComments(WorkReviewComment workReviewComment) {
        this.workReviewComments.add(workReviewComment);
        workReviewComment.setWorkReview(this);
        return this;
    }

    public WorkReview removeWorkReviewComments(WorkReviewComment workReviewComment) {
        this.workReviewComments.remove(workReviewComment);
        workReviewComment.setWorkReview(null);
        return this;
    }

    public void setWorkReviewComments(Set<WorkReviewComment> workReviewComments) {
        this.workReviewComments = workReviewComments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkReview)) {
            return false;
        }
        return id != null && id.equals(((WorkReview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkReview{" +
            "id=" + getId() +
            ", reviewDateTime='" + getReviewDateTime() + "'" +
            ", starsNumber=" + getStarsNumber() +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
