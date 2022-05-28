package org.prgrms.board.web.post;

import static org.prgrms.board.web.ApiResponse.*;

import java.util.List;

import org.prgrms.board.config.support.Pageable;
import org.prgrms.board.domain.post.Post;
import org.prgrms.board.service.post.PostService;
import org.prgrms.board.web.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/posts")
@RestController
public class PostRestController {

	private final PostService postService;

	public PostRestController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public ApiResponse<List<PostDto>> findAll(Pageable pageable) {
		long offset = pageable.offset();
		int limit = pageable.limit();

		List<PostDto> posts = postService.findAll(offset, limit).stream()
			.map(PostDto::new).toList();

		return ok(posts);
	}

	@GetMapping("/count")
	public ApiResponse<Long> count() {
		long count = postService.count();

		return ok(count);
	}

	@GetMapping("/{id}")
	public ApiResponse<PostDto> findById(@PathVariable Long id) {
		Post post = postService.findById(id);

		return ok(new PostDto(post));
	}

	@PostMapping
	public ApiResponse<PostDto> posting(@RequestBody PostingRequest request) {
		Post post = postService.write(
			request.getTitle(),
			request.getContent(),
			request.getWriterId()
		);

		return ok(new PostDto(post));
	}

	@PostMapping("/{id}")
	public ApiResponse<PostDto> modify(@PathVariable Long id, @RequestBody PostingRequest request) {
		Post post = postService.modify(id, request.getTitle(), request.getContent());

		return ok(new PostDto(post));
	}
}