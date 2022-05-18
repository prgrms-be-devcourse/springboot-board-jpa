package com.example.springbootboardjpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private CustomerDto customer;
    private String created_by;
    private LocalDateTime created_at;
}
