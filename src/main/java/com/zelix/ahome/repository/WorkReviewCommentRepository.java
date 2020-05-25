package com.zelix.ahome.repository;

import com.zelix.ahome.domain.WorkReviewComment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the WorkReviewComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkReviewCommentRepository extends JpaRepository<WorkReviewComment, Long> {

    @Query("select workReviewComment from WorkReviewComment workReviewComment where workReviewComment.user.login = ?#{principal.username}")
    List<WorkReviewComment> findByUserIsCurrentUser();
}
