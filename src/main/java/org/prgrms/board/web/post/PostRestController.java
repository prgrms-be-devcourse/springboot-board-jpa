package org.prgrms.board.web.post;

import static org.prgrms.board.web.ApiResponse.*;

import java.util.List;

import org.prgrms.board.config.support.Pageable;
import org.prgrms.board.service.post.PostService;
import org.prgrms.board.web.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
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
}
