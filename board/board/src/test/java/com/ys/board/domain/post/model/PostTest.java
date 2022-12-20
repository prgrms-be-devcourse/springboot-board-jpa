package com.ys.board.domain.post.model;

import static org.junit.jupiter.api.Assertions.*;

import com.ys.board.domain.post.api.PostUpdateRequest;
import javax.persistence.PostUpdate;
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
        String content = "content";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new Post(title, content));
    }

    @DisplayName("Post 생성 실패 테스트 - title null 이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @EmptySource
    void createFailFirstNameIsNull(String title) {
        //given
        String content = "content";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new Post(title, content));
    }


    @DisplayName("Post 생성 실패 테스트 - content 빈 값이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void createFailLastNameIsEmpty(String content) {
        //given
        String title = "title";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new Post(title, content));
    }

    @DisplayName("Post} 생성 실패 테스트 - content null 이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @EmptySource
    void createFailLastNameIsNull(String content) {
        //given
        String title = "title";
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new Post(title, content));
    }

    @DisplayName("Post 생성 실패 테스트 - title, content 가 빈 값 이면 생성에 실패하고 예외를 던진다.")
    @ParameterizedTest
    @CsvSource(delimiterString = ",", value = {",", " , "})
    void createFailFirstNameAndLastNameIsEmpty(String title, String content) {
        //when & then
        assertThrows(IllegalArgumentException.class, () -> new Post(title, content));
    }

    @DisplayName("Post 생성 성공 테스트 - title 과 content이 존재하면 생성에 성공한다")
    @Test
    void createSuccess() {
        //given
        String title = "title";
        String content = "content";
        //when
        Post post = assertDoesNotThrow(() -> new Post(title, content));

        // then
        assertEquals(title, post.getTitle());
        assertEquals(content, post.getContent());
    }

    @DisplayName("UpdateAll 성공 테스트 - title 과 content가 존재하면 성공한다")
    @Test
    void updateAllSuccess() {
        //given
        String title = "title";
        String content = "content";

        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);
        //when
        Post post = new Post(title, content);

        post.updateAll(postUpdateRequest);

        // then
        assertEquals(updateTitle, post.getTitle());
        assertEquals(updateContent, post.getContent());
    }

}