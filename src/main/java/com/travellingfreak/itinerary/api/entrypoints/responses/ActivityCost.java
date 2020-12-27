package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.math.BigDecimal;

import com.travellingfreak.itinerary.api.dataproviders.model.enums.CurrencyCode;

import lombok.Data;

@Data
public class ActivityCost {
	
	private long id;
	private String note;
	private BigDecimal amount;
	private CurrencyCode currencyCode;
	
	

}
