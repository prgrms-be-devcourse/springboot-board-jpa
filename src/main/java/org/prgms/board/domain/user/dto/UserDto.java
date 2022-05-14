package org.prgms.board.domain.user.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.prgms.board.domain.post.domain.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

	@Getter
	@NoArgsConstructor
	public static class Register {
		private String name;
		private int age;
	}

	@Getter
	@NoArgsConstructor
	public static class Update {
		private String name;
		private int age;
		private Long userId;
	}

	@Getter
	@NoArgsConstructor
	public static class Response {
		private Long id;
		private String name;
		private int age;
		private List<Post> posts = new ArrayList<>();
		private LocalDateTime createdAt;
	}

}
