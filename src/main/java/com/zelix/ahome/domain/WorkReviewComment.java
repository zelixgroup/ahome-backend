package com.zelix.ahome.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A WorkReviewComment.
 */
@Entity
@Table(name = "work_review_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkReviewComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_date_time")
    private ZonedDateTime commentDateTime;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties(value = "workReviewComments", allowSetters = true)
    private WorkReview workReview;

    @ManyToOne
    @JsonIgnoreProperties(value = "workReviewComments", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "workReviewComments", allowSetters = true)
    private WorkReview workReview;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCommentDateTime() {
        return commentDateTime;
    }

    public WorkReviewComment commentDateTime(ZonedDateTime commentDateTime) {
        this.commentDateTime = commentDateTime;
        return this;
    }

    public void setCommentDateTime(ZonedDateTime commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public String getComment() {
        return comment;
    }

    public WorkReviewComment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public WorkReview getWorkReview() {
        return workReview;
    }

    public WorkReviewComment workReview(WorkReview workReview) {
        this.workReview = workReview;
        return this;
    }

    public void setWorkReview(WorkReview workReview) {
        this.workReview = workReview;
    }

    public User getUser() {
        return user;
    }

    public WorkReviewComment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WorkReview getWorkReview() {
        return workReview;
    }

    public WorkReviewComment workReview(WorkReview workReview) {
        this.workReview = workReview;
        return this;
    }

    public void setWorkReview(WorkReview workReview) {
        this.workReview = workReview;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkReviewComment)) {
            return false;
        }
        return id != null && id.equals(((WorkReviewComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkReviewComment{" +
            "id=" + getId() +
            ", commentDateTime='" + getCommentDateTime() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
