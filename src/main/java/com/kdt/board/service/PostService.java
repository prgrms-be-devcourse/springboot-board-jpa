package com.kdt.board.service;

import com.kdt.board.converter.PostConverter;
import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import com.kdt.board.dto.PostDTO;
import com.kdt.board.repository.PostRepository;
import com.kdt.board.repository.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    @Autowired
    public PostService(UserRepository userRepository, PostRepository postRepository,
        PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
        this.userRepository = userRepository;
    }

    public Long savePost(PostDTO postDTO) {
        Post post = postConverter.convertPost(postDTO);
        if (postDTO.getUserDTO().getId() != null) {
            Optional<User> user = userRepository.findById(post.getUser().getId());
            if (user.isEmpty()) {
                log.error("User Empty Error", new IllegalArgumentException());
                throw new IllegalArgumentException();
            } else {
                post.getUser().setCreatedBy(post.getUser().getName());
                post.saveUser(user.get());
            }
        } else if (postDTO.getUserDTO().getId() == null) {
            post.getUser().setCreatedBy(post.getUser().getName());
            User user = userRepository.save(post.getUser());
            post.saveUser(user);
        }
        post.setCreatedBy(post.getUser().getName());
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    public PostDTO findPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        log.info("post = {}", postRepository.findAll());
        if (post.isEmpty()) {
            log.error("Post Empty Error", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }
        return postConverter.convertPostDTO(post.get());
    }

    public Page<PostDTO> findPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertPostDTO);
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            log.error("Post Empty Error", new IllegalArgumentException());
            throw new IllegalArgumentException();
        }
        if (post.get().getContent() != null) {
            post.get().setContent(postDTO.getContent());
        }
        if (post.get().getTitle() != null) {
            post.get().setTitle(postDTO.getTitle());
        }
        return postConverter.convertPostDTO(post.get());
    }
}
