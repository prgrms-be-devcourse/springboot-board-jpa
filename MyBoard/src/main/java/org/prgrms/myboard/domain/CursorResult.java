package org.prgrms.myboard.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class CursorResult<T> {

    private final List<T> values;
    private final Long nextCursorId;

}
