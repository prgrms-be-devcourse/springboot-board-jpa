package com.devcourse.springbootboardjpa.post.util;

import com.devcourse.springbootboardjpa.user.domain.User;
import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.post.domain.Post;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostConverter {

    public static PostDTO.FindResponse postToFindResponse(Post post) {
        return PostDTO.FindResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .userName(post.getUser().getName())
                .build();
    }

    public static Post saveRequestToPost(PostDTO.SaveRequest postSaveRequest, User user) {
        return Post.builder()
                .title(postSaveRequest.getTitle())
                .content(postSaveRequest.getContent())
                .user(user)
                .build();
    }
}
