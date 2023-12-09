package com.programmers.boardjpa.post.entity;

import com.programmers.boardjpa.post.exception.PostException;
import com.programmers.boardjpa.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PostTest {

    @Test
    @DisplayName("Post 생성 시 잘못된 제목 길이는 예외를 발생시킨다.")
    void testInvalidTitleErrorException() {
        // given
        String title = "a".repeat(256);
        String content = "content for post";
        User user = new User("김뫄뫄", 10, "NETFLIX");

        // when - then
        assertThatThrownBy(() -> new Post(title, content, user))
                .isInstanceOf(PostException.class)
                .hasMessageContaining("title 길이가 올바르지 않습니다.");
    }
}
