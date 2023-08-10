package org.prgms.springbootBoardJpa.service;

import org.prgms.springbootBoardJpa.domain.User;

public record UserInfo(
    String name,
    int age,
    String hobby
) {
    public User toEntity() {
        return new User(name, age, hobby);
    }
}
