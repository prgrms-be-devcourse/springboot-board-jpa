package com.programmers.board.core.post.application;

import com.programmers.board.core.post.application.dto.PostDto;
import com.programmers.board.core.post.application.mapper.PostMapper;
import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostDto save(PostDto postDto){
        Post post = postMapper.convertToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return postMapper.convertToDto(savedPost);
    }

    @Transactional
    public PostDto findOne(Long id){
        return postRepository.findById(id)
                .map(postMapper::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("포스팅이 없습니다."));
    }

    @Transactional
    public Page<PostDto> findPosts(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postMapper::convertToDto);
    }

    @Transactional
    public PostDto update(Long id, PostDto postDto){
        Post retrievedPost = postRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("포스팅이 없습니다."));

        retrievedPost.updateTitle(postDto.getTitle());
        retrievedPost.updateContent(postDto.getContent());

        return postMapper.convertToDto(retrievedPost);
    }

}
