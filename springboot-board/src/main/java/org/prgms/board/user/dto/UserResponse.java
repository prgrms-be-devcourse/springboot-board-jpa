package org.prgms.board.user.dto;

import lombok.Getter;
import org.prgms.board.domain.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private Long id;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.age = entity.getAge();
        this.hobby = entity.getHobby();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }
}
