package com.travellingfreak.itinerary.api.entrypoints.responses;

import java.util.List;

import com.travellingfreak.itinerary.api.dataproviders.model.posts.Post;

import lombok.Data;

@Data
public class Expandpost {
	
	private Post post;
	private List<ReplyDetails> replies;
	
	

}
