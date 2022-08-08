package com.kdt.boardMission.dto;

import com.kdt.boardMission.domain.Post;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private long id;
    private String title;
    private String content;
    private UserDto userDto;

    public static PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(UserDto.convertUserDto(post.getUser()))
                .build();
    }
}
