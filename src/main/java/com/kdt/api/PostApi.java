package com.kdt.api;

import static com.kdt.api.PostApi.POSTS;
import static com.kdt.api.PostApi.PREFIX;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.kdt.post.dto.PostSaveDto;
import com.kdt.post.dto.PostViewDto;
import com.kdt.post.service.PostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = PREFIX + POSTS)
public class PostApi {

    protected static final String PREFIX = "/api/v1";
    protected static final String POSTS = "/posts";

    private final PostService postService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ApiResponse<Long> addPost(@RequestBody @Valid PostSaveDto postSaveDto) {
        return ApiResponse.ok(postService.save(postSaveDto));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ApiResponse<Page<PostViewDto>> getPosts(Pageable pageable) {
        return ApiResponse.ok(postService.findAll(pageable));
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ApiResponse<PostViewDto> getPost(@PathVariable Long id) {
        return ApiResponse.ok(postService.findOne(id));
    }

    @PostMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public ApiResponse<Long> updatePost(@RequestBody @Valid PostSaveDto postSaveDto) {
        return ApiResponse.ok(postService.update(postSaveDto));
    }

}
