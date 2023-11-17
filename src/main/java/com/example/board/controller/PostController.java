package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.exception.BindingException;
import com.example.board.response.Response;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping
    public Response<Long> save(@RequestBody @Validated PostDto postDto, BindingResult bindingResult) {
        bindChecking(bindingResult);
        return Response.success(postService.save(postDto));
    }

    @GetMapping
    public Response<Page<PostResponseDto>> readAllPost(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ){
        return Response.success(postService.readAllPost(pageable));
    }

    private static void bindChecking(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {

                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                System.out.println("field :" + field.getField());
                System.out.println("message :" + message);

                sb.append("field :" + field.getField());
                sb.append("message :" + message);

            });

            throw new BindingException(sb.toString());
        }
    }

}
