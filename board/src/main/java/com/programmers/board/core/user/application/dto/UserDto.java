package com.programmers.board.core.user.application.dto;

import com.programmers.board.core.user.domain.Hobby;
import com.programmers.board.core.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private Hobby hobby;

    public User toEntity(){
        return User.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .hobby(this.hobby)
                .build();
    }
}
