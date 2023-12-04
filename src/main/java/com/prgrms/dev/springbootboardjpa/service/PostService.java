package com.prgrms.dev.springbootboardjpa.service;

import com.prgrms.dev.springbootboardjpa.converter.Converter;
import com.prgrms.dev.springbootboardjpa.domain.post.Post;
import com.prgrms.dev.springbootboardjpa.domain.post.PostRepository;
import com.prgrms.dev.springbootboardjpa.domain.user.User;
import com.prgrms.dev.springbootboardjpa.domain.user.UserRepository;
import com.prgrms.dev.springbootboardjpa.dto.PostCreateRequestDto;
import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.dto.PostRequestDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Converter converter; // ...

    @Transactional(readOnly = true) // flush를 굳이 하지 않는다. , 굳이 필요한가?, Transactional - 전파
    public PostDto findById(Long id) {
        return postRepository.findById(id)
                .map(converter::convertPostDto)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
                // 외부 의존적이 예외, 커스텀 예외를 만들.
    }

    @Transactional
    public Page<PostDto> getAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::convertPostDto);
    }

    @Transactional
    public PostDto create(PostCreateRequestDto postCreateRequestDto) {
        Post post = converter.convertPost(postCreateRequestDto);
        User user = userRepository.findById(postCreateRequestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다"));
        post.setUser(user);
        return converter.convertPostDto(postRepository.save(post));
    }

    @Transactional
    public PostDto update(PostRequestDto requestDto, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        post.update(requestDto.title(), requestDto.content());
        return converter.convertPostDto(post);
    }

}
