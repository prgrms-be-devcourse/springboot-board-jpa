package com.prgrms.board.dto.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PostRequestDto {
    private Long cursorId;

    @Min(value = 1, message = "{exception.post.page.minSize}")
    @Max(value = 100, message = "{exception.post.page.maxSize}")
    private Integer size;
}
