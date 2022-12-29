package com.prgrms.board.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostTest {
    @Test
    @DisplayName("Post가 성공적으로 생성된다.")
    void Post_생성_성공() {
        Member member = Member.builder().name("기서").age(26).build();

        assertDoesNotThrow(() ->
                Post.builder()
                        .title("제목")
                        .content("내용")
                        .writer(member)
                        .build());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "   ", "\t", "\n" })
    @DisplayName("Post의 title이 null 또는 공백인 경우 post 생성에 실패한다.")
    void Post_생성_실패_title_empty(String title) {

        Member member = Member.builder().name("기서").age(26).build();

        assertThrows(IllegalArgumentException.class, () ->
                Post.builder()
                        .title(title)
                        .content("내용")
                        .writer(member)
                        .build());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    @DisplayName("Post의 content가 null 또는 공백인 경우 post 생성에 실패한다.")
    void Post_생성_실패_content_empty(String content) {
        Member member = Member.builder().name("기서").age(26).build();

        assertThrows(IllegalArgumentException.class, () ->
                Post.builder()
                        .title("제목")
                        .content(content)
                        .writer(member)
                        .build());
    }
}
