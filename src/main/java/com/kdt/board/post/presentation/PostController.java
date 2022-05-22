package com.kdt.board.post.presentation;

import com.kdt.board.post.application.PostService;
import com.kdt.board.post.presentation.dto.request.EditPostRequest;
import com.kdt.board.post.presentation.dto.request.WritePostRequest;
import com.kdt.board.post.presentation.dto.response.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void write(@RequestBody @Valid final WritePostRequest writePostRequest) {
        postService.write(writePostRequest.toRequestDto());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC, size = 10) final Pageable pageable
            ) {
        return Collections.unmodifiableList(PostResponse.listOf(postService.getAll(pageable)));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getOne(@PathVariable final long id) {
        return PostResponse.from(postService.getOne(id));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void edit(@PathVariable final long id, @RequestBody @Valid final EditPostRequest editPostRequest) {
        postService.edit(editPostRequest.toRequestDto(id));
    }
}
