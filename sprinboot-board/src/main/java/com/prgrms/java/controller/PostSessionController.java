package com.prgrms.java.controller;

import com.prgrms.java.domain.Email;
import com.prgrms.java.global.model.MySession;
import com.prgrms.java.dto.post.*;
import com.prgrms.java.global.service.SessionHandler;
import com.prgrms.java.service.PostService;
import com.prgrms.java.service.UserService;
import com.prgrms.java.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/posts")
public class PostSessionController {

    private final PostService postService;
    private final UserService userService;
    private final SessionHandler sessionHandler;

    public PostSessionController(PostService postService, UserService userService, SessionHandler sessionHandler) {
        this.postService = postService;
        this.userService = userService;
        this.sessionHandler = sessionHandler;
    }

    @GetMapping
    public ResponseEntity<GetPostsResponse> getPosts(Pageable pageable) {
        GetPostsResponse posts = postService.getPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDetailsResponse> getPostDetails(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        GetPostDetailsResponse postDetail = postService.getPostDetail(id);
        return ResponseEntity.ok(postDetail);
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@Valid @RequestBody CreatePostRequest request, HttpServletRequest httpServletRequest) {
        final long userId = authenticate(httpServletRequest);

        long postId = postService.createPost(request, userId);
        return ResponseEntity.ok(new CreatePostResponse(postId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModifyPostResponse> modifyPost(@PathVariable Long id, @Valid @RequestBody ModifyPostRequest request, HttpServletRequest httpServletRequest) {
        authenticate(httpServletRequest);

        postService.modifyPost(id, request);
        return ResponseEntity.ok(new ModifyPostResponse(id));
    }

    private long authenticate(HttpServletRequest httpServletRequest) {
        MySession session = sessionHandler.find(httpServletRequest);
        userService.validMember(session.email());
        return session.userId();
    }
}
