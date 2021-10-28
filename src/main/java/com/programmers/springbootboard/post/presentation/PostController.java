package com.programmers.springbootboard.post.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.post.application.PostService;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.dto.request.PostDeleteRequest;
import com.programmers.springbootboard.post.dto.request.PostInsertRequest;
import com.programmers.springbootboard.post.dto.response.PostDeleteResponse;
import com.programmers.springbootboard.post.dto.response.PostDetailResponse;
import com.programmers.springbootboard.post.dto.response.PostInsertResponse;
import com.programmers.springbootboard.post.dto.request.PostUpdateRequest;
import com.programmers.springbootboard.post.dto.response.PostUpdateResponse;
import com.programmers.springbootboard.post.dto.bundle.PostDeleteBundle;
import com.programmers.springbootboard.post.dto.bundle.PostFindBundle;
import com.programmers.springbootboard.post.dto.bundle.PostInsertBundle;
import com.programmers.springbootboard.post.dto.bundle.PostUpdateBundle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.programmers.springbootboard.common.converter.ResponseConverter.toResponseEntity;
import static com.programmers.springbootboard.common.domain.LinkType.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Api(tags = "Post")
@RestController
@RequestMapping(value = "/api/v1/posts", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostConverter postConverter;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PostController.class);
    }

    @ApiOperation("게시물 생성")
    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@RequestBody PostInsertRequest request) {
        PostInsertBundle bundle = postConverter.toPostInsertBundle(request);
        PostInsertResponse responseDto = postService.insert(bundle);

        EntityModel<PostInsertResponse> entityModel = EntityModel.of(responseDto,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(responseDto.getPostId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(responseDto.getPostId()).withRel(UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(responseDto.getPostId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return toResponseEntity(
                ResponseMessage.POST_INSERT_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("게시물 단건 조회")
    @GetMapping(value = "/{postId}")
    public ResponseEntity<ResponseDto> post(@PathVariable Long postId) {
        PostFindBundle bundle = postConverter.toPostFindBundle(postId);
        PostDetailResponse response = postService.findById(bundle);

        EntityModel<PostDetailResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getPostId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(UPDATE_METHOD).withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return toResponseEntity(
                ResponseMessage.POST_INQUIRY_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("게시물 전체 조회")
    @GetMapping()
    public ResponseEntity<ResponseDto> posts(Pageable pageable) {
        Page<PostDetailResponse> response = postService.findAll(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return toResponseEntity(
                ResponseMessage.POSTS_INQUIRY_SUCCESS,
                response,
                link
        );
    }

    @ApiOperation("게시물 수정")
    @PutMapping(value = "/{postId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        PostUpdateBundle bundle = postConverter.toPostUpdateBundle(postId, request);
        PostUpdateResponse response = postService.update(bundle);

        EntityModel<PostUpdateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getPostId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getPostId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name())
        );

        return toResponseEntity(
                ResponseMessage.POST_UPDATE_SUCCESS,
                entityModel
        );
    }

    @ApiOperation("게시물 삭제")
    @DeleteMapping(value = "/{postId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> delete(@PathVariable Long postId, @RequestBody PostDeleteRequest request) {
        PostDeleteBundle bundle = postConverter.toPostDeleteBundle(postId, request);
        PostDeleteResponse response = postService.delete(bundle);

        EntityModel<PostDeleteResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withRel(INSERT_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
                getLinkToAddress().slash(response.getPostId()).withSelfRel().withType(HttpMethod.DELETE.name())
        );

        return toResponseEntity(
                ResponseMessage.POST_DELETE_SUCCESS,
                entityModel
        );
    }
}
