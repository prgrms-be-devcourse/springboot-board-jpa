package org.prgrms.myboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.domain.OffsetResult;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;
import org.prgrms.myboard.repository.PostRepository;
import org.prgrms.myboard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.prgrms.myboard.util.ErrorMessage.ID_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final long DEFAULT_CURSOR_ID = 10L;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        User user = userRepository.findById(postCreateRequestDto.userId())
            .orElseGet(() -> {
                log.error("found no userId : {}", postCreateRequestDto.userId());
                throw new RuntimeException(ID_NOT_FOUND_MESSAGE);
            });
        Post post = postCreateRequestDto.toPost(user);
        postRepository.save(post);
        return PostResponseDto.from(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
            .orElseGet(() -> {
                log.error("found no postId : {}", id);
                throw new RuntimeException(ID_NOT_FOUND_MESSAGE);
            });
        return PostResponseDto.from(post);
    }

    @Transactional
    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
            .orElseGet(() -> {
                log.error("found no postId : {}", id);
                throw new RuntimeException(ID_NOT_FOUND_MESSAGE);
            });
        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto updateById(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id)
            .orElseGet(() -> {
                log.error("found no postId : {}", id);
                throw new RuntimeException(ID_NOT_FOUND_MESSAGE);
            });
        post.update(postUpdateRequestDto.title(), postUpdateRequestDto.content());
        return PostResponseDto.from(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByUserId(Long userId) {
        return postRepository.findAllByUserId(userId)
            .stream()
            .map(PostResponseDto::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public CursorResult<PostResponseDto> findPostsByCursorPagination(Long cursorId, Integer pageSize) {
        if(pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if(pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }

        List<Post> posts = cursorId == null ?
            postRepository.findAllByOrderByIdAsc(PageRequest.of(0, pageSize)) :
            postRepository.findByIdHigherThanOrderByIdLimitByPageSize(cursorId, pageSize);

        List<PostResponseDto> values = posts.stream()
            .map(PostResponseDto::from)
            .toList();

        long nextCursorId = cursorId == null ?
            DEFAULT_CURSOR_ID :
            cursorId + pageSize;

        return new CursorResult<>(values, nextCursorId);
    }

    @Transactional(readOnly = true)
    public OffsetResult<PostResponseDto> findPostsByOffsetPagination(int page, int pageSize) {
        if(pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }

        Page<PostResponseDto> posts = postRepository.findAll(PageRequest.of(page, pageSize)).map(PostResponseDto::from);
        int currentPage = posts.getNumber();
        List<PostResponseDto> values = posts.getContent();
        int postCount = values.size();
        long totalPageCount = posts.getTotalElements();

        return OffsetResult.<PostResponseDto>builder()
            .currentPage(currentPage)
            .postCount(postCount)
            .totalPageCount(totalPageCount)
            .values(values)
            .build();
    }

}

