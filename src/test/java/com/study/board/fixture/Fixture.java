package com.study.board.fixture;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.user.domain.User;

public class Fixture {

    public static User sampleUser() {
        return new User("득윤", "체스");
    }

    public static Post samplePost() {
        return new Post("제목", "내용", sampleUser());
    }
}
