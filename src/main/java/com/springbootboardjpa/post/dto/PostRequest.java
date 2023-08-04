package com.springbootboardjpa.post.dto;

public record PostRequest(Long memberId, String content, String title) {
}
