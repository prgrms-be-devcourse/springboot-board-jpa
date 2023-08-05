package com.prgrms.board.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class Paging {

    private static final int DEFAULT_PAGE_SIZE = 10;

    public static Pageable createPageRequest(int page) {
        page = Math.max(page, 1);
        return PageRequest.of(page - 1, DEFAULT_PAGE_SIZE);
    }
}
