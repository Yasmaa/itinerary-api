package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.time.LocalTime;

import lombok.Data;

@Data
public class TimeUpdate {
	
	private LocalTime startTime;
	private LocalTime endTime;
	
	
	

}
