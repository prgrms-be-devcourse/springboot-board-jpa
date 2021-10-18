package kdt.cse0518.board.user.dto;

import kdt.cse0518.board.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Post> posts;

    private UserDto() {
    }

    public UserDto update(final String name, final int age, final String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.modifiedAt = LocalDateTime.now();
        return this;
    }
}
