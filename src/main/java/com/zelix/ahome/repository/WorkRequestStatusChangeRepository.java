package com.zelix.ahome.repository;

import com.zelix.ahome.domain.WorkRequestStatusChange;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkRequestStatusChange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkRequestStatusChangeRepository extends JpaRepository<WorkRequestStatusChange, Long> {
}
