package com.kdt.jpaboard.domain.board.post.dto;

import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String content;

    private UserDto userDto;
}

