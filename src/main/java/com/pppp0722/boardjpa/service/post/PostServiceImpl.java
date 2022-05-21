package com.pppp0722.boardjpa.service.post;

import com.pppp0722.boardjpa.domain.post.Post;
import com.pppp0722.boardjpa.domain.post.PostRepository;
import com.pppp0722.boardjpa.web.dto.PostRequestDto;
import com.pppp0722.boardjpa.web.dto.PostResponseDto;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public PostResponseDto save(PostRequestDto postRequestDto) {
        Post post = postRepository.save(postRequestDto.to());
        return PostResponseDto.from(post);
    }

    @Override
    @Transactional
    public PostResponseDto findById(Long id) {
        return postRepository.findById(id)
            .map(PostResponseDto::from)
            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(PostResponseDto::from);
    }

    @Override
    @Transactional
    public Page<PostResponseDto> findByUserId(Long id, Pageable pageable) {
        return postRepository.findByUserId(id, pageable)
            .map(PostResponseDto::from);
    }

    @Override
    @Transactional
    public PostResponseDto update(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setUser(postRequestDto.getUserResponseDto().to());

        Post updatedPost = postRepository.save(post);

        return PostResponseDto.from(updatedPost);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
