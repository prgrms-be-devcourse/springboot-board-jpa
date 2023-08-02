package dev.jpaboard.post.api;

import dev.jpaboard.post.application.PostService;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
import dev.jpaboard.post.dto.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping()
    @ResponseStatus(CREATED)
    public PostResponse create(@RequestBody PostCreateRequest request,
                               @SessionAttribute(name = "userId", required = false) Long userId) {
        return postService.create(request, userId);
    }

    @GetMapping()
    public PostsResponse findSlicePosts(@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        return postService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PostResponse findPost(@PathVariable Long id) {
        return postService.findPost(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable("id") Long postId,
                       @RequestBody PostUpdateRequest request,
                       @SessionAttribute(name = "userId", required = false) Long userId) {
        postService.update(postId, request, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long postId,
                       @SessionAttribute(name = "userId", required = false) Long userId) {
        postService.delete(postId, userId);
    }

}
