package com.example.springbootboard.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    String name;
    Integer age;
    String hobby;
}
