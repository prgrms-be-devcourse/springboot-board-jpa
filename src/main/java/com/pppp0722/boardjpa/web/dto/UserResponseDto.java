package com.pppp0722.boardjpa.web.dto;

import com.pppp0722.boardjpa.domain.user.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private LocalDateTime createdAt;
    private Long id;
    private String name;
    private Integer age;
    private String hobby;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
            .createdAt(user.getCreatedAt())
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .hobby(user.getHobby())
            .build();
    }

    public User to() {
        User user = new User();
        user.setCreatedAt(createdAt);
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        user.setHobby(hobby);

        return user;
    }
}
