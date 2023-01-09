package kdt.springbootboardjpa.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

public record PostDto(Long id, @NotBlank(message = "NotBlank") String title, @NotBlank(message = "NotBlank") String content, @Positive(message = "Positive") Long createdBy) {

    @Builder
    public PostDto {
    }
}
