package com.travellingfreak.itinerary.api.dataproviders.model.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Date: 7/19/2020 Time: 9:38 AM
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public class SoftDeleteWithAudit extends SoftDelete {
  @Column(name = "created_by")
  @CreatedBy
  private String createdBy;

  @Column(name = "created_date")
  @CreatedDate
  private LocalDateTime createdDate;

  @Column(name = "last_modified_by")
  @LastModifiedBy
  private String lastModifiedBy;

  @Column(name = "last_modified_date")
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public SoftDeleteWithAudit() {
    super();
    createdDate = LocalDateTime.now();
  }
}
