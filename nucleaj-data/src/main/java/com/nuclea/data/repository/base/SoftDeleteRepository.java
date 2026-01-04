package com.nuclea.data.repository.base;

import com.nuclea.data.entity.base.ISoftDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Base repository interface for entities with soft delete functionality.
 * Provides methods to filter out soft-deleted records.
 *
 * @param <T> Entity type implementing ISoftDelete
 * @param <ID> Entity ID type
 */
@NoRepositoryBean
public interface SoftDeleteRepository<T extends ISoftDelete, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * Find all non-deleted entities.
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deletedDate IS NULL")
    List<T> findAllActive();

    /**
     * Find by ID excluding soft-deleted entities.
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deletedDate IS NULL")
    Optional<T> findByIdActive(@Param("id") ID id);

    /**
     * Soft delete entity by ID.
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deletedDate = :deletedDate WHERE e.id = :id")
    void softDeleteById(@Param("id") ID id, @Param("deletedDate") LocalDateTime deletedDate);

    /**
     * Restore soft-deleted entity by ID.
     */
    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deletedDate = NULL WHERE e.id = :id")
    void restoreById(@Param("id") ID id);

    /**
     * Find all soft-deleted entities.
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.deletedDate IS NOT NULL")
    List<T> findAllDeleted();
}
