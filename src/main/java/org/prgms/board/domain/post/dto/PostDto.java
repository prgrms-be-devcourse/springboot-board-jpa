package org.prgms.board.domain.post.dto;

import java.time.LocalDateTime;

import org.prgms.board.domain.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostDto {

	@Getter
	@NoArgsConstructor
	public static class Write {
		private String title;
		private String content;
		private Long userId;
	}

	@Getter
	@NoArgsConstructor
	public static class Update {
		private String title;
		private String content;
	}

	@Getter
	@NoArgsConstructor
	public static class Response {
		private Long id;
		private String title;
		private String content;
		private User writer;
		private LocalDateTime createdAt;
	}

}
