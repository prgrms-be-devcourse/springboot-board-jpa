package org.prgrms.myboard.service;

import lombok.RequiredArgsConstructor;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final int PAGE_BASE_OFFSET = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        User user = userRepository.findById(postCreateRequestDto.userId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 Id입니다."));
        Post post = new Post(postCreateRequestDto.title(), postCreateRequestDto.content(), user);
        postRepository.save(post);
        return post.toPostResponseDto();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 id입니다."));
        return post.toPostResponseDto();
    }

    @Transactional
    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 id입니다."));
        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDto updateById(Long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 id입니다."));
        post.update(postUpdateRequestDto.title(), postUpdateRequestDto.content());
        return post.toPostResponseDto();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllByUserId(Long userId) {
        return postRepository.findAllByUserId(userId)
            .stream()
            .map(Post::toPostResponseDto)
            .toList();
    }

    public List<PostResponseDto> callCursorPagination(Long cursorId, int pageSize) {
        return postRepository.findByIdLessThanOrderByIdLimitByPageSize(cursorId, pageSize)
            .stream()
            .map(Post::toPostResponseDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public CursorResult<PostResponseDto> findPostsByCursorPagination(Long cursorIdCandidate, Integer pageSizeCandidate) {
        int pageSize = pageSizeCandidate == null ? DEFAULT_PAGE_SIZE : pageSizeCandidate;
        long cursorId = cursorIdCandidate == null ? (long)pageSize : cursorIdCandidate;

        List<PostResponseDto> posts = callCursorPagination(cursorId, pageSize);
        boolean hasNext = postRepository.existsByIdAfter(cursorId);
        boolean hasPrevious = postRepository.existsByIdBefore(cursorId - pageSize);

        return CursorResult.<PostResponseDto>builder()
            .hasNext(hasNext)
            .hasPrevious(hasPrevious)
            .nextCursorId(hasNext ? cursorId + pageSize : -1)
            .previousCursorId(hasPrevious ? cursorId - pageSize : -1)
            .values(posts)
            .postCount(posts.size())
            .build();
    }

    @Transactional(readOnly = true)
    public OffsetResult<PostResponseDto> findPostsByOffsetPagination(Pageable pageable) {
        Page<PostResponseDto> posts = postRepository.findAll(pageable).map(Post::toPostResponseDto);
        int currentPage = pageable.getPageNumber() + PAGE_BASE_OFFSET;
        int lastPageIndex = posts.getTotalPages();
        List<PostResponseDto> values = posts.getContent();
        int postCount = values.size();

        return OffsetResult.<PostResponseDto>builder()
            .currentPage(currentPage)
            .postCount(postCount)
            .lastPageIndex(lastPageIndex)
            .values(values)
            .build();
    }
}

