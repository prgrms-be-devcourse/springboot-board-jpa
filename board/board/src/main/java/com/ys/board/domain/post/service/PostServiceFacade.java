package com.ys.board.domain.post.service;

import com.ys.board.domain.post.Post;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.api.PostCreateResponse;
import com.ys.board.domain.user.User;
import com.ys.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceFacade {

    private final UserService userService;

    private final PostService postService;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request) {

        User findUser = userService.findById(request.getUserId());

        Post post = postService.createPost(request);
        post.changeUser(findUser);

        return new PostCreateResponse(post.getId());
    }

}
