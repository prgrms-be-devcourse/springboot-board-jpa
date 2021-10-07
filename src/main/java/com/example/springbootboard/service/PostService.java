package com.example.springbootboard.service;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.Title;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.dto.RequestCreatePost;
import com.example.springbootboard.dto.RequestUpdatePost;
import com.example.springbootboard.dto.ResponsePost;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long save(RequestCreatePost request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no user. id = {0}", request.getUserId())));

        Post post = postRepository.save(request.toEntity(user));

        return post.getId();
    }

    @Transactional
    public Long update(RequestUpdatePost request) {

        Post post = postRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no post. id = {0}", request.getId())));

        post.update(new Title(request.getTitle()), request.getContent());

        return null;
    }

    public ResponsePost findOne(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no post. id = {0}", postId)));

        return toDto(post);
    }


    public Page<ResponsePost> findAll(Pageable pageable) {

        return postRepository.findAll(pageable)
                .map(this::toDto);
    }

    public Page<ResponsePost> findByUser(Long userId, Pageable pageable) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("There is no user. id = {0}", userId)));

        return postRepository.findByUser(user, pageable)
                .map(this::toDto);
    }



    private ResponsePost toDto(Post post) {
        return new ResponsePost(post.getId(), post.getTitle().getTitle(), post.getContent());
    }
}

