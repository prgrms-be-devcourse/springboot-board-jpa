package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.domain.Post;
import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    @Autowired
    PostService(PostRepository postRepository, PostConverter postConverter){
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostEntity);
    }


    @Transactional
    public PostDto find(int id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostEntity)
                .orElseThrow(() -> new NotFoundException("포스트를 찾을 수 없습니다."));
    }

    @Transactional
    public Integer save(PostDto postDto) {
        return Integer.valueOf(postRepository.save(postConverter.convertPost(postDto)).getId());
    }


}
