package com.example.springbootboardjpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private Long id;
    @NotBlank
    private String name;
    @Positive
    private Integer age;
    @NotBlank
    private String hobby;
    private String created_by;
    private LocalDateTime created_at;
}
