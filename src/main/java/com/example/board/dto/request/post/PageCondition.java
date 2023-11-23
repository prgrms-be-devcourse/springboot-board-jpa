package com.example.board.dto.request.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageCondition {

    private Integer page;
    private Integer size;

    public void updateValidPageCondition() {
        page = isValidPage(page) ? page : Integer.valueOf(1);
        size = isValidSize(size) ? size : Integer.valueOf(10);
    }

    private Boolean isValidPage(Integer page) {
        return page != null && page > 0;
    }

    private Boolean isValidSize(Integer size) {
        return size != null && size > 0;
    }
}
