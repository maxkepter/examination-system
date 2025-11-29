package com.examination_system.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface for soft-delete functionality
 * Technical component to be used by domain repositories
 */
@NoRepositoryBean
public interface SoftDeleteRepository<T, ID> extends JpaRepository<T, ID> {
    @Query("SELECT e FROM #{#entityName} e WHERE e.isActive = true")
    List<T> findAllActive();

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.isActive = true")
    Optional<T> findActiveById(ID id);

    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.id IN :ids AND e.isActive = true")
    long countByIdIn(Iterable<ID> ids);
}
