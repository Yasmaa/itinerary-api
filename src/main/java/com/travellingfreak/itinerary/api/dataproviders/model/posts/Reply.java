package com.travellingfreak.itinerary.api.dataproviders.model.posts;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Date: 7/19/2020 Time: 2:46 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "replies")
@Data
public class Reply extends Audit {
  @Id
  @GeneratedValue
  private long id;

  private String message;
  
  @ManyToOne(fetch=FetchType.LAZY)
  private Post post;
  

  
  /*
  @ManyToMany
  @JoinTable(
      name = "reply_mentions",
      joinColumns = @JoinColumn(name = "reply_id"),
      inverseJoinColumns = @JoinColumn(name = "account_id")
  )
  private List<Account> mentions;
  
  */
}

