package com.kdt.board.post.application;

import com.kdt.board.common.exception.PostNotFoundException;
import com.kdt.board.common.exception.UserNotFoundException;
import com.kdt.board.post.application.dto.request.EditPostRequestDto;
import com.kdt.board.post.application.dto.request.WritePostRequestDto;
import com.kdt.board.post.application.dto.response.PostResponseDto;
import com.kdt.board.post.domain.Post;
import com.kdt.board.post.domain.repository.PostRepository;
import com.kdt.board.user.domain.User;
import com.kdt.board.user.domain.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void write(WritePostRequestDto writePostRequestDto) {
        if (writePostRequestDto == null) {
            throw new IllegalArgumentException();
        }

        final User user = userRepository.findById(writePostRequestDto.getUserId()).orElseThrow(UserNotFoundException::new);
        postRepository.save(writePostRequestDto.of(user));
    }

    @Override
    @Transactional
    public List<PostResponseDto> getAll(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException();
        }

        return PostResponseDto.listOf(postRepository.findAllOrderByCreatedAtDesc(pageable));
    }

    @Override
    @Transactional
    public PostResponseDto getOne(long id) {
        final Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return PostResponseDto.from(post);
    }

    @Override
    @Transactional
    public void edit(EditPostRequestDto editPostRequestDto) {
        if (editPostRequestDto == null) {
            throw new IllegalArgumentException();
        }

        final Post post = postRepository.findById(editPostRequestDto.getPostId()).orElseThrow(PostNotFoundException::new);
        final User user = userRepository.findById(editPostRequestDto.getUserId()).orElseThrow(UserNotFoundException::new);
        post.update(user, editPostRequestDto.getTitle(), editPostRequestDto.getContent());
    }
}
