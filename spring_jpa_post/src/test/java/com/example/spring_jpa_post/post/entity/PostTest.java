package com.example.spring_jpa_post.post.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @DisplayName("게시판 생성 성공")
    @Test
    void createPostTest() {
        assertDoesNotThrow(()->Post.builder().title("title").content("content").build());
    }

    @DisplayName("제목 혹은 내용이 없으면 생성 불가")
    @ParameterizedTest
    @CsvSource(
            {",","title,",",content","'',content","title,''","'',''"}
    )
    void failCreatePostTest(String title, String content){
        assertThrows(IllegalArgumentException.class, () -> Post.builder().title(title).content(content).build());
    }
}