package com.taskmanager.taskmanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Paginated response wrapper for task lists
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponse<T> {
    private List<T> content;
    private int page;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
