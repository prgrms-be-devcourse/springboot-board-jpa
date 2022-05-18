package org.programmers.springbootboardjpa.domain.exception;

import java.time.LocalDate;

public class IllegalBirthDateException extends IllegalArgumentException {

    public IllegalBirthDateException(LocalDate localDate) {
        super("부적합한 생일 정보입니다: " + localDate);
    }

    public IllegalBirthDateException(LocalDate localDate, String detail) {
        super("부적합한 생일 정보입니다: " + localDate + "\n사유: " + detail);
    }
}