package com.ys.board.domain.post.api;

import com.ys.board.domain.post.model.Post;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Slice;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostResponses {

    private List<PostResponse> postResponses;

    private Long cursorId;

    private boolean hasNext;

    public PostResponses(Long cursorId, Slice<Post> postSlice) {
        this.hasNext = postSlice.hasNext();
        this.cursorId = cursorId;
        this.postResponses = postSlice.getContent().stream()
            .map(PostResponse::new)
            .collect(Collectors.toList());
    }

}
