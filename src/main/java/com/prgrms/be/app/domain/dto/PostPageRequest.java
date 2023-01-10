package com.prgrms.be.app.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPageRequest {

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 20;

    private int page;
    private int size;
    private Sort.Direction direction;

    public PostPageRequest(int page, int size, Sort.Direction direction) {
        this.page = page <= 0 ? 1 : page;
        this.size = isRangeSize(size) ? size : DEFAULT_SIZE;
        this.direction = direction;
    }

    public PostPageRequest(int page, int size) {
        this(page, size, Sort.Direction.DESC);
    }

    private boolean isRangeSize(int size) {
        return size > 0 && size <= MAX_SIZE;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createdAt");
    }
}
