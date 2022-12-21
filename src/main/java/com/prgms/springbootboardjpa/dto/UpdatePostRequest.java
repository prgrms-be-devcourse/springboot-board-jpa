package com.prgms.springbootboardjpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UpdatePostRequest {
    @NotBlank
    @Length(max = 30)
    private String title;

    @NotBlank
    private String content;
}