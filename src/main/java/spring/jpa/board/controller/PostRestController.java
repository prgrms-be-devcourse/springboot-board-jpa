package spring.jpa.board.controller;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.jpa.board.dto.ApiResponse;
import spring.jpa.board.dto.post.PostCreateRequest;
import spring.jpa.board.dto.post.PostFindRequest;
import spring.jpa.board.dto.post.PostModifyRequest;
import spring.jpa.board.service.PostService;

@RestController
public class PostRestController {

  private final PostService postService;

  public PostRestController(PostService postService) {
    this.postService = postService;
  }

  @ExceptionHandler(NotFoundException.class)
  public ApiResponse<String> notFoundHandler(NotFoundException e) {
    return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ApiResponse<String> internalServerErrorHandler(Exception e) {
    return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
  }


  @GetMapping("/posts")
  public ApiResponse<Page<PostFindRequest>> getAll(Pageable pageable) {
    return ApiResponse.ok(postService.findAll(pageable));
  }

  @GetMapping("/posts/{id}")
  public ApiResponse<PostFindRequest> getOne(@PathVariable Long id) throws NotFoundException {
    return ApiResponse.ok(postService.findById(id));
  }

  @PostMapping("/posts")
  public ApiResponse<Long> save(@RequestBody PostCreateRequest postCreateRequest)
      throws NotFoundException {
    return ApiResponse.ok(postService.save(postCreateRequest));
  }

  @PostMapping("/posts/{id}")
  public ApiResponse<Long> update(@PathVariable Long id,
      @RequestBody PostModifyRequest postModifyRequest)
      throws NotFoundException {
    return ApiResponse.ok(postService.update(id, postModifyRequest));
  }


}
