package org.spring.notice.fixture;

import org.spring.notice.domain.user.User;

import java.util.UUID;

public class Fixture {
    
    public static User createUser(){
        return User.create(UUID.randomUUID().toString(), "테스트유저", 100, "코딩");
    }
}
