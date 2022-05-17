package com.example.boardjpa.dto;

import com.example.boardjpa.exception.ErrorCode;
import com.example.boardjpa.exception.custom.FieldBlankException;

import java.util.Objects;

public class UpdatePostRequestDto {
    private String content;

    public UpdatePostRequestDto(String content) {
        if (!Objects.nonNull(content)) {
            throw new FieldBlankException("필수 필드가 비어있습니다.", ErrorCode.FIELD_BLANK);
        }
        this.content = content;
    }

    public UpdatePostRequestDto() {
    }

    public String getContent() {
        return content;
    }
}
