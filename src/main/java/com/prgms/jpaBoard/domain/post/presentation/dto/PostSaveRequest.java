package com.prgms.jpaBoard.domain.post.presentation.dto;


public record PostSaveRequest(String title, String content, Long userId) {
}
