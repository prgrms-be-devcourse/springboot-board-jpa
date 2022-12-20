package com.prgrms.hyuk.domain.post;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_CONTENT_FORMAT_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.hyuk.exception.InvalidContentFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class ContentTest {

    private static final String A = "a";

    @ParameterizedTest
    @ValueSource(ints = {1, 100})
    @DisplayName("컨텐츠 생성 성공")
    void testCreateSuccess(int length) {
        //given
        //when
        var content = new Content(generateStringContent(length));

        //then
        assertThat(content).isNotNull();
    }

    @Test
    @DisplayName("컨텐츠 생성 실패")
    void testCreateFailBecauseInvalidFormat() {
        //given
        String content = "";

        //when
        //then
        assertThatThrownBy(() -> new Content(content))
            .isInstanceOf(InvalidContentFormatException.class)
            .hasMessageContaining(INVALID_CONTENT_FORMAT_EXP_MSG.getMessage());
    }

    private String generateStringContent(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(A);
        }
        return sb.toString();
    }
}