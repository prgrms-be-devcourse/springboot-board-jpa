package com.devcourse.springjpaboard.post.controller.dto;

public record CreatePostRequest(String title, String content, UserDto userDto) {

}
