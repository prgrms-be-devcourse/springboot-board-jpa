package com.prgrms.board.post.service.dto;

import com.prgrms.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostServiceConverter {
    public Post toPost(PostDetailedParam postDetailedParam) {
        return new Post(
                postDetailedParam.title(),
                postDetailedParam.content(),
                postDetailedParam.userId()
        );
    }

    public PostShortResult toPostShortResult(Post savedPost) {
        return new PostShortResult(savedPost.getId());
    }

    public PostDetailedResult toPostDetailedResult(Post retrievedPost) {
        return new PostDetailedResult(
                retrievedPost.getId(),
                retrievedPost.getTitle(),
                retrievedPost.getContent(),
                retrievedPost.getCreatedBy(),
                retrievedPost.getCreatedDate()
        );
    }

    public PostDetailedResults toPostDetailedResults(Page<Post> retrievedPosts) {
        List<PostDetailedResult> convertedList = retrievedPosts.stream()
                .map(this::toPostDetailedResult)
                .toList();
        return new PostDetailedResults(convertedList);
    }
}
