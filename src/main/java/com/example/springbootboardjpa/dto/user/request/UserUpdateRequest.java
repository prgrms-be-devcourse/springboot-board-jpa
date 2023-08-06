package com.example.springbootboardjpa.dto.user.request;

import com.example.springbootboardjpa.enums.Hobby;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private int age;
    private Hobby hobby;
}
