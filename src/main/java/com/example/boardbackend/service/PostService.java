package com.example.boardbackend.service;

import com.example.boardbackend.common.error.exception.NotFoundException;
import com.example.boardbackend.controller.SearchType;
import com.example.boardbackend.domain.Post;
import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.dto.request.UpdatePostRequest;
import com.example.boardbackend.dto.request.UpdateViewRequest;
import com.example.boardbackend.dto.response.BoardResponse;
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

    @Transactional
    public PostDto savePost(PostDto postDto) {
        Post post = Post.of(postDto);
        // insert 문은 여기서 실행되지 않고 트랜잭션이 끝날때 실행되므로 엔티티의 view는 아직 null이다.
        Post saved = postRepository.save(post);
        return PostDto.of(saved);
    }

    public Page<BoardResponse> findPostsAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostDto::of)
                .map(BoardResponse::of);
    }

    public Long countPostsAll() {
        return postRepository.count();
    }

    public List<BoardResponse> findPostsByUserId(Long userId) {
        return postRepository.findByCreatedBy(userId).stream()
                .map(PostDto::of)
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }

    public PostDto findPostById(Long id) {
        return postRepository.findById(id)
                .map(PostDto::of)
                .orElseThrow(() -> new NotFoundException("해당 ID의 게시물을 찾을 수 없습니다"));
    }

    @Transactional
    public PostDto updatePostById(Long id, UpdatePostRequest updatePostRequest) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isEmpty())
            throw new NotFoundException("해당 ID의 게시물을 찾을 수 없습니다");
        Post entity = byId.get();
        String newTitle = updatePostRequest.getTitle();
        String newContent = updatePostRequest.getContent();
        entity.updatePost(newTitle, newContent);
        return PostDto.of(entity);
    }

    @Transactional
    public Long updateViewById(Long id, UpdateViewRequest updateViewRequest) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isEmpty())
            throw new NotFoundException("해당 ID의 게시물을 찾을 수 없습니다");
        Post entity = byId.get();
        Long newView = updateViewRequest.getView();
        entity.updateView(newView);
        return entity.getView();
    }

    @Transactional
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public Page<BoardResponse> findPostsByKeyword(SearchType searchType, String keyword, Pageable pageable) {
        Page<Post> foundResult;
        // 검색 유형에 따라 분기
        if(searchType == SearchType.TITLE)
            foundResult = postRepository.findByTitleContains(keyword, pageable);
        else if(searchType == SearchType.CONTENT )
            foundResult = postRepository.findByContentContains(keyword, pageable);
        else
            foundResult = postRepository.findByUserNameContains(keyword, pageable);
        return foundResult
                .map(PostDto::of)
                .map(BoardResponse::of);
    }

}
