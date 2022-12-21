package com.ys.board.domain.post.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ys.board.domain.post.api.PostUpdateRequest;
import com.ys.board.domain.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PostTest {

    @DisplayName("Post 생성 실패 테스트 - title 빈 값이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createFailFirstNameIsEmpty(String title) {
        //given
        User user = User.create("이름", 28, "취미");
        String content = "content";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> Post.create(user, title, content));
    }

    @DisplayName("Post 생성 실패 테스트 - title null 이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @EmptySource
    void createFailFirstNameIsNull(String title) {
        //given
        User user = User.create("이름", 28, "취미");
        String content = "content";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> Post.create(user, title, content));
    }


    @DisplayName("Post 생성 실패 테스트 - content 빈 값이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createFailLastNameIsEmpty(String content) {
        //given
        User user = User.create("이름", 28, "취미");
        String title = "title";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> Post.create(user, title, content));
    }

    @DisplayName("Post} 생성 실패 테스트 - content null 이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @EmptySource
    void createFailLastNameIsNull(String content) {
        //given
        User user = User.create("이름", 28, "취미");
        String title = "title";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> Post.create(user, title, content));
    }

    @DisplayName("Post 생성 실패 테스트 - title, content 가 빈 값 이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @CsvSource(delimiterString = ",", value = {",", " , "})
    void createFailFirstNameAndLastNameIsEmpty(String title, String content) {
        //given
        User user = User.create("이름", 28, "취미");
        //when & then
        assertThrows(IllegalArgumentException.class, () -> Post.create(user, title, content));
    }

    @DisplayName("Post 생성 실패 테스트 - user 가 없다면 생성에 실패한다.")
    @Test
    void createFailUserEmpty() {
        //given
        String title = "title";
        String content = "content";

        //when &then
        assertThrows(IllegalArgumentException.class, () -> Post.create(null, title, content));
    }

    @DisplayName("Post 생성 성공 테스트 - user, title, content 존재하면 생성에 성공한다")
    @Test
    void createSuccess() {
        //given
        User user = User.create("이름", 28, "취미");
        String title = "title";
        String content = "content";
        //when
        Post post = assertDoesNotThrow(() -> Post.create(user, title, content));

        // then
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
    }

    @DisplayName("UpdateAll 성공 테스트 - title 과 content가 존재하면 성공한다")
    @Test
    void updateAllSuccess() {
        //given
        User user = User.create("이름", 28, "취미");
        String title = "title";
        String content = "content";

        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);
        //when
        Post post = Post.create(user, title, content);

        post.updateAll(postUpdateRequest);

        // then
        assertEquals(updateTitle, post.getTitle());
        assertEquals(updateContent, post.getContent());
    }

}