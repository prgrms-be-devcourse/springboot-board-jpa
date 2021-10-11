package yjh.jpa.springnoticeboard.domain.dto;


import lombok.*;
import yjh.jpa.springnoticeboard.domain.entity.Post;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private int age;
    private String hobby;

    private List<Post> posts;
}
