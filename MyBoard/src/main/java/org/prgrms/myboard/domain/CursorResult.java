package org.prgrms.myboard.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class CursorResult<T> {
    private List<T> values;
    private Boolean hasNext;

    public CursorResult(List<T> values, Boolean hasNext) {
        this.values = values;
        this.hasNext = hasNext;
    }
}
