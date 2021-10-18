package com.prgrms.board.dto.user;

import com.prgrms.board.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserFindRequest {

    private Long id;

    private String name;

    private int age;

    private String hobby;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserFindRequest(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.hobby = user.getHobby();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    public static UserFindRequest from(User user){
        return new UserFindRequest(user);
    }

}
