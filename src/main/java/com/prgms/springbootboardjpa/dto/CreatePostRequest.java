package com.prgms.springbootboardjpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    @NotNull
    private Long memberId;

    @NotBlank
    @Length(max = 30)
    private String title;

    @NotBlank
    private String content;
}
