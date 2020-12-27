package com.travellingfreak.itinerary.api.dataproviders.model.posts;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 2:36 PM
 */
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Post")
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Audit {
  @Id
  @GeneratedValue
  private long id;

  private String message;
  
  
  
  /*
  
  @Column(name="created_by", insertable = false, updatable = false)
  private String created_by;
  
  @Column(name="created_date", insertable = false, updatable = false)
  private LocalTime created_date;
  
  @Column(name="last_modified_date", insertable = false, updatable = false)
  private LocalTime last_modified_date;

  

  @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
  @JoinTable(
      name = "post_mentions",
      joinColumns = @JoinColumn(name = "post_id"),
      inverseJoinColumns = @JoinColumn(name = "account_id")
  )
  private List<Account> mentions = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  private List<Reply> replies = new ArrayList<>();
  */
  
  
  
}
