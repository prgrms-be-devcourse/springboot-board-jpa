package com.prgrms.board.controller;

import com.prgrms.board.controller.request.CreatePostRequest;
import com.prgrms.board.controller.request.UpdatePostRequest;
import com.prgrms.board.controller.response.CreatePostResponse;
import com.prgrms.board.controller.response.FindPostResponse;
import com.prgrms.board.controller.response.FindPostsResponse;
import com.prgrms.board.controller.response.UpdatePostResponse;
import com.prgrms.board.service.PostService;
import com.prgrms.board.service.dto.PostDto;
import com.prgrms.board.service.dto.PostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 작성
     *
     * @param createPostRequest
     * @return CreatePostResponse
     */
    @PostMapping
    @Transactional
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest createPostRequest) {

        Long userId = createPostRequest.getUserId();
        String title = createPostRequest.getTitle();
        String content = createPostRequest.getContent();
        Long postId = postService.create(userId, new PostDto(title, content));
        return ResponseEntity.ok(new CreatePostResponse(postId));
    }

    /**
     * 게시글 수정
     *
     * @param postId
     * @param updatePostRequest
     * @return
     */
    @PostMapping("/{postId}")
    @Transactional
    public ResponseEntity<UpdatePostResponse> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest updatePostRequest) {

        Long updatedPostId = postService.update(postId, updatePostRequest.getContent());
        return ResponseEntity.ok(new UpdatePostResponse(updatedPostId));
    }


    /**
     * 게시글 전체 페이징 조회
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<FindPostsResponse> getPosts(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        PostListDto postListDto = postService.findAll(pageable);
        List<FindPostResponse> findPostResponseList = postListDto.getPostDtoList().stream()
                .map(it -> new FindPostResponse(it.getId(), it.getTitle(), it.getContent()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new FindPostsResponse(postListDto.getCount(), findPostResponseList));
    }

    /**
     * 게시글 단일 조회
     *
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponse> getPost(@PathVariable("postId") Long postId){
        PostDto postDto = postService.findById(postId);
        return ResponseEntity.ok(new FindPostResponse(postDto.getId(), postDto.getTitle(), postDto.getContent()));
    }
}
