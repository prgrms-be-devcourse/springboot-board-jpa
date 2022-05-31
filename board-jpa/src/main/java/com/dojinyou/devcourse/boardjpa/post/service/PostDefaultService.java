package com.dojinyou.devcourse.boardjpa.post.service;

import com.dojinyou.devcourse.boardjpa.common.exception.AuthorizationException;
import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import com.dojinyou.devcourse.boardjpa.post.entity.Post;
import com.dojinyou.devcourse.boardjpa.post.respository.PostRepository;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostResponseDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostUpdateDto;
import com.dojinyou.devcourse.boardjpa.user.entity.User;
import com.dojinyou.devcourse.boardjpa.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostDefaultService implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    public PostDefaultService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void create(long userId, PostCreateDto postCreateDto) {
        if (postCreateDto == null) {
            throw new IllegalArgumentException();
        }

        User user = userService.findById(userId);

        Post newPost = new Post.Builder().title(postCreateDto.getTitle())
                                         .content(postCreateDto.getContent())
                                         .user(user)
                                         .build();

        postRepository.save(newPost);
    }

    @Override
    public PostResponseDto findById(long id) {
        validId(id);
        Post foundPost = postRepository.findById(id).orElseThrow(NotFoundException::new);

        return PostResponseDto.from(foundPost);
    }

    @Override
    @Transactional
    public void updateById(long id, PostUpdateDto postUpdateDto) {
        validId(id);

        Post foundPost = postRepository.findById(id).orElseThrow(NotFoundException::new);
        long createUserId = postUpdateDto.getUserId();

        if (foundPost.isNotCreateUserId(createUserId)) {
            throw new AuthorizationException();
        }

        foundPost.update(postUpdateDto.getTitle(), postUpdateDto.getContent());
    }

    @Override
    public List<PostResponseDto> findAll(Pageable pageable) {
        if (pageable == null ) {
            throw new IllegalArgumentException();
        }
        Page<Post> postPage = postRepository.findAll(pageable);
        if (postPage.isEmpty()) {
            throw new NotFoundException();
        }
        return postPage.stream().map(PostResponseDto::from).toList();
    }

    private void validId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
