package com.prgrms.board.post.service;

import com.prgrms.board.converter.PostConverter;
import com.prgrms.board.domain.post.Post;
import com.prgrms.board.domain.post.PostRepository;
import com.prgrms.board.post.dto.PostDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostConverter postConverter;

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertPost(postDto);
        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(PostDto postDto) throws NotFoundException {
        Post findPost = postRepository.findById(postDto.getId())
                .map(post -> postConverter.convertPost(postDto))
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
        return findPost.getId();
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public PostDto findOne(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
    }
}
