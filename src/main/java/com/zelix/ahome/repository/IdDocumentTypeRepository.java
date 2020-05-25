package com.zelix.ahome.repository;

import com.zelix.ahome.domain.IdDocumentType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IdDocumentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdDocumentTypeRepository extends JpaRepository<IdDocumentType, Long> {
}
