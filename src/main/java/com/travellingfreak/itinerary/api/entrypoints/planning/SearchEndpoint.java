package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.model.PageResponse;
import com.travellingfreak.itinerary.api.core.model.geo.City;
import com.travellingfreak.itinerary.api.core.model.geo.Place;
import com.travellingfreak.itinerary.api.core.planning.FindCityOrderedbyPopulationUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindPlacebyNameUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindPlacebyNamenDayUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/travel/{travelId}/search")
@Api(tags = "Search API")
@RequiredArgsConstructor
public class SearchEndpoint {
	
	private final FindCityOrderedbyPopulationUseCase findCityOrderedbyPopulation;
	private final FindPlacebyNameUseCase findPlacebyName;
	private final FindPlacebyNamenDayUseCase findPlacebyNamenDay;
  @ApiOperation(value = "Search a city by name, sorted by population desc.")
  @GetMapping(value = "/city", produces = "application/json")
  @Timed("api.search.city")
  @PreAuthorize("hasPermission(#travelId, '', 'EDITOR')")
  public PageResponse<City> searchCity(@PathVariable("travelId") final long travelId,
                                       @RequestParam("q") final String searchText,
                                       @RequestParam("page") final int page,
                                       @RequestParam("limit") final int limit) {
    
	  
	    return new PageResponse<>(findCityOrderedbyPopulation.findCitiesOrderesbyPopulation(travelId, searchText, page, limit));

	  
  }

  @ApiOperation(value = "Search a place by name")
  @GetMapping(value = "/place", produces = "application/json")
  @Timed("api.search.place")
  @PreAuthorize("hasPermission(#travelId, '', 'EDITOR')")
  public PageResponse<Place> searchPlace(@PathVariable("travelId") final long travelId,
                                       @RequestParam("q") final String searchText,
                                       @RequestParam("tag") final String tag,
                                       @RequestParam("page") final int page,
                                       @RequestParam("limit") final int limit) {
	    return new PageResponse<>(findPlacebyName.findPlacebyName(travelId, searchText,tag, page, limit));
  }

  @ApiOperation(value = "Search a place by name constrained by day (to determine bounding box)")
  @GetMapping(value = "/place/{day}", produces = "application/json")
  @Timed("api.search.place.day")
  @PreAuthorize("hasPermission(#travelId, '', 'EDITOR')")
  public PageResponse<Place> searchPlace(@PathVariable("travelId") final long travelId,
                                         @RequestParam("q") final String searchText,
                                         @RequestParam(value = "tag", required = false) final String tag,
                                         @PathVariable("day") final long dayId,
                                         @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                         @RequestParam(value = "limit", defaultValue = "10", required = false) final int limit) {
	    return new PageResponse<>(findPlacebyNamenDay.findPlacebyNamenDay(travelId, dayId, searchText, tag,page, limit));
	    
	    
  }

}
