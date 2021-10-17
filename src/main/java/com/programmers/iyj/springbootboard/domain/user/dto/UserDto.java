package com.programmers.iyj.springbootboard.domain.user.dto;

import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class UserDto {
    private Long id;
    private String name;
    private Integer age;
    private Hobby hobby;
}
