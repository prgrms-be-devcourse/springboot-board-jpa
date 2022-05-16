package com.kdt.boardMission.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {

    private UserDto userDto;
    private PostDto postDto;

}
