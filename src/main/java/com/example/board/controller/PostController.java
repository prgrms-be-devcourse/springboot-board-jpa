package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.exception.BaseException;
import com.example.board.exception.BindingException;
import com.example.board.exception.ErrorMessage;
import com.example.board.response.Response;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
