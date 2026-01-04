package com.nuclea.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Paged result wrapper for pagination support.
 *
 * @param <T> Data type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResult<T> {

    private List<T> data;
    private long totalRecords;
    private int pageNumber;
    private int pageSize;
    private int totalPages;

    public static <T> PagedResult<T> of(List<T> data, long totalRecords, int pageNumber, int pageSize) {
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        return PagedResult.<T>builder()
                .data(data)
                .totalRecords(totalRecords)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .build();
    }
}
