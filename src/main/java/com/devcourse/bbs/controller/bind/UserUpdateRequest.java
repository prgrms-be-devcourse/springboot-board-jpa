package com.devcourse.bbs.controller.bind;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String name;
    private int age;
    private String hobby;
}
