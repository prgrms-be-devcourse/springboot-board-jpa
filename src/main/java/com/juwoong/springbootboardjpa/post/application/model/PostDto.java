package com.juwoong.springbootboardjpa.post.application.model;

import java.time.LocalDateTime;

public record PostDto(Long id, Long userId, String title, String content, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
}
