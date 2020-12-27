package com.travellingfreak.itinerary.api.entrypoints.planning;

import com.travellingfreak.itinerary.api.core.planning.AddReplyToPostUseCase;
import com.travellingfreak.itinerary.api.core.planning.CreateDayPostUseCase;
import com.travellingfreak.itinerary.api.core.planning.CreateTravelPostUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeletePostUseCase;
import com.travellingfreak.itinerary.api.core.planning.DeleteReplyUseCase;
import com.travellingfreak.itinerary.api.core.planning.FindPostUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdatePostUseCase;
import com.travellingfreak.itinerary.api.core.planning.UpdateReplyUseCase;
import com.travellingfreak.itinerary.api.entrypoints.authentication.CurrentUser;
import com.travellingfreak.itinerary.api.entrypoints.requests.CreatePostRequest;
import com.travellingfreak.itinerary.api.entrypoints.responses.Expandpost;
import com.travellingfreak.itinerary.api.entrypoints.responses.ResourceCreationResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/discussions/{travelId}")
@Api(tags = "Discussions API")
@RequiredArgsConstructor
public class DiscussionsEndpoint {
	
  private final CreateTravelPostUseCase createTravelPostUseCase;
  private final CreateDayPostUseCase createDayPostUseCase;
  private final UpdatePostUseCase updatePostUseCase;
  private final DeletePostUseCase deletePostUseCase;
  private final AddReplyToPostUseCase addReplyToPostUseCase;
  private final FindPostUseCase findPostUseCase;
  private final UpdateReplyUseCase updateReplyUseCase;
  private final DeleteReplyUseCase deleteReplyUseCase;
  
  @ApiOperation("Sets the description of the travel (which is managed as a post)")
  @PostMapping(value = "travel", produces = "application/json")
  @Timed("api.planning.createTravelPost")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public  ResourceCreationResponse createTravelPost(@PathVariable("travelId") final long travelId,
                                               @RequestBody final CreatePostRequest createPostRequest) {
	  return new ResourceCreationResponse(createTravelPostUseCase.createTravelPost(CurrentUser.getCurrentUser(),travelId,createPostRequest));
  }

  @ApiOperation("Adds a new post for a particular day")
  @PostMapping(value = "day/{day}", produces = "application/json")
  @Timed("api.planning.createDayPost")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public  ResourceCreationResponse createDayPost(@PathVariable("travelId") final long travelId,
                            @PathVariable("day") final long id,
                            @RequestBody final CreatePostRequest createPostRequest) {
	  return new  ResourceCreationResponse(createDayPostUseCase.addpostforaday(CurrentUser.getCurrentUser(),travelId, id, createPostRequest));
  }

  @ApiOperation(value = "Allows to edit a previously posted message",
                notes = "You need to be either owner of the travel plan or the creator of the message to be able to update it")
  @PutMapping(value = "{discussionId}", produces = "application/json")
  @Timed("api.planning.editPost")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void editPost(@PathVariable("travelId") final long travelId,
                       @PathVariable("discussionId") final long discussionId,
                       @RequestBody final CreatePostRequest createPostRequest) {
	  updatePostUseCase.editpost(CurrentUser.getCurrentUser(),travelId, discussionId, createPostRequest);
  }

  @ApiOperation(value = "Allows to delete a previously posted message",
                notes = "You need to be either owner of the travel plan or the creator of the message to be able to update it")
  @DeleteMapping(value = "{discussionId}", produces = "application/json")
  @Timed("api.planning.deletePost")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void deletePost(@PathVariable("travelId") final long travelId,
                         @PathVariable("discussionId") final long discussionId) {
	  deletePostUseCase.deletepost(travelId, discussionId);
  }


  @ApiOperation("Adds a new reply to an existing post")
  @PostMapping(value = "reply/{postId}", produces = "application/json")
  @Timed("api.planning.replyToPost")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public  ResourceCreationResponse replyToPost(@PathVariable("travelId") final long travelId,
                          @PathVariable("postId") final long postId,
                          @RequestBody final CreatePostRequest createPostRequest) {
	  return new  ResourceCreationResponse(addReplyToPostUseCase.addreplyforpost(CurrentUser.getCurrentUser(),travelId, postId, createPostRequest));
  }

  @ApiOperation("Expand a post. Apis which return posts only return CollapsedPosts")
  @GetMapping(value = "reply/{postId}", produces = "application/json")
  @Timed("api.planning.getPost")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  @ResponseBody
  public Expandpost getPost(@PathVariable("travelId") final long travelId,
                      @PathVariable("postId") final long postId) {
    return findPostUseCase.expandedPost(postId);
  }

  
  @ApiOperation(value = "Updates an existing reply to a post",
                notes = "You need to be either owner of the travel plan or the creator of the message to be able to update it")
  @PutMapping(value = "reply/{replyId}", produces = "application/json")
  @Timed("api.planning.replyToPost")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void editReply(@PathVariable("travelId") final long travelId,
                        @PathVariable("replyId") final long replyId,
                        @RequestBody final CreatePostRequest createPostRequest) {
	  updateReplyUseCase.updatereplyforpost(CurrentUser.getCurrentUser(),travelId, replyId, createPostRequest);
  }

  @ApiOperation(value = "Deletes an existing reply to a post",
                notes = "You need to be either owner of the travel plan or the creator of the message to be able to update it")
  @DeleteMapping(value = "reply/{replyId}", produces = "application/json")
  @Timed("api.planning.deleteReply")
  @PreAuthorize("hasPermission(#travelId, '', 'WRITE')")
  public void deleteReply(@PathVariable("travelId") final long travelId,
                          @PathVariable("replyId") final long replyId) {
	  deleteReplyUseCase.deletereplyforpost(travelId,replyId);
  }

}
