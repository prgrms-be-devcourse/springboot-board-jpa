package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.post.request.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.request.FindAllPostRequest;
import com.prgrms.jpa.controller.dto.post.request.UpdatePostRequest;
import com.prgrms.jpa.controller.dto.post.response.CreatePostResponse;
import com.prgrms.jpa.controller.dto.post.response.FindAllPostResponse;
import com.prgrms.jpa.controller.dto.post.response.GetByIdPostResponse;
import com.prgrms.jpa.domain.Post;
import com.prgrms.jpa.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPostDto;
import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPostIdDto;
import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPostsDto;

@Service
public class PostFacade {

    private final PostService postService;
    private final UserService userService;

    public PostFacade(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Transactional
    public CreatePostResponse create(CreatePostRequest createPostRequest) {
        User user = userService.getById(createPostRequest.getUserId());
        Post post = postService.create(createPostRequest);
        post.updateUser(user);
        return toPostIdDto(post.getId());
    }

    @Transactional(readOnly = true)
    public FindAllPostResponse findAll(FindAllPostRequest findAllPostRequest) {
        List<Post> posts = postService.findAll(findAllPostRequest);
        return toPostsDto(posts);
    }

    @Transactional(readOnly = true)
    public GetByIdPostResponse getById(long id) {
        Post post = postService.getById(id);
        return toPostDto(post);
    }

    @Transactional
    public void update(long id, UpdatePostRequest updatePostRequest) {
        Post post = postService.getById(id);
        postService.update(post, updatePostRequest);
    }
}
