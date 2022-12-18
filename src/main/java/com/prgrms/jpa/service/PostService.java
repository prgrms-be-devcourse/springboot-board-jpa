package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.CreatePostResponse;
import com.prgrms.jpa.controller.dto.post.GetByIdPostResponse;
import com.prgrms.jpa.controller.dto.post.FindAllPostResponse;
import com.prgrms.jpa.controller.dto.post.UpdatePostRequest;
import com.prgrms.jpa.domain.Post;
import com.prgrms.jpa.domain.User;
import com.prgrms.jpa.exception.EntityNotFoundException;
import com.prgrms.jpa.exception.ExceptionMessage;
import com.prgrms.jpa.repository.PostRepository;
import com.prgrms.jpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPost;
import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPostDto;
import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPostIdDto;
import static com.prgrms.jpa.utils.PostEntityDtoMapper.toPostsDto;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final String USER = "사용자";
    private static final String POST = "게시글";

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreatePostResponse create(CreatePostRequest createPostRequest) {
        User user = userRepository.findById(createPostRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), USER)));
        Post post = postRepository.save(toPost(createPostRequest, user));
        return toPostIdDto(post.getId());
    }

    @Transactional(readOnly = true)
    public FindAllPostResponse findAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return toPostsDto(page.getContent(), page.getTotalPages(), page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public GetByIdPostResponse getById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), POST)));
        return toPostDto(post);
    }

    @Transactional
    public void update(long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ENTITY_NOT_FOUND.name(), POST)));
        post.change(updatePostRequest.getTitle(), updatePostRequest.getContent());
    }
}
