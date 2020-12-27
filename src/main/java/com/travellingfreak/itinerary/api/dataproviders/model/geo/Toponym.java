/*
 * Copyright 2008 Marc Wick, geonames.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.travellingfreak.itinerary.api.dataproviders.model.geo;

import com.travellingfreak.itinerary.api.dataproviders.model.planning.Travel;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "Toponym")
@Table(name = "geonames")
public class Toponym {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "asciiname")
  private String asciiName;

  @Column(name = "alternatenames")
  private String alternateNames;

  @Column(name = "country")
  private String countryCode;

  private Long population;

  private Integer elevation;

  @Enumerated(EnumType.STRING)
  @Column(name = "feature_class")
  private FeatureClass featureClass;

  @Column(name = "feature_code")
  private String featureCode;

  private double latitude;

  private double longitude;

  @Column(name = "admin_level1")
  private String adminCode1;

  @Column(name = "admin_level2")
  private String adminCode2;

  @Column(name = "admin_level3")
  private String adminCode3;

  @Column(name = "admin_level4")
  private String adminCode4;

  @Column(name = "admin_level5")
  private String adminCode5;

  private String timezone;

  @ManyToMany(mappedBy = "locations") private Collection<Travel> travels;

  public Collection<Travel> getTravels() {
    return travels;
  }

  public void setTravels(Collection<Travel> travels) {
    this.travels = travels;
  }
}
