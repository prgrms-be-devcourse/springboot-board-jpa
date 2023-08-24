package com.example.yiseul.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @Nested
    @DisplayName("나이는 0세 이상이어야 한다.")
    class validationPositiveAgeTest {

        String name = "joje";
        String hobby = "basketball";

        @ParameterizedTest
        @ValueSource(ints = {0, 100})
        @DisplayName("나이는 0세 이상이면 멤버 생성을 성공한다.")
        void positiveAgeSuccess(int age) {
            // when & then
            assertThatCode(() -> new Member(name, age, hobby)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("나이가 0세 미만이면 예외가 발생한다.")
        void positiveAgeFail() {
            // given
            int age = -1;

            // when & then
            assertThatThrownBy(() -> new Member(name, age, hobby))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("나이는 0세 이상이어야 합니다.");
        }
    }


}