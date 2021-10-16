package com.programmers.iyj.springbootboard.domain.user.dto;

import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {
    private Long id;
    private String name;
    private Integer age;
    private Hobby hobby;
}
