package org.prgms.boardservice.domain.post;

import org.prgms.boardservice.domain.post.dto.PageDto;
import org.prgms.boardservice.domain.post.dto.PostCreateRequestDto;
import org.prgms.boardservice.domain.post.dto.PostResponseDto;
import org.prgms.boardservice.domain.post.dto.PostUpdateRequestDto;
import org.prgms.boardservice.domain.post.vo.PostUpdateVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<String> noSuchElementExceptionHandle(NoSuchElementException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        Long postId = postService.create(postCreateRequestDto.toEntity());

        return ResponseEntity.created(URI.create("/api/v1/posts/" + postId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getOne(@PathVariable Long id) throws NoSuchElementException {
        PostResponseDto postResponseDto = new PostResponseDto(postService.getById(id));

        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping
    public ResponseEntity<PageDto<PostResponseDto>> getPage(Pageable pageable) throws NoSuchElementException {
        Page<Post> page = postService.getByPage(pageable);
        Page<PostResponseDto> postResponseDtoPage = page.map(PostResponseDto::new);

        return ResponseEntity.ok(new PageDto<>(postResponseDtoPage));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody PostUpdateRequestDto postUpdateRequestDto, @PathVariable Long id) {
        Long postId = postService.update(new PostUpdateVo(id, postUpdateRequestDto.title(), postUpdateRequestDto.content()));

        return ResponseEntity.noContent()
                .location(URI.create("/api/v1/posts/" + postId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deleteById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
