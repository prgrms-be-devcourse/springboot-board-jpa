package com.example.board.dto.post;

import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {
    @NotNull
    private Long userId;

    @NotBlank(message = "제목을 공백일 수 없습니다")
    private String title;

    @NotBlank(message = "본문은 공백일 수 없습니다.")
    private String content;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
