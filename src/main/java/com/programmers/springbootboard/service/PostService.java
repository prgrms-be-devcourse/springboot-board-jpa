package com.programmers.springbootboard.service;

import com.programmers.springbootboard.dto.ApiResponse;
import com.programmers.springbootboard.dto.PostRequestDto;
import com.programmers.springbootboard.dto.PostResponseDto;
import com.programmers.springbootboard.entity.Post;
import com.programmers.springbootboard.entity.User;
import com.programmers.springbootboard.handler.NotFoundException;
import com.programmers.springbootboard.repository.PostRepository;
import com.programmers.springbootboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {
        Assert.notNull(dto.getUsername(), "Username must be provided.");

        User user = userRepository.findByName(dto.getUsername())
                .orElseThrow(() -> new NotFoundException("No User found with username : " + dto.getUsername()));

        return PostResponseDto.of(postRepository.save(Post.of(dto, user)));
    }

    @Transactional(readOnly = true)
    public PostResponseDto readPost(Long id) {
        Post post = postRepository.findWithUserById(id)
                .orElseThrow(() -> new NotFoundException("No Post found with id: " + id));

        return PostResponseDto.of(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> readPostPage(Pageable pageable) {
        Page<Post> postList = postRepository.findAllWithTeam(pageable);
        if (!postList.hasContent()) throw new NotFoundException("No Post found");

        return postList.map(PostResponseDto::of);
    }

    @Transactional
    public PostResponseDto updatePost(PostRequestDto dto) {
        Assert.notNull(dto.getId(), "Post Id must be provided.");

        Post post = postRepository.findById(dto.getId())
                .orElseThrow( () -> new NotFoundException("No Post found with id: " + dto.getId()));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        return PostResponseDto.of(post);
    }
}
