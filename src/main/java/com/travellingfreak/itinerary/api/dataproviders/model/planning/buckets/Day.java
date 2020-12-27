package com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Activity;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 9:55 AM
 */
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Day")
@Table(name = "days")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Day extends Audit {
  @Id
  @GeneratedValue
  private long id;

  @ManyToOne(fetch=FetchType.EAGER)
  private Travel travel;

  @Column(name = "day_number")
  private int number;

  @Column(name = "day_date")
  private LocalDate date;

  private String title;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private DaySettings settings;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "day_posts",
      joinColumns = @JoinColumn(name = "day_id"),
      inverseJoinColumns = @JoinColumn(name = "post_id")
  )
  private List<Post> post = new ArrayList<>();



  public void addActivity(final Activity activity) {
    activity.setDay(this);
    activity.setTravel(travel);
    
  }

  public void removeActivity(final Activity activity) {
    activity.setDay(null);
  }

  public void addSettings(final DaySettings settings) {
	    this.settings = settings;
	    settings.setDay(this);
	  }






}
