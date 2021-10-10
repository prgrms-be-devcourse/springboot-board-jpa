package org.prgms.board.post.controller;

import org.prgms.board.common.ResponseHandler;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllPost(Pageable pageable, @PathVariable Optional<Long> userId) {
        if (userId.isPresent()) {
            return ResponseHandler.generateResponse("Successfully retrieved list!", HttpStatus.OK, postService.getAllPostByUser(pageable, userId.get()));
        } else {
            return ResponseHandler.generateResponse("Successfully retrieved list!", HttpStatus.OK, postService.getAllPost(pageable));
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Object> getOnePost(@PathVariable Long postId) {
        return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, postService.getOnePost(postId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> addPost(@PathVariable Long userId, @RequestBody PostRequest postRequest) {
        return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, postService.addPost(userId, postRequest));
    }

    @PutMapping("/{userId}/{postId}")
    public ResponseEntity<Object> modifyPost(@PathVariable Long userId, @PathVariable Long postId, @RequestBody PostRequest postRequest) {
        return ResponseHandler.generateResponse("Successfully updated data!", HttpStatus.OK, postService.modifyPost(userId, postId, postRequest));
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Object> removePost(@PathVariable Long userId, @PathVariable Long postId) {
        postService.removePost(userId, postId);
        return ResponseHandler.generateResponse("Successfully removed data!", HttpStatus.OK, null);
    }

}
