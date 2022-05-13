package org.spring.notice.fixture;

import org.spring.notice.domain.user.User;

import java.util.UUID;

public class Fixture {
    public static User createUser(){
        return User.create( "360ed88f-5bc8-4b0a-aeef-7b407a81ebc0", "테스트유저", 100, "코딩");
    }
}
