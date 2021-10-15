package org.jpa.kdtboard.post.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.jpa.kdtboard.common.Response;
import org.jpa.kdtboard.domain.board.UserRepository;
import org.jpa.kdtboard.post.dto.PostDto;
import org.jpa.kdtboard.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * Created by yunyun on 2021/10/12.
 */

@RestController
@RequiredArgsConstructor
public class PostController {

    @ExceptionHandler
    private Response<String> exceptionHandle(Exception exception) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private Response<String> notFoundHandle(NotFoundException exception) {
        return Response.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }


    private final PostService postService;

    @GetMapping("/posts")
    public Response<Page<PostDto>> getAllWithPaging(Pageable pageable){
        return Response.ok(postService.findAllWithPaging(pageable));
    }

    @GetMapping("/posts/{id}")
    public Response<PostDto> getById(@PathVariable Long id) throws Throwable {
        return Response.ok(postService.findById(id));
    }

    @PostMapping("/posts")
    public Response<Long> save(@RequestBody PostDto postDto) throws NoSuchAlgorithmException {
        return Response.ok(postService.save(postDto));
    }

    @PostMapping("/posts/{id}")
    public Response<Long> update(@PathVariable Long id, @RequestBody PostDto postDto) throws Throwable {
        if (!postService.validateForPassword(id, postDto.getPassword())) throw new RuntimeException("유효한 비밀번호가 아닙니다. 확인바랍니다.");
        return Response.ok(postService.update(id, postDto));
    }

}
