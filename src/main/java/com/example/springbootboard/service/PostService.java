package com.example.springbootboard.service;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.Title;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.dto.response.PostDto;
import com.example.springbootboard.dto.request.RequestCreatePost;
import com.example.springbootboard.dto.request.RequestUpdatePost;
import com.example.springbootboard.dto.PagePostDto;
import com.example.springbootboard.error.exception.EntityNotFoundException;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(RequestCreatePost request) {

        User user = request.getUserDto().toEntity();
        userRepository.save(user);

        Post post = postRepository.save(request.toEntity(user));

        return post.getId();
    }

    @Transactional
    public Long update(Long id, RequestUpdatePost request) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("There is no post. id = {0}", id)));

        post.update(new Title(request.getTitle()), request.getContent());

        return id;
    }

    public PostDto findOne(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("There is no post. id = {0}", postId)));

        return toDto(post);
    }


    public PagePostDto findAll(Pageable pageable) {
        Page<PostDto> result = postRepository.findAll(pageable)
                .map(this::toDto);

        return toDto(result);
    }



    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    private PostDto toDto(Post post) {
        return PostDto.builder()
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .postId(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent())
                .build();
    }

    private PagePostDto toDto(Page<PostDto> result) {
        return PagePostDto.builder()
                .posts(result.getContent())
                .page(result.getNumber())
                .size(result.getNumberOfElements())
                .first(result.isFirst())
                .last(result.isLast())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages()).build();
    }
}

