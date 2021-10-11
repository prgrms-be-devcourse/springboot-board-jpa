package com.kdt.programmers.forum;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PostDtoTest {

    @Test
    @DisplayName("ID가 null인 객체를 만들 수 있다")
    void testCreateDtoWithNullId() {
        // Given When
        PostDto dto = new PostDto("테스트 제목", "");

        // Then
        assertThat(dto.getId(), equalTo(null));
    }

    @Test
    @DisplayName("ID는 양의 정수여야한다")
    void testInvalidId() {
        // Given When
        PostDto dto = new PostDto(1L, "valid id test", "");

        // Then
        assertThat(dto.getId(), equalTo(1L));

        assertThrows(IllegalArgumentException.class,
            () -> new PostDto(0L, "illegal id test", ""));

        assertThrows(IllegalArgumentException.class,
            () -> new PostDto(-1L, "illegal id test", ""));
    }

    @Test
    @DisplayName("게시글의 제목은 60자 이하여야한다")
    void testTitleLength() {
        // Given
        String validTitle = RandomString.make(60);
        String invalidTitle = RandomString.make(61);

        // When Then
        PostDto dto = new PostDto(validTitle, "");
        assertThat(dto.getTitle(), equalTo(validTitle));

        assertThrows(IllegalArgumentException.class,
            () -> new PostDto(invalidTitle, ""));
    }
}
