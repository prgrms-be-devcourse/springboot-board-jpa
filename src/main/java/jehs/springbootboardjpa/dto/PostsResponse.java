package jehs.springbootboardjpa.dto;

import jehs.springbootboardjpa.entity.Post;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PostsResponse {

    private final List<PostResponse> content;
    private final int totalPages;
    private final long totalElements;

    public PostsResponse(Page<Post> postsPage) {
        this.content = postsPage.getContent().stream().map(PostResponse::new).toList();
        this.totalPages = postsPage.getTotalPages();
        this.totalElements = postsPage.getTotalElements();
    }
}
