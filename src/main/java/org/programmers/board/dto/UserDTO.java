package org.programmers.board.dto;

import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Name;

public class UserDTO {

    private final String name;
    private final int age;
    private final String hobby;

    public UserDTO(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(
                new Name(userDTO.getName()),
                userDTO.getAge(),
                userDTO.getHobby()
        );
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }
}