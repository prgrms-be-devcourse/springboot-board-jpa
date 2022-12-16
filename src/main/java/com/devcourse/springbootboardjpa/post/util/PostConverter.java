package com.devcourse.springbootboardjpa.post.util;

import com.devcourse.springbootboardjpa.post.domain.User;
import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.user.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public PostDTO.FindResponse postToFindResponse(Post post) {
        return PostDTO.FindResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public Post saveRequestToPost(PostDTO.SaveRequest postSaveRequest, User user) {
        return Post.builder()
                .title(postSaveRequest.getTitle())
                .content(postSaveRequest.getContent())
                .user(user)
                .build();
    }
}
