package com.prgrms.board.domain.post.dto.request;

import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
	@NotNull(message = "유저 아이디는 필수입니다.")
	Long userId,

	@NotBlank(message = "제목은 필수입니다.")
	@Size(max = 30, message = "제목은 최대 30자까지 가능합니다.")
	String title,

	@NotBlank(message = "내용은 필수입니다.")
	String content
) {
	public Post toEntity(User user) {
		return Post.create(user, title, content);
	}
}
