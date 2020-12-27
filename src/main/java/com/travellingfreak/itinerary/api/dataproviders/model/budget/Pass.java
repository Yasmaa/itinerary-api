package com.travellingfreak.itinerary.api.dataproviders.model.budget;

import com.travellingfreak.itinerary.api.dataproviders.model.base.SoftDelete;

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/18/2020 Time: 10:39 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "Pass")
@Table(name = "pass_entity")
@SQLDelete(sql =
               "UPDATE Pass " +
               "SET deleted = true " +
               "WHERE id = ?")
@Loader(namedQuery = "findPassById")
@NamedQuery(name = "findPassById", query =
    "SELECT p "+
    "FROM Pass p " +
    "WHERE " +
    "      p.id = ?1 AND "+
    "      p.deleted = false")
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Pass extends SoftDelete {
  @Id
  @GeneratedValue
  private long id;

  private String name;

  @Column(name = "cover_image")
  private String coverImage;

  @OneToMany(fetch = FetchType.EAGER)
  private List<BuyOption> buyOptions = new ArrayList<>();

  public void addBuyOption(final BuyOption option) {
    buyOptions.add(option);
    option.setPass(this);
  }

  public void removeBuyOption(final BuyOption option) {
    buyOptions.remove(option);
    option.setPass(null);
  }

}
