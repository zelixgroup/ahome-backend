package com.zelix.ahome.repository;

import com.zelix.ahome.domain.WorkReview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkReview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkReviewRepository extends JpaRepository<WorkReview, Long> {
}
