package com.prgrms.hyuk.domain.post;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_TITLE_FORMAT_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.hyuk.exception.InvalidTitleFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TitleTest {

    public static final String A = "a";

    @ParameterizedTest
    @ValueSource(ints = {10, 25, 50})
    @DisplayName("제목 생성 성공")
    void testCreateTitleSuccess(int length) {
        //given
        var title = generateTitle(length);

        //when
        var newTitle = new Title(title);

        //then
        assertThat(newTitle).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 51})
    @DisplayName("제목 생성 실패")
    void testCreateTitleFailBecauseInvalidFormat(int length) {
        //given
        var title = generateTitle(length);

        //when, then
        assertThatThrownBy(() -> new Title(title))
            .isInstanceOf(InvalidTitleFormatException.class)
            .hasMessageContaining(INVALID_TITLE_FORMAT_EXP_MSG.getMessage());
    }

    private String generateTitle(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(A);
        }
        return sb.toString();
    }
}