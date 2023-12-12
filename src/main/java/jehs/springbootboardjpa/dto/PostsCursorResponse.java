package jehs.springbootboardjpa.dto;

import jehs.springbootboardjpa.entity.Post;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public class PostsCursorResponse {

    private final List<PostResponse> content;
    private final boolean hasNext;

    public PostsCursorResponse(Slice<Post> postSlice) {
        this.content = postSlice.getContent().stream().map(PostResponse::new).toList();
        this.hasNext = postSlice.hasNext();
    }
}
