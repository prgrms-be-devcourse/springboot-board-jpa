package org.programmers.springboardjpa.domain.post.api;

import org.programmers.springboardjpa.domain.post.dto.PostResponse;
import org.programmers.springboardjpa.domain.post.service.PostDefaultService;
import org.programmers.springboardjpa.domain.post.dto.PostRequest;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostUpdateRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RequestMapping("/api/v1")
@RestController
public class PostApi {
    private final PostDefaultService postDefaultService;

    public PostApi(PostDefaultService postDefaultService) {
        this.postDefaultService = postDefaultService;
    }

    @PostMapping("/posts")
    public PostResponse.PostResponseDto save(@Valid @RequestBody PostRequest.PostCreateRequestDto createRequest) {
        return postDefaultService.savePost(createRequest);
    }

    @GetMapping("/posts")
    public List<PostResponse.PostResponseDto> getAll(Pageable pageable) {
        return postDefaultService.getPostList(pageable);
    }

    @GetMapping("/posts/{id}")
    public PostResponse.PostResponseDto get(@PathVariable @Positive Long id) {
        return postDefaultService.getPost(id);
    }

    @PutMapping("/posts/{id}")
    public PostResponse.PostResponseDto update(@PathVariable @Positive Long id, @Valid @RequestBody PostUpdateRequestDto updateRequest) {
        return postDefaultService.updatePost(id, updateRequest);
    }
}
