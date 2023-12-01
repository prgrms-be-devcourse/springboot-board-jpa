package com.example.board.service;

import com.example.board.dto.PostDetailResponseDto;
import com.example.board.dto.PostCreateDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.PostUpdateDto;
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
    public Long save(PostCreateDto postDto) {
        User user = userRepository.findById(postDto.userId()).orElseThrow(() ->
                new BaseException(ErrorMessage.USER_NOT_FOUND)
        );
        return postRepository.save(Post.from(user, postDto)).getId();
    }

    public Page<PostResponseDto> readAllPost(Pageable pageable) {
        return postRepository.findAllByEntityGraph(pageable).map(PostResponseDto::from);
    }

    public PostDetailResponseDto readPostDetail(Long postId) {
        return PostDetailResponseDto.from(postRepository.findByIdEntityGraph(postId)
                .orElseThrow(() -> new BaseException(ErrorMessage.POST_NOT_FOUND)));
    }

    @Transactional
    public Long update(Long postId, PostUpdateDto postUpdateDto, Long userId) {
        Post post = postRepository.findByIdEntityGraph(postId)
                .orElseThrow(() -> new BaseException(ErrorMessage.POST_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorMessage.USER_NOT_FOUND));

        if (!post.getUser().equals(user)) {
            throw new BaseException(ErrorMessage.WRITER_NOT_MATCHED);
        }
        return post.update(postUpdateDto);
    }
}
