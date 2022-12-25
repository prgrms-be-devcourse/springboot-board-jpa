package kdt.springbootboardjpa.service.dto;

import lombok.Builder;

public record PostDto(Long id, String title, String content, Long createdBy) {

    @Builder
    public PostDto {
    }
}
