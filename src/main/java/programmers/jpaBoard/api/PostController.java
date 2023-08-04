package programmers.jpaBoard.api;
;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programmers.jpaBoard.dto.PostDto;
import programmers.jpaBoard.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/new")
    public ResponseEntity<PostDto.Response> createPost(@RequestBody PostDto.Request request) {
        PostDto.Response response = postService.create(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto.Response> getPost(@PathVariable Long id) {
        PostDto.Response response = postService.getPost(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PostDto.Response>> getAllPost(Pageable pageable) {
        Page<PostDto.Response> responses = postService.getAllPost(pageable);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto.Response> updatePost(@PathVariable Long id, @RequestBody PostDto.Request request) {
        PostDto.Response response = postService.update(id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
