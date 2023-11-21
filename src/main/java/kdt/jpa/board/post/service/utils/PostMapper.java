package kdt.jpa.board.post.service.utils;

import kdt.jpa.board.post.domain.Post;
import kdt.jpa.board.post.service.dto.response.PostListResponse;
import kdt.jpa.board.post.service.dto.response.PostResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PostMapper {

    public static PostResponse toPostResponse(Post post) {
        return new PostResponse(post.getTitle(), post.getContent(), post.getUserName());
    }

    public static PostListResponse toPostListResponse(Page<Post> posts) {
        Page<PostResponse> responses = posts.map(PostMapper::toPostResponse);
        return new PostListResponse(responses);
    }
}
