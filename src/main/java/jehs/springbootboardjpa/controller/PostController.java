package jehs.springbootboardjpa.controller;

import jehs.springbootboardjpa.dto.*;
import jehs.springbootboardjpa.entity.Post;
import jehs.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<SuccessMessage<PostResponse>> getPost(@PathVariable(name = "id") Long postId) {
        return new ResponseEntity<>(
                new SuccessMessage<>(
                        "성공적으로 게시글이 조회되었습니다.",
                        postService.getPostResponseById(postId)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/v1/posts")
    public ResponseEntity<SuccessMessage<ListResponse<Post, PostResponse>>> getAllPosts(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return new ResponseEntity<>(
                new SuccessMessage<>(
                        "성공적으로 모든 게시글이 조회되었습니다.",
                        postService.getAllPostsWithUser(page, size)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/v1/posts/cursor")
    public ResponseEntity<SuccessMessage<PostsCursorResponse>> getAllPostsByCursor(@RequestParam(name = "size") int size, @RequestParam(name = "cursorId") Long cursorId) {
        return new ResponseEntity<>(
                new SuccessMessage<>(
                        "성공적으로 모든 게시글이 조회되었습니다.",
                        postService.getAllPostsWithUserByCursor(size, cursorId)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/api/v1/posts")
    public ResponseEntity<SuccessMessage<Void>> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(postCreateRequest);
        return new ResponseEntity<>(
                new SuccessMessage<>(
                        "성공적으로 게시글이 생성되었습니다."
                ),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/api/v1/posts/{id}")
    public ResponseEntity<SuccessMessage<Void>> updatePost(@PathVariable(name = "id") Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(postId, postUpdateRequest);
        return new ResponseEntity<>(
                new SuccessMessage<>(
                        "성공적으로 게시글이 수정되었습니다."
                ),
                HttpStatus.OK
        );
    }
}
