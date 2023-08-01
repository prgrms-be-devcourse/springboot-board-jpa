package com.juwoong.springbootboardjpa.user.api.model;

import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {

    private String name;
    private Integer age;
    private Hobby hobby;

}
