package org.prgrms.myboard.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OffsetResult<T> {
    private final int currentPage;
    private final int lastPageIndex;
    private final int postCount;
    private final List<T> values;

    @Builder
    public OffsetResult(int currentPage, int lastPageIndex, int postCount, List<T> values) {
        this.currentPage = currentPage;
        this.lastPageIndex = lastPageIndex;
        this.postCount = postCount;
        this.values = values;
    }
}
