package dev.jpaboard.post.dto;

import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        @Size(max = 25) String title,
        @Size(max = 5000) String content
) {
}
