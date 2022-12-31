package com.prgrms.springbootboardjpa.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    private String name;
    private Integer age;
    private String hobby;
}
