package com.example.springbootboard.dto;

import com.example.springbootboard.domain.Hobby;
import com.example.springbootboard.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestUser {


    @Pattern(regexp = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$", message = "이름을 확인해주세요")
    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotNull(message = "나이는 필수입니다")
    private Integer age;

    @NotBlank(message = "취미는 필수입니다")
    private String hobby;

    @Builder
    public RequestUser(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User toEntity() throws IllegalArgumentException {
        return User.builder()
                .createdAt(LocalDateTime.now())
                .createdBy(getName())
                .name(getName())
                .age(getAge())
                .hobby(Hobby.valueOf(getHobby()))
                .build();
    }
}
