package com.example.spring_jpa_post.user.dto;

import com.example.spring_jpa_post.user.entity.Hobby;
import lombok.*;

import javax.validation.constraints.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Max(value = 200,message = "200살 이하만 가능하다.")
    @Min(value = 1,message = "1살 이상만 가능하다.")
    private int age;

    private Hobby hobby;
}
