package com.devcourse.springjpaboard.application.post.controller.dto;

public record CreatePostRequest(String title, String content, Long userId) {

}
