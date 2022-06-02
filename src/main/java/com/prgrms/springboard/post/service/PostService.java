package com.prgrms.springboard.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.springboard.post.domain.Post;
import com.prgrms.springboard.post.domain.PostRepository;
import com.prgrms.springboard.post.dto.CreatePostRequest;
import com.prgrms.springboard.post.dto.ModifyPostRequest;
import com.prgrms.springboard.post.dto.PostResponse;
import com.prgrms.springboard.post.dto.PostsResponse;
import com.prgrms.springboard.post.exception.PostNotFoundException;
import com.prgrms.springboard.post.exception.UserHaveNotPermission;
import com.prgrms.springboard.user.domain.User;
import com.prgrms.springboard.user.dto.UserDto;
import com.prgrms.springboard.user.service.UserService;

@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Long createPost(CreatePostRequest postRequest) {
        UserDto userDto = userService.findOne(postRequest.getUserId());
        User user = userDto.toEntity();

        Post post = postRepository.save(Post.of(postRequest.getTitle(), postRequest.getContent(), user));
        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostResponse findOne(Long id) {
        return postRepository.findById(id)
            .map(PostResponse::from)
            .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public PostsResponse findAll(Pageable pageable) {
        Page<PostsResponse.PagePostResponse> posts = postRepository.findAll(pageable)
            .map(PostsResponse.PagePostResponse::from);

        return PostsResponse.of(posts);
    }

    public void modifyPost(Long id, ModifyPostRequest postRequest) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(id));

        UserDto userDto = userService.findOne(postRequest.getUserId());
        User user = userDto.toEntity();

        validateUser(post, user);

        post.changePostInformation(postRequest.getTitle(), postRequest.getContent());
    }

    private void validateUser(Post post, User user) {
        if (post.isNotSameUser(user)) {
            throw new UserHaveNotPermission(user.getId());
        }
    }
}
