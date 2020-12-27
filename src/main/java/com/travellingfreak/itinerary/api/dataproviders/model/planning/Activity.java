package com.travellingfreak.itinerary.api.dataproviders.model.planning;

import com.travellingfreak.itinerary.api.dataproviders.model.base.Audit;
import com.travellingfreak.itinerary.api.dataproviders.model.budget.Cost;
import com.travellingfreak.itinerary.api.dataproviders.model.budget.Pass;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.Category;
import com.travellingfreak.itinerary.api.dataproviders.model.enums.SubCategory;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Attachment;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.activities.Like;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.booking.BookingReference;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Bucket;
import com.travellingfreak.itinerary.api.dataproviders.model.planning.buckets.Day;
import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 10:13 AM
 */
@Entity
@Table(name = "activities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends Audit {
  @Id
  @GeneratedValue
  private long id;

  private String gate;

  @ManyToOne(fetch = FetchType.LAZY)
  private Travel travel;

  @ManyToOne(fetch = FetchType.LAZY)
  private Day day;

  @ManyToOne(fetch = FetchType.LAZY)
  private Bucket bucket;

  @Column(name = "bucket_position")
  private Integer bucketPosition;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Category category;

  @Enumerated(EnumType.STRING)
  @NotNull
  private SubCategory subCategory;

  @NotNull
  private String title;

  @OneToOne
  private Post post;

  @OneToOne
  private Cost price;

  @OneToOne
  private Pass pass;

  private LocalTime startTime;

  private LocalTime endTime;

  @Column(name = "is_locked")
  private boolean locked;

  @ManyToOne
  private Location startLocation;

  @ManyToOne
  private Location endLocation;

  @ManyToOne
  private BookingReference bookingRef;

  @Column(name ="flight_number")
  private String flightNumber;

  @Column(name ="distance_covered")
  private Integer distanceCovered;


  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "activity_locations",
      joinColumns = @JoinColumn(name = "activity_id"),
      inverseJoinColumns = @JoinColumn(name = "location_id")
  )
  private List<Location> locationRefs = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY)
  private List<Cost> fees = new ArrayList<>();

  public void addLocation(final Location location) {
    this.locationRefs.add(location);
  }
  public void removeLocation(final Location location) {
    this.locationRefs.remove(location);
  }

  public void addFee(final Cost cost) {
    this.fees.add(cost);
    cost.setParentActivity(this);
    cost.setTravel(getTravel());
  }
  public void removeFee(final Cost cost) {
    this.fees.remove(cost);
    cost.setParentActivity(null);
    cost.setTravel(null);
  }




  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private List<Like> reactions = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  private List<Attachment> images = new ArrayList<>();

  public void like(final String memberId) {
    final var like = new Like();
    like.setCreatedBy(memberId);
    like.setActivity(this);
    like.setThumbsUp(true);
    this.reactions.add(like);
  }

  public void dislike(final String memberId) {
    final var like = new Like();
    like.setCreatedBy(memberId);
    like.setActivity(this);
    like.setThumbsUp(false);
    this.reactions.add(like);
  }

  public void addAttachment(final Attachment attachment) {
    this.images.add(attachment);
    attachment.setActivity(this);
  }

  public void removeAttachment(final Attachment attachment) {
    this.images.remove(attachment);
    attachment.setActivity(null);
  }


}
