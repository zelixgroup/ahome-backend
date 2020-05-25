package com.zelix.ahome.repository;

import com.zelix.ahome.domain.IdDocument;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IdDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdDocumentRepository extends JpaRepository<IdDocument, Long> {
}
