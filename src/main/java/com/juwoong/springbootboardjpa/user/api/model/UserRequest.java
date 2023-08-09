package com.juwoong.springbootboardjpa.user.api.model;

import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;

public sealed interface UserRequest permits UserRequest.Create, UserRequest.Update {

    record Create(String name, int age, Hobby hobby) implements UserRequest {
    }

    record Update(String name, int age, Hobby hobby) implements UserRequest {
    }

}
