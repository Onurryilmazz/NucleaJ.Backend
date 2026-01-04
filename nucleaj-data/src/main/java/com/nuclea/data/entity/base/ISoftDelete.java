package com.nuclea.data.entity.base;

import java.time.LocalDateTime;

/**
 * Interface for soft delete functionality.
 * Entities implementing this interface will not be physically deleted from database,
 * instead they will be marked as deleted with a timestamp.
 */
public interface ISoftDelete {
    LocalDateTime getDeletedDate();
    void setDeletedDate(LocalDateTime deletedDate);

    default boolean isDeleted() {
        return getDeletedDate() != null;
    }

    default void markAsDeleted() {
        setDeletedDate(LocalDateTime.now());
    }

    default void undoDelete() {
        setDeletedDate(null);
    }
}
