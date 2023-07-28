package com.prgms.jpaBoard.domain.post.application;

import com.prgms.jpaBoard.domain.post.Post;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostSaveRequest;
import com.prgms.jpaBoard.domain.user.User;

public final class PostMapper {

    private PostMapper() {

    }

    public static Post of(PostSaveRequest postSaveRequest, User user) {
        return new Post(postSaveRequest.title(), postSaveRequest.content(), user);
    }
}
