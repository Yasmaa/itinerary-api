package com.travellingfreak.itinerary.api.entrypoints.responses;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;
import lombok.Data;

@Data
public class Collapsedpost {
	
	private Post post;
	private ReplyDetails lastreply;
	private Integer count;
	
	

}
