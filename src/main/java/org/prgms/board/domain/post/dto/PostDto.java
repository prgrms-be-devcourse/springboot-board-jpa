package org.prgms.board.domain.post.dto;

import java.time.LocalDateTime;

import org.prgms.board.domain.post.domain.Post;
import org.prgms.board.domain.user.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Write {
		private String title;
		private String content;
		private Long userId;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Update {
		private String title;
		private String content;
		private Long postId;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private String title;
		private String content;
		private UserDto.Response writer;
		private LocalDateTime createdAt;

		public static Response toPostResponse(Post post) {
			final UserDto.Response userResponse = UserDto.Response.toUserResponse(post.getWriter());
			return new Response(post.getId(), post.getTitle(), post.getContent(), userResponse, post.getCreatedAt());
		}

	}

}
