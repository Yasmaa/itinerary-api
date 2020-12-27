package com.travellingfreak.itinerary.api.core.model;

import org.springframework.data.domain.Page;

import java.util.List;

import lombok.Data;

/**
 * Date: 7/19/2020 Time: 11:49 PM
 */
@Data
public class PageResponse<T> {
  private final List<T> content;
  private final boolean last;
  private final boolean first;
  private final int totalPages;
  private final long totalElements;
  private final boolean empty;

  public PageResponse(final Page<T> page) {
    this.content = page.getContent();
    this.last = page.isLast();
    this.first = page.isFirst();
    this.empty = page.isEmpty();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
  }

}
