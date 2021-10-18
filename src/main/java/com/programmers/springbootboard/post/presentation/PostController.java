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

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PostController.class);
    }

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insert(@RequestBody PostInsertRequest request) {
        PostInsertBundle postInsertServiceDto = postConverter.toPostInsertServiceDto(request);
        PostInsertResponse responseDto = postService.insert(postInsertServiceDto);

        // TODO
        EntityModel<PostInsertResponse> entityModel = EntityModel.of(responseDto,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(responseDto.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(responseDto.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(responseDto.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.CREATED,
                ResponseMessage.POST_INSERT_SUCCESS,
                entityModel
        );
    }

    // TODO <-에러 발생
    @DeleteMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> delete(@PathVariable Long id, @RequestBody PostDeleteRequest request) {
        PostDeleteBundle serviceDto = postConverter.toPostDeleteServiceDto(id, request);
        PostDeleteResponse post = postService.deleteByEmail(serviceDto);

        EntityModel<PostDeleteResponse> entityModel = EntityModel.of(post,
                getLinkToAddress().slash(post.getId()).withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("insert").withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(post.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.POST_DELETE_SUCCESS,
                entityModel
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        PostUpdateBundle serviceUpdateDto = postConverter.toPostUpdateServiceDto(id, request);
        PostUpdateResponse post = postService.update(serviceUpdateDto);

        EntityModel<PostUpdateResponse> entityModel = EntityModel.of(post,
                getLinkToAddress().slash(post.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(post.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(post.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.POST_UPDATE_SUCCESS,
                entityModel
        );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> post(@PathVariable Long id) {
        PostFindBundle serviceDto = postConverter.toPostFindServiceDto(id);
        PostDetailResponse post = postService.findById(serviceDto);

        EntityModel<PostDetailResponse> entityModel = EntityModel.of(post,
                getLinkToAddress().slash(post.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(post.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(post.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.POST_INQUIRY_SUCCESS,
                entityModel
        );
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> posts(Pageable pageable) {
        Page<PostDetailResponse> posts = postService.findAll(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBERS_INQUIRY_SUCCESS,
                posts,
                link
        );
    }
}
