package com.programmers.springbootboard.post.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.post.application.PostService;
import com.programmers.springbootboard.post.dto.PostDeleteResponse;
import com.programmers.springbootboard.post.dto.PostDetailResponse;
import com.programmers.springbootboard.post.dto.PostInsertRequest;
import com.programmers.springbootboard.post.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/post", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(PostController.class);
    }

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insertPost(@RequestBody PostInsertRequest request) {
        Email email = new Email(request.getEmail());
        PostDetailResponse post = postService.insert(email, request);

        Links links = Links.of(
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(post.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(post.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(post.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_INSERT_SUCCESS, post, links));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable Long id, @RequestBody Email email) {
        PostDeleteResponse post = postService.deleteByEmail(id, email);

        Links links = Links.of(
                getLinkToAddress().slash(post.getId()).withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("insert").withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(post.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_DELETE_SUCCESS, post, links));
    }

    @PatchMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        Email email = new Email(request.getEmail());
        PostDetailResponse post = postService.update(email, id, request);

        Links links = Links.of(
                getLinkToAddress().slash(post.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(post.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(post.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_UPDATE_SUCCESS, post, links));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> post(@PathVariable Long id) {
        PostDetailResponse post = postService.findById(id);

        Links links = Links.of(
                getLinkToAddress().slash(post.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(post.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(post.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POST_INQUIRY_SUCCESS, post, links));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> posts() {
        List<PostDetailResponse> posts = postService.findAll();

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.POSTS_INQUIRY_SUCCESS, posts, link));
    }


}
