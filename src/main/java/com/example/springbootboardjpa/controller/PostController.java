package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    /**
     * 게시물 page 조회
     *
     * @param pageable
     * @return Page<PostDto>
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ApiResponse<Page<PostDTO.Response>> getPost(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) { // method name, page default value
        Page<PostDTO.Response> pages = postService.findAll(pageable);
        return ApiResponse.ok(pages);
    }

    /**
     * 게시물 단건 조회
     *
     * @param id
     * @return PostDto
     */
    @GetMapping(value = "/{id}",produces = APPLICATION_JSON_VALUE)
    public ApiResponse<PostDTO.Response> getPosts(@PathVariable Long id) { // method name
        PostDTO.Response findPost = postService.findById(id);
        return ApiResponse.ok(findPost);
    }

    /**
     * 게시물 생성
     *
     * @param postDTO
     * @return PostId
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ApiResponse<PostDTO.ResponseId> createPost(@Valid @RequestBody PostDTO.Save postDTO) {
        long postId = postService.save(postDTO);
        PostDTO.ResponseId response = new PostDTO.ResponseId(postId);
        return ApiResponse.ok(response, "/api/v1/posts"); // 201, 202 CREATED // post /api/posts -> id 1 - /api/posts/1
    }

    /**
     * 게시물 수정
     *
     * @param id
     * @param postDTO
     */
    @PostMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE) // slash // update is method post? - put or patch
    public ApiResponse<String> updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO.Request postDTO) {
        postService.update(id, postDTO.getTitle(), postDTO.getContent());
        return ApiResponse.ok("success!", "/api/v1/posts/" + id);
    }
}
