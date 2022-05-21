package com.prgrms.springbootboardjpa.service;


import com.prgrms.springbootboardjpa.dto.Converter;
import com.prgrms.springbootboardjpa.dto.PostDto;
import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.repository.PostRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostService {

    PostRepository postRepository;

    Converter converter;

    public PostService(PostRepository postRepository, Converter converter) {
        this.postRepository = postRepository;
        this.converter = converter;
    }

    @Transactional
    public Page<PostDto> findAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts
            .map(converter::convertToPostDto);
    }

    @Transactional
    public PostDto findById(Long id) throws NotFoundException {
        Optional<Post> post = postRepository.findById(id);
        return converter.convertToPostDto(post.orElseThrow(NotFoundException::new));

    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = converter.convertToPostEntity(postDto);
        Post save = postRepository.save(post);
        return save.getId();
    }

    @Transactional
    public Long updateContent(Long id, String newContent) throws NotFoundException {
        Optional<Post> post = postRepository.findById(id);
        post.orElseThrow(NotFoundException::new).changeContent(newContent);
        return id;
    }


}

