package com.example.springbootboard.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String hobby;
    private List<PostResponseDto> postDtos;
}
