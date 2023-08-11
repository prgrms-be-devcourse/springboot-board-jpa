package com.programmers.jpa_board.user.domain.dto;

import com.programmers.jpa_board.post.domain.Post;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public sealed interface UserDto permits CreateUserRequest, UserResponse {
    record CreateUserRequest(
            @NotBlank
            @Pattern(regexp = "^[가-힣a-zA-Z]{1,30}$", message = "이름은 최소 1자 이상 최대 30자 이하의 한글 또는 영문자만 가능합니다.")
            String name,

            @Max(value = 100, message = "최대 나이는 100세입니다.")
            int age,

            @NotBlank
            @Size(max = 100, message = "최대 사이즈는 100자입니다.")
            String hobby
    ) {
    }

    record UserResponse(Long id, String name, int age, String hobby, List<Post> posts, LocalDateTime createAt) {
    }
}
