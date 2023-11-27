package com.example.board.dto.request.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageCondition {
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_SIZE = 10;

    private Integer page;
    private Integer size;

    public void updateValidPageCondition() {
        page = isValidPage(page) ? page : DEFAULT_PAGE;
        size = isValidSize(size) ? size : DEFAULT_SIZE;
    }

    private Boolean isValidPage(Integer page) {
        return page != null && page > 0;
    }

    private Boolean isValidSize(Integer size) {
        return size != null && size > 0;
    }
}
