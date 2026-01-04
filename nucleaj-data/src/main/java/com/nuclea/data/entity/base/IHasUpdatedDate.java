package com.nuclea.data.entity.base;

import java.time.LocalDateTime;

/**
 * Interface for entities that track update timestamps.
 */
public interface IHasUpdatedDate {
    LocalDateTime getUpdatedDate();
    void setUpdatedDate(LocalDateTime updatedDate);
}
