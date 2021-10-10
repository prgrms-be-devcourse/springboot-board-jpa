package com.kdt.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdt.user.dto.UserDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PostDto {

    private Long id;

    @NotBlank
    @Length(max = 50)
    private String title;

    @NotBlank
    private String content;

    @JsonProperty(value = "user")
    @NotNull
    private UserDto userDto;

}
