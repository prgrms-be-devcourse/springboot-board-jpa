package org.prgms.board.domain.user.dto;

import java.time.LocalDateTime;

import org.prgms.board.domain.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Save {
		private String name;
		private int age;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Update {
		private String name;
		private int age;
		private Long userId;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private String name;
		private int age;
		private LocalDateTime createdAt;

		public static Response toUserResponse(User user) {
			return new Response(user.getId(), user.getName(), user.getAge(), user.getCreatedAt());
		}
	}

}
