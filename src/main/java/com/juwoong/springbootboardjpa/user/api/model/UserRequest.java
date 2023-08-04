package com.juwoong.springbootboardjpa.user.api.model;

import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;


public record UserRequest (String name, int age, Hobby hobby) {
}

