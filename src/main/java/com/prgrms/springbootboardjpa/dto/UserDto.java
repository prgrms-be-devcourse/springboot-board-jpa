package com.prgrms.springbootboardjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserDto {

    private String name;
    private int age;
    private String hobby;

}
