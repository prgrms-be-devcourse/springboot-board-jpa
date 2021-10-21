package com.example.board.domain.user.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "나이는 필수입니다.")
    @Min(value = 1, message = "나이는 1세 이상이어야합니다.")
    private Integer age;

    private String hobby;

    private LocalDateTime createdAt;
}
