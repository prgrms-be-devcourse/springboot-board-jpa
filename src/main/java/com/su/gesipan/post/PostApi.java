package com.su.gesipan.post;

import com.su.gesipan.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostApi {

    private final UserService userService;
    private final PostService postService;

    /**** Query ****/

    // 게시글 조회 - 페이징
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<PostDto.Result> findAll(Pageable pageable) {
        return postService.findAll(pageable);
    }


    // 게시글 조회 - 단건
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PostDto.Result findById(@PathVariable Long id) {
        return postService.findById(id);
    }


    /**** Command ****/

    // 게시글 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostDto.Result createPost(@Validated @RequestBody PostDto.Create dto) {
        return userService.createPost(dto);
    }


    // 게시글 수정
    @PostMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    void editPost(@PathVariable Long id, @Validated @RequestBody PostDto.Update dto) {
        postService.editPost(id, dto);
    }
}
