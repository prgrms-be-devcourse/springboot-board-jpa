package com.programmers.epicblues.board.controller;

import com.programmers.epicblues.board.dto.PostPagePayload;
import com.programmers.epicblues.board.dto.PostResponse;
import com.programmers.epicblues.board.exception.InvalidRequestArgumentException;
import com.programmers.epicblues.board.exception.ResourceNotFoundException;
import com.programmers.epicblues.board.service.PostService;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @RequestMapping("/posts")
  public ResponseEntity<List<PostResponse>> getPostWithPage(
      @Valid @ModelAttribute PostPagePayload payload, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new InvalidRequestArgumentException(bindingResult);
    }
    int page = payload.getPage();
    int size = payload.getSize();

    var postResponseList = postService.getPosts(PageRequest.of(page, size));

    return ResponseEntity.ok(postResponseList);
  }

  @RequestMapping("/posts/{postId}")
  public ResponseEntity<PostResponse> getPostById(@PathVariable long postId) {

    try {
      var postResponse = postService.getPostById(postId);
      return ResponseEntity.ok(postResponse);

    } catch (NoSuchElementException exception) {
      throw new ResourceNotFoundException("Invalid id", exception);
    }

  }

}
