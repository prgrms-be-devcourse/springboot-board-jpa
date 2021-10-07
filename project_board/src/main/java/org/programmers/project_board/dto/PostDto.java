package org.programmers.project_board.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private UserDto userDto;

}
