package dev.jpaboard.post.api;

import dev.jpaboard.post.application.PostService;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
import dev.jpaboard.post.dto.PostsResponse;
import lombok.RequiredArgsConstructor;
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
    @ResponseStatus(CREATED)
    public PostResponse create(@RequestBody PostCreateRequest request,
                               @SessionAttribute(name = "userId", required = false) Long userId) {
        return postService.create(request, userId);
    }

    @GetMapping("/posts/{id}")
    public PostResponse findPost(@PathVariable Long id) {
        return postService.findPost(id);
    }

    @GetMapping("/posts")
    public PostsResponse findSlicePosts(@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        return postService.findAll(pageable);
    }

    @PatchMapping("/posts/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable("id") Long postId,
                       @RequestBody PostUpdateRequest request,
                       @SessionAttribute(name = "userId", required = false) Long userId) {
        postService.update(postId, request, userId);
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable("id") Long postId,
                       @SessionAttribute(name = "userId", required = false) Long userId) {
        postService.delete(postId, userId);
    }

  @DeleteMapping("/posts")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAll() {
    postService.deleteAll();
  }

}
