package dev.jpaboard.post.api;

import dev.jpaboard.post.application.PostService;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping("/posts")
  @ResponseStatus(HttpStatus.CREATED)
  public PostResponse create(PostCreateRequest request) {
    return postService.create(request);
  }

  @PatchMapping("/posts/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponse update(@PathVariable Long id, PostUpdateRequest request) {
    return postService.update(request, id);
  }

  @GetMapping("/posts/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponse findPost(@PathVariable Long id) {
    return postService.findPost(id);
  }

  @GetMapping("/posts/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public Page<PostResponse> findPostByUser(@PathVariable Long userId, @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
    return postService.findPostByUser(userId, pageable);
  }

  @GetMapping("/posts")
  @ResponseStatus(HttpStatus.OK)
  public Page<PostResponse> findSlicePost(@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
    return postService.findAll(pageable);
  }

  @DeleteMapping("/posts/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable Long id) {
    postService.delete(id);
  }

  @DeleteMapping("/posts")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAll() {
    postService.deleteAll();
  }

}
