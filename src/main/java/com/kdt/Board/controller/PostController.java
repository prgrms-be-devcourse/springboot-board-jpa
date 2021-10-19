package com.kdt.Board.controller;

import com.kdt.Board.dto.PostRequest;
import com.kdt.Board.dto.PostResponse;
import com.kdt.Board.response.ApiResponse;
import com.kdt.Board.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /* @Parameter
    page: 요청할 페이지 번호
    size: 한 페이지당 조회할 개수(default:20)
    sort: Sorting 기준 파라미터
    ex) size=10&page=1&sort=id,desc
     */
    @GetMapping
    public ApiResponse<Page<PostResponse>> getPosts(Pageable pageable) {
        return ApiResponse.ok(postService.getPosts(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) throws NotFoundException {
        return ApiResponse.ok(postService.getPost(id));
    }

    @PostMapping
    public ApiResponse<Long> writePost(HttpServletRequest request,  @RequestBody PostRequest postRequest) {
        final Cookie[] cookies = request.getCookies();
        final String userId = Arrays.stream(cookies).filter(x -> x.getName().equals("userid")).findFirst().get().getValue();
        return ApiResponse.ok(
                postService.writePost(Long.parseLong(userId), postRequest)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> editPost(HttpServletRequest request, @PathVariable Long id, @RequestBody PostRequest postRequest) throws AuthenticationException {
        final Cookie[] cookies = request.getCookies();
        final String userId = Arrays.stream(cookies).filter(x -> x.getName().equals("userid")).findFirst().get().getValue();
        return ApiResponse.ok(
                postService.editPost(Long.parseLong(userId), id, postRequest)
        );
    }
}
