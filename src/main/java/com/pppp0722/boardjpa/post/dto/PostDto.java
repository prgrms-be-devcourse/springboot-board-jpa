package com.pppp0722.boardjpa.post.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private LocalDateTime createdAt;
    private String createdBy;
    private Long id;
    private String title;
    private String content;
    private UserDto userDto;
}
