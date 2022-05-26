package com.prgrms.boardjpa.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.prgrms.boardjpa.user.Hobby;
import com.prgrms.boardjpa.user.User;

public class UserDto {
	public record CreateRequest(@Email String email, @Nullable Hobby hobby,
								@NotBlank String name, @NotNull Integer age, @NotBlank String password) {
		public User createUser() {
			return User.builder()
				.name(this.name)
				.age(this.age)
				.password(this.password)
				.hobby(this.hobby)
				.email(this.email)
				.build();
		}
	}

	public record Info(Long id, String email, Hobby hobby,
					   String name, Integer age) {

		public static Info from(User user) {
			return new Info(
				user.getId(),
				user.getEmail(),
				user.getHobby(),
				user.getName(),
				user.getAge()
			);
		}
	}
}
