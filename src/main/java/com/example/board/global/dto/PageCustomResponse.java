package com.example.board.global.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public record PageCustomResponse<T>(List<T> content,
                                    PageableCustom pageInfo) {

    public static <T> PageCustomResponse<T> of(List<T> stock, Pageable pageable, long totalElement) {
        return new PageCustomResponse<>(stock, PageableCustom.of(new PageImpl<>(stock, pageable, totalElement)));
    }
}
