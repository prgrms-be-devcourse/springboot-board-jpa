package com.prgrms.be.app.util;

import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.User;
import com.prgrms.be.app.domain.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post covertToPost(PostDTO.CreateRequest postCreateDto, User user) {
        return new Post(postCreateDto.title(), postCreateDto.content(), user);
    }


    public PostDTO.PostsWithPaginationResponse convertToPaginationResponse(Page<Post> postPage){
        return new PostDTO.PostsWithPaginationResponse(
                postPage.getContent().stream().map(this::convertToPostsResponse).toList(),
                postPage.isFirst(),
                postPage.isLast(),
                postPage.getNumber(),
                postPage.getTotalPages()
        );
    }
    private PostDTO.PostsResponse convertToPostsResponse(Post post) {
        return new PostDTO.PostsResponse(
                post.getTitle(),
                post.getId(),
                post.getCreatedAt()
        );
    }

    public PostDTO.PostDetailResponse convertToPostDetailResponse(Post post) {
        return new PostDTO.PostDetailResponse(
                post.getTitle(),
                post.getContent(),
                post.getId(),
                post.getCreatedAt(),
                post.getCreatedBy().getId(),
                post.getCreatedBy().getName()
        );
    }
}
