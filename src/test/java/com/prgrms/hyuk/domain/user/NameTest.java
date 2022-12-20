package com.prgrms.hyuk.domain.user;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_NAME_FORMAT_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.hyuk.exception.InvalidNameFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    public static final String A = "a";

    @ParameterizedTest
    @ValueSource(ints = {1, 15, 30})
    @DisplayName("이름 생성 성공")
    void testCreateNameSuccess(int length) {
        //given
        var name = generateName(length);

        //when
        var newName = new Name(name);

        //then
        assertThat(newName).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 31})
    @DisplayName("이름 생성 실패")
    void testCreateNameFailBecauseInvalidFormat(int length) {
        //given
        var name = generateName(length);

        //when
        //then
        assertThatThrownBy(() -> new Name(name))
            .isInstanceOf(InvalidNameFormatException.class)
            .hasMessageContaining(INVALID_NAME_FORMAT_EXP_MSG.getMessage());
    }

    private String generateName(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(A);
        }
        return sb.toString();
    }
}