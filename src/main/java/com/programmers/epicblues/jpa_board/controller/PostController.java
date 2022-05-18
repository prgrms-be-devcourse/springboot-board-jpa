package com.programmers.epicblues.jpa_board.controller;

import com.programmers.epicblues.jpa_board.dto.PostPagePayload;
import com.programmers.epicblues.jpa_board.dto.PostResponse;
import com.programmers.epicblues.jpa_board.exception.InvalidRequestArgumentException;
import com.programmers.epicblues.jpa_board.service.PostService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InvalidRequestArgumentException.class)
  public Map<String, String> handleValidationException(
      InvalidRequestArgumentException exception) {
    return exception.getBindingResult().getAllErrors().stream().collect(
        Collectors.toMap(
            error -> ((FieldError) error).getField(),
            error -> error.getDefaultMessage() == null ? "No Message" : error.getDefaultMessage()));
  }

}
