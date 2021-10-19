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
public class PostSaveDto {

    private Long id;

    @NotBlank(message = "title is not blank")
    @Length(max = 50, message = "length must be between 0 and 50")
    private String title;

    @NotBlank(message = "content is not blank")
    private String content;

    @JsonProperty(value = "user")
    @NotNull(message = "userDto is not null")
    private UserDto userDto;

}
