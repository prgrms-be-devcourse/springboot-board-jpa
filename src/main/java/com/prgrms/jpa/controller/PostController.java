package com.prgrms.jpa.controller;

import com.prgrms.jpa.controller.dto.post.request.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.request.FindAllPostRequest;
import com.prgrms.jpa.controller.dto.post.response.CreatePostResponse;
import com.prgrms.jpa.controller.dto.post.response.FindAllPostResponse;
import com.prgrms.jpa.controller.dto.post.response.GetByIdPostResponse;
import com.prgrms.jpa.controller.dto.post.request.UpdatePostRequest;
import com.prgrms.jpa.service.PostFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> create(@RequestBody @Valid CreatePostRequest createPostRequest) {
        CreatePostResponse id = postFacade.create(createPostRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<FindAllPostResponse> findAll(@RequestBody @Valid FindAllPostRequest findAllPostRequest) {
        FindAllPostResponse findAllPostResponse = postFacade.findAll(findAllPostRequest);
        return ResponseEntity.ok(findAllPostResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdPostResponse> getById(@PathVariable long id) {
        GetByIdPostResponse getByIdPostResponse = postFacade.getById(id);
        return ResponseEntity.ok(getByIdPostResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        postFacade.update(id, updatePostRequest);
        return ResponseEntity.ok().build();
    }
}
