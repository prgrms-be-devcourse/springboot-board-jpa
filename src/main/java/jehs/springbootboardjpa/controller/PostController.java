package jehs.springbootboardjpa.controller;

import jehs.springbootboardjpa.dto.PostCreateRequest;
import jehs.springbootboardjpa.dto.PostResponse;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessMessage> getPost(@PathVariable(name = "id") Long postId) {
        PostResponse postResponse = postService.getPostResponseById(postId);
        return new ResponseEntity<>(new SuccessMessage("성공적으로 게시글이 조회되었습니다.", postResponse), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessMessage> getAllPosts(@PageableDefault Pageable pageable) {
        Page<PostResponse> postResponses = postService.getAllPostsWithUser(pageable);
        return new ResponseEntity<>(new SuccessMessage("성공적으로 모든 게시글이 조회되었습니다.", postResponses), HttpStatus.OK);
    }

    @GetMapping("/cursor")
    public ResponseEntity<SuccessMessage> getAllPostsByCursor(@PageableDefault Pageable pageable, @RequestParam(name = "cursorId") Long cursorId) {
        Page<PostResponse> postResponses = postService.getAllPostsWithUserByCursor(pageable, cursorId);
        return new ResponseEntity<>(new SuccessMessage("성공적으로 모든 게시글이 조회되었습니다.", postResponses), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SuccessMessage> createPost(@RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(postCreateRequest);
        return new ResponseEntity<>(new SuccessMessage("성공적으로 게시글이 생성되었습니다."), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessMessage> updatePost(@PathVariable(name = "id") Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(postId, postUpdateRequest);
        return new ResponseEntity<>(new SuccessMessage("성공적으로 게시글이 수정되었습니다."), HttpStatus.OK);
    }
}
