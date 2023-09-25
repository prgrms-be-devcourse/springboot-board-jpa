package org.prgrms.myboard.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OffsetResult<T> {

    private final int currentPage;

    private final long totalPageCount;

    private final int postCount;

    private final List<T> values;

    @Builder
    public OffsetResult(int currentPage, long totalPageCount, int postCount, List<T> values) {
        this.currentPage = currentPage;
        this.totalPageCount = totalPageCount;
        this.postCount = postCount;
        this.values = values;
    }

}
