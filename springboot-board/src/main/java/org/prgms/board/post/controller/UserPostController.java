package org.prgms.board.post.controller;

import org.prgms.board.common.response.ApiResponse;
import org.prgms.board.post.dto.PostResponse;
import org.prgms.board.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPostController {
    private final PostService postService;

    public UserPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/users/{id}/posts")
    public ApiResponse<Page<PostResponse>> getPostsByUser(Pageable pageable, @PathVariable Long id) {
        return ApiResponse.toResponse(postService.getPostsByUser(pageable, id));
    }
}
