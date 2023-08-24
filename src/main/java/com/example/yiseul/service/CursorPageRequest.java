package com.example.yiseul.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class CursorPageRequest extends PageRequest { //대수정

    private final long cursor;

    private CursorPageRequest(Long cursor, int size, Sort sort) {
        super(0, size, sort);
        this.cursor = cursor;
    }

    public static CursorPageRequest of(Long cursor, int size, Sort sort) {
        return new CursorPageRequest(cursor, size, sort);
    }

}