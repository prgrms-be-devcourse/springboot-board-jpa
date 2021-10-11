package yjh.jpa.springnoticeboard.domain.dto;

import lombok.*;
import yjh.jpa.springnoticeboard.domain.entity.User;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;

    private UserDto user;
}
