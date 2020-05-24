package com.zelix.ahome.repository;

import com.zelix.ahome.domain.WorkRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkRequestRepository extends JpaRepository<WorkRequest, Long> {
}
