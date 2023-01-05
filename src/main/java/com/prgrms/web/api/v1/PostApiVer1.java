package com.prgrms.web.api.v1;

import static com.prgrms.dto.PostDto.*;
import static com.prgrms.web.auth.CookieUtil.getCookie;

import com.prgrms.domain.post.PostService;
import com.prgrms.dto.PostDto.Response;
import com.prgrms.dto.PostDto.ResponsePostDtos;
import com.prgrms.dto.PostDto.Update;
import com.prgrms.global.error.ErrorCode;
import com.prgrms.global.exception.AuthenticationFailedException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/posts")
public class PostApiVer1 {

    private final PostService service;

    public PostApiVer1(@Validated PostService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPostById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findPostById(id));
    }

    @PostMapping
    public ResponseEntity<Void> registerPost(@RequestBody Request postDto, HttpServletRequest request) {

        long userId = Long.parseLong(getCookie(request).getValue());

        Response responsePostDto = service.insertPost(userId, postDto);
        URI redirectPath = URI.create("api/v1/posts/" + responsePostDto.getPostId());

        return ResponseEntity.created(redirectPath)
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> modifyPost(@PathVariable Long id,
        @RequestBody Update postDto,
        HttpServletRequest request) {

        checkOwnPost(request, id);
        Response response = service.updatePost(id, postDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponsePostDtos> getPostsByPage(Pageable pageable) {

        return ResponseEntity.ok(service.getPostsByPage(pageable));
    }

    private void checkOwnPost(HttpServletRequest request, Long postId) {

        long loginUserid = Long.parseLong(getCookie(request).getValue());
        Response post = service.findPostById(postId);

        if (loginUserid != post.userId()) {
            throw new AuthenticationFailedException("본인 게시글만 수정, 삭제할 수 있습니다",
                ErrorCode.AUTHENCTICATION_FAILED);
        }
    }

}
