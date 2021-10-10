package com.example.springbootboard.service;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.Title;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.dto.RequestCreatePost;
import com.example.springbootboard.dto.RequestUpdatePost;
import com.example.springbootboard.dto.ResponsePagePost;
import com.example.springbootboard.dto.ResponsePost;
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

        User user = request.getRequestUser().toEntity();
        userRepository.save(user);

        Post post = postRepository.save(request.toEntity(user));

        return post.getId();
    }

    @Transactional
    public Long update(Long id, RequestUpdatePost request) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no post. id = {0}", id)));

        post.update(new Title(request.getTitle()), request.getContent());

        return id;
    }

    public ResponsePost findOne(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no post. id = {0}", postId)));

        return toDto(post);
    }


    public ResponsePagePost findAll(Pageable pageable) {
        Page<ResponsePost> result = postRepository.findAll(pageable)
                .map(this::toDto);

        return toDto(result);
    }



    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    private ResponsePost toDto(Post post) {
        return ResponsePost.builder()
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .id(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent())
                .build();
    }

    private ResponsePagePost toDto(Page<ResponsePost> result) {
        return ResponsePagePost.builder()
                .posts(result.getContent())
                .page(result.getNumber())
                .size(result.getNumberOfElements())
                .first(result.isFirst())
                .last(result.isLast())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages()).build();
    }
}

