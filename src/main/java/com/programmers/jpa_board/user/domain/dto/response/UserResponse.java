package com.programmers.jpa_board.user.domain.dto.response;

import com.programmers.jpa_board.post.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(Long id, String name, int age, String hobby, List<Post> posts, LocalDateTime createAt) {
}
