package com.example.board.service;

import com.example.board.dto.PostDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import com.example.board.model.Post;
import com.example.board.model.User;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(PostDto postDto) {
        User user = userRepository.findById(postDto.userId()).orElseThrow(() ->
                new BaseException(ErrorMessage.USER_NOT_FOUND)
        );
        return postRepository.save(Post.from(user, postDto)).getId();
    }

    public Page<PostResponseDto> readAllPost(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponseDto::from);
    }
}
