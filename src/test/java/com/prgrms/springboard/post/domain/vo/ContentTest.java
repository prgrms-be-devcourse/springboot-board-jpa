package com.prgrms.springboard.post.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

class ContentTest {

    @DisplayName("내용 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"JPA 공부", "시큐리티 공부"})
    void content_ValidContent(String input) {
        // given
        Content content = new Content(input);

        // when
        // then
        assertThat(content).isNotNull();
    }

    @DisplayName("내용은 null, 공백, 200자 초과면 안 된다.")
    @ParameterizedTest
    @NullAndEmptySource
    void content_WrongContent(String content) {
        assertThatThrownBy(() -> new Content(content))
            .isInstanceOf(InvalidValueException.class);
    }

}
