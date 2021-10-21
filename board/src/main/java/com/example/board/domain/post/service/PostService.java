package com.example.board.domain.post.service;

import com.example.board.domain.post.converter.PostConverter;
import com.example.board.domain.post.domain.Post;
import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.post.repository.PostRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostConverter postConverter;
    private final PostRepository postRepository;

    public Long save(PostDto postDto) {
        Post post = postConverter.convertPost(postDto);
        Post save = postRepository.save(post);

        return save.getId();
    }

    public Long update(Long id, PostDto postDto) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        return post.getId();
    }

    public PostDto findOne(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

    public Page<PostDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }
}
