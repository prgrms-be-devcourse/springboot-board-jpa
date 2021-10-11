package com.homework.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private Long id;

    @NotNull(message = "Name is mandatory")
    private String name;

    @Range(min = 1, max = 100, message = "Age is between 0 and 100")
    private int age;

    @Pattern(regexp = "^[a-zA-Z]*$", message = "Hobby should be written by English")
    private String hobby;

    @JsonProperty(value = "post")
    private List<PostDto> postDtos;

    @Builder
    public UserDto(Long id, String name, int age, String hobby, List<PostDto> postDtos) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postDtos = postDtos;
    }
}
