package org.prgrms.myboard.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CursorResult<T> {
    private final Boolean hasNext;
    private final Boolean hasPrevious;
    private final int postCount;
    private final List<T> values;
    private final Long nextCursorId;
    private final Long previousCursorId;

    @Builder
    public CursorResult(Boolean hasNext, Boolean hasPrevious, int postCount, List<T> values, Long nextCursorId, Long previousCursorId) {
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.postCount = postCount;
        this.values = values;
        this.nextCursorId = nextCursorId;
        this.previousCursorId = previousCursorId;
    }
}
