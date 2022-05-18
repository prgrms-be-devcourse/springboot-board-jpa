package com.kdt.springbootboardjpa;

import com.kdt.springbootboardjpa.converter.PostConverter;
import com.kdt.springbootboardjpa.domain.Post;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostDTO;
import com.kdt.springbootboardjpa.exception.UnAuthorizationAccessException;
import com.kdt.springbootboardjpa.repository.PostRepository;
import com.kdt.springbootboardjpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.NoSuchElementException;

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

    public PostDTO findPost(long id) {
        return postRepository.findById(id).map(converter::convertPostDTO).orElseThrow(
                () -> new NoSuchElementException(MessageFormat.format("ID {0}에 대한 검색 결과가 없습니다.", id))
        );
    }

    public List<PostDTO> findAllPosts() {
        return postRepository.findAll().stream().map(converter::convertPostDTO).toList();
    }

    public void makePost(PostCreateRequest request) {
        var found = userRepository.findByUsername(request.getUsername());
        if (found.isEmpty()) {
            throw new UnAuthorizationAccessException(MessageFormat.format("{0}는 존재하지 않는 유저입니다.", request.getUsername()));
        }
        postRepository.save(converter.convertPost(request, found.get()));
    }

    @Transactional
    public void editPost(long id, PostDTO postDTO) {
        var found = postRepository.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException(MessageFormat.format("ID {0}에 대한 검색 결과가 없습니다. ", id));
        }
        Post post = found.get();
        post.changeTitle(postDTO.getTitle());
        post.changeContent(postDTO.getContent());
    }
}
