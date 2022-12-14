package com.ys.board.domain.post.api;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostResponses {

    private List<PostResponse> postResponses;

    private Long cursorId;

    private boolean hasNext;

    public boolean hasNext() {
        return hasNext;
    }

}
