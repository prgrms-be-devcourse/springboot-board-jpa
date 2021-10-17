package com.assignment.board.dto;

import com.assignment.board.domain.Post;
import com.assignment.board.domain.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private UserDto userDto;



}
