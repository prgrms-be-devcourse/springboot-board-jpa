package com.kdt.post.dto;

import com.kdt.user.dto.UserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PostControlRequestDto {
    private Long userId;

    private Long postId;

    @NotNull
    @Length(max = 100)
    private String title;

    @NotNull
    @Length(max = 1000)
    private String content;

    private UserDto userDto;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdatedAt;

    public PostDto getPostDto(){
        return PostDto.builder()
                .id(postId)
                .title(title)
                .content(content)
                .userDto(userDto)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .lastUpdatedAt(lastUpdatedAt)
                .build();
    }
}
