package org.programmers.springboardjpa.domain.post.api;

import org.programmers.springboardjpa.domain.post.dto.PostRequest;
import org.programmers.springboardjpa.domain.post.dto.PostResponse.PostResponseDto;
import org.programmers.springboardjpa.domain.post.service.PostDefaultService;
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
    public PostResponseDto save(@Valid @RequestBody PostRequest.PostCreateRequest createRequest) {
        return postDefaultService.savePost(createRequest);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getAll(Pageable pageable) {
        return postDefaultService.getPostList(pageable);
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto get(@PathVariable @Positive Long id) {
        return postDefaultService.getPost(id);
    }

    @PutMapping("/posts/{id}")
    public PostResponseDto update(
            @PathVariable @Positive Long id,
            @Valid @RequestBody PostRequest.PostUpdateRequest updateRequest
    ) {
        return postDefaultService.updatePost(id, updateRequest);
    }
}
