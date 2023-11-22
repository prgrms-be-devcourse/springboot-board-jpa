package com.devcourse.springbootboardjpahi.dto;

import lombok.Builder;

@Builder
public record UserDto(String name, Integer age, String hobby) {
}
