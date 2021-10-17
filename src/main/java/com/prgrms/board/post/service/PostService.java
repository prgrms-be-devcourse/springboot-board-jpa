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

    private final PostRepository postRepository;

    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        Post post = postConverter.convertPost(postDto);
        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(Long PostId, PostDto postDto) throws NotFoundException {
        Post findPost = postRepository.findById(PostId)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));
        findPost.changeTitle("수정된 게시물 제목");
        findPost.changeContent("수정된 게시물 내용");
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
