package com.prgrms.springboard.post.dto;

import java.util.Objects;

import org.springframework.data.domain.Sort;

public class PageRequest {

    private static final int MIN_PAGE = 0;
    private static final int FIRST_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 50;

    private final int page;
    private final int size;
    private final Sort.Direction direction;

    public PageRequest(int page, int size, Sort.Direction direction) {
        this.page = checkPage(page);
        this.size = checkSize(size);
        this.direction = checkDirection(direction);
    }

    private int checkPage(int page) {
        if (page < MIN_PAGE) {
            return FIRST_PAGE;
        }
        return page;
    }

    private int checkSize(int size) {
        if (size > MAX_SIZE || size < MIN_SIZE) {
            return DEFAULT_SIZE;
        }
        return size;
    }

    private Sort.Direction checkDirection(Sort.Direction direction) {
        if (Objects.isNull(direction)) {
            return Sort.Direction.ASC;
        }
        return direction;
    }

    public org.springframework.data.domain.PageRequest toPageable() {
        return org.springframework.data.domain.PageRequest.of(page, size, direction, "createdAt");
    }
}
