package org.prgrms.myboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;
import org.prgrms.myboard.repository.PostRepository;
import org.prgrms.myboard.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        Post post = postCreateRequestDto.toPost();
        User user = userRepository.findById(postCreateRequestDto.userId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 Id입니다."));
        post.allocateUser(user);
        postRepository.save(post);
        return post.toPostResponseDto();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
            new RuntimeException("존재하지 않는 id입니다."));
        return post.toPostResponseDto();
    }

    @Transactional
    public void deleteById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
            new RuntimeException("존재하지 않는 id입니다."));
        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto updateById(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
            new RuntimeException("존재하지 않는 id입니다."));
        post.update(postUpdateRequestDto);
        return post.toPostResponseDto();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {
        return postRepository.findAll()
            .stream()
            .map(Post::toPostResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByUserName(String userName) {
        return postRepository.findAllByCreatedBy(userName)
            .stream()
            .map(Post::toPostResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByUserId(Long userId) {
        return postRepository.findAllByUserId(userId)
            .stream()
            .map(Post::toPostResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts(Long cursorId, Pageable pageable) {
        return postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable)
            .stream()
            .map(Post::toPostResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public CursorResult<PostResponseDto> findAllByCursorId(Long cursorId, Pageable pageable) {
        List<PostResponseDto> posts = getPosts(cursorId, pageable);
        Long lastIdOfList = posts.isEmpty() ?
            null : posts.get(posts.size() - 1).id();
        log.info("{}", posts.get(0).content());
        return new CursorResult<>(posts, hasNext(lastIdOfList));
    }

    private Boolean hasNext(Long id) {
        return id == null ? false : postRepository.existsByIdLessThan(id);
    }
}

