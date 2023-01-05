package com.prgrms.domain.post;

import com.prgrms.dto.PostDto;
import com.prgrms.dto.PostDto.Response;
import com.prgrms.dto.PostDto.ResponsePostDtos;
import com.prgrms.dto.PostDto.Update;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService service;

    public PostController(@Validated PostService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPostById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findPostById(id));
    }

    @PostMapping
    public ResponseEntity<Void> registerPost(@RequestBody PostDto.Request postDto) {

        Response responsePostDto = service.insertPost(postDto);
        URI redirectPath = URI.create("api/posts/" + responsePostDto.getPostId());

        return ResponseEntity.created(redirectPath)
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response> modifyPost(@PathVariable Long id,
        @RequestBody Update postDto) {
        Response response = service.updatePost(id, postDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponsePostDtos> getPostsByPage(Pageable pageable) {

        return ResponseEntity.ok(service.getPostsByPage(pageable));
    }

}
