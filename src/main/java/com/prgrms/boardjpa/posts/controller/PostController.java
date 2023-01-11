package com.prgrms.boardjpa.posts.controller;

import com.prgrms.boardjpa.common.CommonResponse;
import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.posts.facade.PostFacade;
import com.prgrms.boardjpa.posts.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
  private final PostService postService;

  private final PostFacade postFacade;

  public PostController(PostService postService, PostFacade postFacade) {
    this.postService = postService;
    this.postFacade = postFacade;
  }

  /**
   * 게시글 목록 조회
   * @param pageable 페이지 정보
   * @return postDto를 담은 페이지
   */
  @GetMapping
  public CommonResponse<Page<PostDto>> getPosts(Pageable pageable) {
    var postDtos = postService.getPostDtos(pageable);
    return CommonResponse.ok(postDtos);
  }

  /**
   * id로 게시글을 조회한다.
   * @param postId 게시글 id
   * @return postDto 조회된 게시글
   */
  @GetMapping("/{postId}")
  public CommonResponse<PostDto> getPost(@PathVariable Long postId) {
    var postDto = postService.getPostDto(postId);
    return CommonResponse.ok(postDto);
  }

  /**
   * 게시글을 저장한다.
   * @param postRequest (제목, 내용, 작성자)
   * @return postDto 저장된 게시글
   */
  @PostMapping
  public CommonResponse<PostDto> createPost(
      @RequestBody @Valid PostRequest postRequest
  ) {
    var postDto = postFacade.createPost(postRequest);
    return CommonResponse.ok(postDto);
  }

  /**
   * 게시글을 수정한다.
   * @param id 게시글 정보
   * @param postRequest 수정될 게시글
   * @return postDto 수정된 게시글
   */
  @PostMapping("/{id}")
  public CommonResponse<PostDto> updatePost(
      @PathVariable Long id,
      @RequestBody @Valid PostRequest postRequest
  ) {
    var postDto = postFacade.updatePost(id, postRequest);
    return CommonResponse.ok(postDto);
  }
}
