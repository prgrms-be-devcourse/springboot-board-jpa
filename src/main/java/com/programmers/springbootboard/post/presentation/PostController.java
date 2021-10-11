package com.programmers.springbootboard.post.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.post.application.PostService;
import com.programmers.springbootboard.post.dto.PostDetailResponse;
import com.programmers.springbootboard.post.dto.PostInsertRequest;
import com.programmers.springbootboard.post.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> insertPost(@RequestBody PostInsertRequest request) {
        Email email = new Email(request.getEmail());
        postService.insert(email, request);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_INSERT_SUCCESS));
    }

    @PatchMapping("/post/{id}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        Email email = new Email(request.getEmail());
        postService.update(email, id, request);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_UPDATE_SUCCESS));
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<ResponseDto> post(@PathVariable Long id) {
        PostDetailResponse post = postService.findById(id);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_INQUIRY_SUCCESS, post));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDto> posts() {
        List<PostDetailResponse> posts = postService.findAll();
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POSTS_INQUIRY_SUCCESS, posts));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long id, @RequestBody Email email) {
        postService.deleteByEmail(id, email);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_DELETE_SUCCESS));
    }
}
