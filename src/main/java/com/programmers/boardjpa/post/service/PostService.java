package com.programmers.boardjpa.post.service;

import com.programmers.boardjpa.post.dto.PostInsertRequestDto;
import com.programmers.boardjpa.post.dto.PostResponseDto;
import com.programmers.boardjpa.post.dto.PostUpdateRequestDto;
import com.programmers.boardjpa.post.entity.Post;
import com.programmers.boardjpa.post.exception.PostErrorCode;
import com.programmers.boardjpa.post.exception.PostException;
import com.programmers.boardjpa.post.mapper.PostMapper;
import com.programmers.boardjpa.post.repository.PostRepository;
import com.programmers.boardjpa.user.entity.User;
import com.programmers.boardjpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND, id));

        return postMapper.toPostResponseDto(post);
    }

    @Transactional
    public PostResponseDto insertPost(PostInsertRequestDto postInsertRequestDto) {
        User user = userRepository.findById(postInsertRequestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 user가 존재하지 않습니다. ID : " + postInsertRequestDto.userId()));

        Post post = postMapper.postInsertRequestDtoToPost(postInsertRequestDto);
        post.addUser(user);

        postRepository.save(post);

        return postMapper.toPostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(postUpdateRequestDto.postId())
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND, postUpdateRequestDto.postId()));

        post.changeTitleAndContent(postUpdateRequestDto.title(), postUpdateRequestDto.content());

        return postMapper.toPostResponseDto(post);
    }

    public List<PostResponseDto> getPosts(Pageable pageable) {
        List<Post> postList = postRepository.findAllWithUser(pageable).stream().toList();

        return postList.stream()
                .map(postMapper::toPostResponseDto)
                .toList();
    }
}
