package dev.jpaboard.post.api;

import dev.jpaboard.post.application.PostService;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
import dev.jpaboard.post.dto.PostsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(CREATED)
    public PostResponse createPost(@Valid @RequestBody PostCreateRequest request,
                                   @SessionAttribute(name = "userId") Long userId) {
        return postService.create(request, userId);
    }

    @GetMapping
    public PostsResponse findSlicePosts(@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        return postService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PostResponse findPost(@PathVariable Long id) {
        return postService.findPost(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updatePost(@PathVariable("id") Long postId,
                           @Valid @RequestBody PostUpdateRequest request,
                           @SessionAttribute(name = "userId") Long userId) {
        postService.update(postId, request, userId);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long postId,
                           @SessionAttribute(name = "userId") Long userId) {
        postService.delete(postId, userId);
    }

}
