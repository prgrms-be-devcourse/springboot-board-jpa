package com.kdt.devboard.post.domain;

import com.kdt.devboard.user.domain.User;
import com.kdt.devboard.user.domain.UserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private String createBy;

    private UserDto userDto;

    @Builder
    public PostDto(Long id, String title, String content, LocalDateTime createAt, String createBy, UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.createBy = createBy;
        this.userDto = userDto;
    }

    public PostDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createAt = entity.getCreateAt();
        this.createBy = entity.getCreateBy();
        this.userDto = new UserDto(entity.getUser());
    }

    public Post toEntity() {
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createBy(this.createBy)
                .createAt(this.createAt)
                .user(this.userDto.toEntity())
                .build();
    }
}