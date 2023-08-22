package org.prgrms.myboard.dto;

import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;

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
    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getAge(), user.getHobby(), user.getPosts(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
