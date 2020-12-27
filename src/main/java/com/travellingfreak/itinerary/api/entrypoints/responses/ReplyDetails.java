package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReplyDetails {
	
	
	private String createdBy;
    private LocalDate createdDate;
    private String lastModifiedBy;
    private LocalDate lastModifiedDate;
    private long id;
    private String message;

}
