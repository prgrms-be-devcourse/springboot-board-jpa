package com.prgrms.boardjpa.Post.dto.request;

import com.prgrms.boardjpa.Post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCreateRequest {

    private Long userId;

    @NotBlank(message = "제목을 입력해 주세요.")
    @Size(min = 2, message = "제목은 2글자 이상 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    @Size(min = 2, message = "내용은 2글자 이상 입력해주세요.")
    private String content;

    @Builder
    public PostCreateRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
