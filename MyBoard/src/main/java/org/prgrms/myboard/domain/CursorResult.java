package org.prgrms.myboard.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
public class CursorResult<T> {
    @NotNull(message = "다음페이지 유무가 등록되어야 합니다.")
    private final Boolean hasNext;

    @NotNull(message = "이전페이지 유무가 등록되어야 합니다.")
    private final Boolean hasPrevious;

    @Min(value = 0, message = "게시물 개수가 0개 이상이어야 합니다.")
    private final int postCount;

    @Nullable
    private final List<T> values;

    @NotNull(message = "다음 커서 Id가 등록되어야 합니다.")
    private final Long nextCursorId;

    @NotNull(message = "이전 커서 Id가 등록되어야 합니다.")
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
