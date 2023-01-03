package com.example.springbootboard.dto;

import com.example.springbootboard.entity.Hobby;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private Hobby hobby;

    @Builder
    public UserDto(Long id, String name, int age, Hobby hobby){
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
