package com.prgrms.jpa.controller.dto.post.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindAllPostRequest {

    private Long cursorId;

    @Min(value = 1)
    private int pageSize;

    @Builder
    public FindAllPostRequest(int pageSize) {
        this.pageSize = pageSize;
    }

    @Builder
    public FindAllPostRequest(Long cursorId, int pageSize) {
        this.cursorId = cursorId;
        this.pageSize = pageSize;
    }
}
