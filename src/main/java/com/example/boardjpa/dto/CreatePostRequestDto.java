package com.example.boardjpa.dto;

import com.example.boardjpa.exception.ErrorCode;
import com.example.boardjpa.exception.custom.FieldBlankException;

import java.util.Objects;

public class CreatePostRequestDto {
    private final String title;
    private final String content;
    private final Long userId;

    public CreatePostRequestDto(String title, String content, Long userId) {
        if (!Objects.nonNull(title) || !Objects.nonNull(content) || !Objects.nonNull(userId)) {
            throw new FieldBlankException("필수 필드가 비어있습니다.", ErrorCode.FIELD_BLANK);
        }
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }
}
