package com.study.board.fixture;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class Fixture {

    public static User createUser() {
        return new User("ndy", "득윤", "체스");
    }

    public static Post createPost() {
        return new Post("제목", "내용", createUser());
    }
}
