package com.devcourse.springjpaboard.post.service.dto;

import com.devcourse.springjpaboard.post.controller.dto.UserDto;

public record PostResponse(String title, String content, UserDto userDto) {
}
