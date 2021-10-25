package com.kdt.board.controller;

import com.kdt.board.ApiResponse;
import com.kdt.board.dto.comment.CommentRequest;
import com.kdt.board.dto.comment.CommentResponse;
import com.kdt.board.dto.post.PostDetailResponse;
import com.kdt.board.dto.post.PostRequest;
import com.kdt.board.dto.post.PostResponse;
import com.kdt.board.service.CommentService;
import com.kdt.board.service.PostService;
import com.kdt.board.service.UserService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {
    // note : IoC, Inversion of Control
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    public PostController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler (Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    /* Post Start */
    // question : controller에서 dto로 바꾸는 게 나은지 service에서 dto로 바꾸는 게 나은지?
    // solved : 기본은 controller으로 둬도 되고, 만약 service에서 null exception과 같은 로직이 추가되면 service에서 바꾸는 걸 추천
    @GetMapping
    public ApiResponse<List<PostResponse>> getAllPosts() throws NotFoundException {
        List<PostResponse> postResponses = postService.findAllPosts().stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
        return ApiResponse.ok(postResponses);
    }

    // question : Optional은 어디서 까야되나?
    // solved : get()은 거의 안 쓰고 service에서 optional 안이 null이면 throw하는 로직이 있으면 좋을 것 같다
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> getOnePost(
            @PathVariable Long postId
    ) throws NotFoundException {
        PostDetailResponse response = new PostDetailResponse(postService.findOnePost(postId).get());
        return ApiResponse.ok(response);
    }

    // TODO: 2021-10-25 requestbody를 여러개 줘도 되나? (userRequest)
    @PostMapping
    public ApiResponse<Long> createPost(
            @RequestBody PostRequest postRequest
    ) throws NotFoundException {
        return ApiResponse.ok(postService.createPost(postRequest));
    }

    // note : update는 보통 객체를 return
    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest
    ) throws NotFoundException{
        return ApiResponse.ok(postService.updatePost(postId, postRequest));
    }

    // question : 부리님꺼 코드 참고했는데 api response 알아봐야겠다
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return ApiResponse.ok();
    }
    /* Post End */

    /* Comment Start */
    // question : 이렇게 commentId만 쓸 때 postId를 pathvariable로 받아야 하나?
    // solved : 받아오는 인자는 꼭 쓰이는 것이 좋음 / browser에서의 url과 api 주소를 다르게 설정할 수 있다는 거 참고.
    // TODO: 2021-10-25 update return값과 postId를 사용하도록 변경, user 바뀐건지 확인
    @PutMapping("/{postId}/comments/{commentId}")
    public ApiResponse<CommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequest commentRequest
    ) {
        return ApiResponse.ok(commentService.updateComment(commentId, commentRequest));
    }

    @GetMapping("/{postId}/comments")
    public ApiResponse<List<CommentResponse>> getAllComments(
            @PathVariable Long postId
    ) {
        PostDetailResponse post = new PostDetailResponse(postService.findOnePost(postId).get());
        return ApiResponse.ok(post.getComments());
    }

    @PostMapping("/{postId}/comments")
    public ApiResponse<Long> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest commentRequest
    ) {
        return ApiResponse.ok(commentService.createComment(postId, commentRequest));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
        return ApiResponse.ok();
    }
    /* Comment End */

}
