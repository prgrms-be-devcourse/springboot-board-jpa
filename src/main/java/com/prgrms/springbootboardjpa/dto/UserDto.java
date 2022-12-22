package com.prgrms.springbootboardjpa.dto;

import com.prgrms.springbootboardjpa.entity.Post;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private String hobby;
}
