package org.programmers.springbootboardjpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootboardjpa.domain.user.exception.IllegalBirthDateException;
import org.programmers.springbootboardjpa.domain.user.Age;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AgeTest {

    @Test
    @DisplayName("검증 - 정상 케이스")
    void normalCase() {
        Age age = new Age(LocalDate.of(1995, 1, 1));
        assertThat(age).isNotNull();
    }

    @Test
    @DisplayName("검증 - 예외 발생: 미래 날짜를 생일로 지정")
    void futureDateValue() {
        LocalDate now = LocalDate.now();
        assertThrows(IllegalBirthDateException.class,
                () -> new Age(LocalDate.now().plusYears(5)));
    }
}