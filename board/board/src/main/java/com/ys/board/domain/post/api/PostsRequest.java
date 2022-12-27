package com.ys.board.domain.post.api;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostsRequest {

    private Long cursorId;

    @Max(value = 100, message = "최대값은 100입니다.") @Min(value = 0, message = "최소값은 0입니다. ")
    private int pageSize;

}
