package com.kdt.springbootboardjpa;

import com.kdt.springbootboardjpa.converter.PostConverter;
import com.kdt.springbootboardjpa.domain.Post;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostDTO;
import com.kdt.springbootboardjpa.repository.PostRepository;
import com.kdt.springbootboardjpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final PostConverter converter;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostConverter converter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.converter = converter;
    }

    //TODO Exception
    public PostDTO findPost(long id) {
        return postRepository.findById(id).map(converter::convertPostDTO).orElseThrow();
    }

    public List<PostDTO> findAllPosts() {
        return postRepository.findAll().stream().map(converter::convertPostDTO).toList();
    }

    public void makePost(PostCreateRequest request) {
        var found = userRepository.findByUsername(request.getUsername());
        if (found.isEmpty()) {
            //TODO Exception
        }
        postRepository.save(converter.convertPost(request, found.get()));
    }

    @Transactional
    public void editPost(long id, PostDTO postDTO) {
        var found = postRepository.findById(id);
        if (found.isEmpty()) {
            //TODO exception
        }
        Post post = found.get();
        post.changeTitle(postDTO.getTitle());
        post.changeContent(postDTO.getContent());
    }

}
