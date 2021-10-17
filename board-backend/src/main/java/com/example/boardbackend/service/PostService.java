package com.example.boardbackend.service;

import com.example.boardbackend.common.error.exception.NotFoundException;
import com.example.boardbackend.domain.Post;
import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.common.converter.DtoConverter;
import com.example.boardbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {
    private final PostRepository postRepository;
    private final DtoConverter dtoConverter;

    @Transactional
    public PostDto savePost(PostDto postDto) {
        Post post = dtoConverter.convetToPostEntity(postDto);
        Post saved = postRepository.save(post);
        return dtoConverter.convertToPostDto(saved);
    }

    public Page<PostDto> findPostsAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(dtoConverter::convertToPostDto);
    }

    public Long countPostsAll(){
        return postRepository.count();
    }

    public List<PostDto> findPostsByUserId(Long userId) {
        return postRepository.findByCreatedBy(userId).stream()
                .map(dtoConverter::convertToPostDto)
                .collect(Collectors.toList());
    }

    public PostDto findPostById(Long id) {
        return postRepository.findById(id)
                .map(dtoConverter::convertToPostDto)
                .orElseThrow(() -> new NotFoundException("해당 ID의 게시물을 찾을 수 없습니다"));
    }

    @Transactional
    public PostDto updatePostById(Long id, String newTitle, String newContent) {
        Optional<Post> byId = postRepository.findById(id);
        if(byId.isEmpty())
            throw new NotFoundException("해당 ID의 게시물을 찾을 수 없습니다");
        Post entity = byId.get();
        entity.setTitle(newTitle);
        entity.setContent(newContent);
        // 트랜잭션이 끝날때 flush
        return dtoConverter.convertToPostDto(entity);
    }

    @Transactional
    public Long updateViewById(Long id, Long newView) {
        Post entity = postRepository.findById(id).get();
        entity.setView(newView);
        return entity.getView();
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

}
