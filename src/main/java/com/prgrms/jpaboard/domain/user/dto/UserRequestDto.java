package com.prgrms.jpaboard.domain.user.dto;

import com.prgrms.jpaboard.domain.user.domain.User;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class UserRequestDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Min(1)
    private Integer age;

    @Size(max = 255)
    private String hobby;

    public UserRequestDto(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User toEntity() {
        LocalDateTime now = LocalDateTime.now();

        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .createdBy(name)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequestDto that = (UserRequestDto) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getAge(), that.getAge()) && Objects.equals(getHobby(), that.getHobby());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getHobby());
    }
}
