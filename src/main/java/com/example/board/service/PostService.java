package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostDto;
import com.example.board.exception.PostNotFoundException;
import com.example.board.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertFromDtoToPost(postDto);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertFromPostToDto);
    }

    // TODO: 공통적인 예외 메세지 따로 뽑아 관리하기
    @Transactional
    public PostDto findById(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertFromPostToDto)
                .orElseThrow(() -> new PostNotFoundException(
                        MessageFormat.format("Post Not Found. There exists no such post with the given ID {0}", id)
                ));
    }

    @Transactional
    public Long editPost(PostDto postDto) {
        Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new PostNotFoundException(
                MessageFormat.format("Post Not Found. There exists no such post with the given ID {0}", postDto.getId())
        ));
        post.updateTitle(postDto.getTitle());
        post.updateContent(postDto.getContent());
        return postRepository.save(post).getId();
    }
}
