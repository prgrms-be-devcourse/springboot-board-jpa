package org.programmers.board.dto;

import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Name;

public class UserDTO {

    private String name;
    private int age;
    private String hobby;

    public UserDTO(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
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