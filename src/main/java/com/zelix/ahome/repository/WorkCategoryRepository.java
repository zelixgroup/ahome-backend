package com.zelix.ahome.repository;

import com.zelix.ahome.domain.WorkCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkCategoryRepository extends JpaRepository<WorkCategory, Long> {
}
