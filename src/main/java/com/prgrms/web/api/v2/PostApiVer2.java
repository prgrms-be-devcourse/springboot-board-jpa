package com.prgrms.web.api.v2;


import static com.prgrms.dto.PostDto.Request;

import com.prgrms.domain.post.PostService;
import com.prgrms.dto.PostDto.Response;
import com.prgrms.dto.PostDto.ResponsePostDtos;
import com.prgrms.dto.PostDto.Update;
import com.prgrms.global.error.ErrorCode;
import com.prgrms.global.exception.AuthenticationFailedException;
import com.prgrms.web.auth.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/posts")
public class PostApiVer2 {

    private final PostService service;
    private final SessionManager sessionManager;

    public PostApiVer2(PostService service, SessionManager sessionManager) {
        this.service = service;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPostById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findPostById(id));
    }

    @PostMapping
    public ResponseEntity<Void> registerPost(@RequestBody Request postDto,
        HttpServletRequest request) {

        long userId = sessionManager.getSession(request);

        Response savedPost = service.insertPost(userId, postDto);
        URI getPostByIdPath = URI.create("api/v2/posts/" + savedPost.getPostId());

        return ResponseEntity.created(getPostByIdPath)
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> editPost(@PathVariable Long id,
        @RequestBody Update postDto,
        HttpServletRequest request) {

        checkOwnPost(request, id);
        Response updatedPost = service.updatePost(id, postDto);

        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping
    public ResponseEntity<ResponsePostDtos> getPostsByPage(Pageable pageable) {

        return ResponseEntity.ok(service.getPostsByPage(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest request) {

        checkOwnPost(request, id);
        service.deletePost(id);

        return ResponseEntity.noContent()
            .build();
    }

    private void checkOwnPost(HttpServletRequest request, Long postId) {

        long userId = sessionManager.getSession(request);
        Response post = service.findPostById(postId);

        if (userId != post.userId()) {
            throw new AuthenticationFailedException("본인 게시글만 수정, 삭제할 수 있습니다",
                ErrorCode.AUTHENCTICATION_FAILED);
        }
    }

}
