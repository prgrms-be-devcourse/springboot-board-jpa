package kdt.springbootboardjpa.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

public record SavePostRequest(@NotBlank String title, @NotBlank String content, @Positive Long createdBy) {
    @Builder
    public SavePostRequest {
    }
}
