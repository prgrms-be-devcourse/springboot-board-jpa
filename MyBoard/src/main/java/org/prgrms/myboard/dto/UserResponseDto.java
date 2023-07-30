package org.prgrms.myboard.dto;

import org.prgrms.myboard.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
    Long id,
    String name,
    int age,
    String hobby,
    List<Post> posts,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
