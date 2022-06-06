package com.example.boardjpa.util;

import com.example.boardjpa.exception.ErrorCode;
import com.example.boardjpa.exception.custom.FieldBlankException;

import java.util.Objects;

public class Validation {
    static public <T> void checkNull (T object) {
        if (Objects.isNull(object)) {
            throw new FieldBlankException("필수 필드가 null 입니다.", ErrorCode.FIELD_NULL);
        }
    }

    static public void checkBlank (String object) {
        if (object.isBlank()) {
            throw new FieldBlankException("필수 필드가 빈 문자열 입니다.", ErrorCode.FIELD_BLANK);
        }
    }
}
