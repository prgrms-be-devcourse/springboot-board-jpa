package com.programmers.springbootboard.post.presentation;

import com.programmers.springbootboard.common.converter.ResponseConverter;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/post", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostConverter postConverter;
    private final ResponseConverter responseConverter;

    private static final String INSERT_METHOD = "insert";
    private static final String DELETE_METHOD = "delete";
    private static final String UPDATE_METHOD = "update";
    private static final String GET_METHOD = "get";
    private static final String GET_ALL_METHOD = "get-all";

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PostController.class);
    }

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@RequestBody PostInsertRequest request) {
        PostInsertBundle bundle = postConverter.toPostInsertBundle(request);
        PostInsertResponse responseDto = postService.insert(bundle);

        EntityModel<PostInsertResponse> entityModel = EntityModel.of(responseDto,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(responseDto.getPostId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(responseDto.getPostId()).withRel(UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(responseDto.getPostId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.CREATED,
                ResponseMessage.POST_INSERT_SUCCESS,
                entityModel
        );
    }

    @DeleteMapping(value = "/{postId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> delete(@PathVariable Long postId, @RequestBody PostDeleteRequest request) {
        PostDeleteBundle bundle = postConverter.toPostDeleteBundle(postId, request);
        PostDeleteResponse response = postService.delete(bundle);

        EntityModel<PostDeleteResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getPostId()).withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(INSERT_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.POST_DELETE_SUCCESS,
                entityModel
        );
    }

    @PutMapping(value = "/{postId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        PostUpdateBundle bundle = postConverter.toPostUpdateBundle(postId, request);
        PostUpdateResponse response = postService.update(bundle);

        EntityModel<PostUpdateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getPostId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.POST_UPDATE_SUCCESS,
                entityModel
        );
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<ResponseDto> post(@PathVariable Long postId) {
        PostFindBundle bundle = postConverter.toPostFindBundle(postId);
        PostDetailResponse response = postService.findById(bundle);

        EntityModel<PostDetailResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getPostId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getPostId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.POST_INQUIRY_SUCCESS,
                entityModel
        );
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> posts(Pageable pageable) {
        Page<PostDetailResponse> response = postService.findAll(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBERS_INQUIRY_SUCCESS,
                response,
                link
        );
    }
}
