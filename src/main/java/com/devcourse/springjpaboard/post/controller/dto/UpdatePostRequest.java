package com.devcourse.springjpaboard.post.controller.dto;

public record UpdatePostRequest(Long postId, String title, String content) {
}
