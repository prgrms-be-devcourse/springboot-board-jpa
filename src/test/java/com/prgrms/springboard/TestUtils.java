package com.prgrms.springboard;

import java.time.LocalDateTime;

import com.prgrms.springboard.user.domain.User;
import com.prgrms.springboard.user.domain.vo.Age;
import com.prgrms.springboard.user.domain.vo.Hobby;
import com.prgrms.springboard.user.domain.vo.Name;

public class TestUtils {
    private TestUtils() {
    }

    public static User createUser() {
        return new User("유민환", LocalDateTime.now(), 1L, new Name("유민환"), new Age(26), new Hobby("낚시"));
    }
}
