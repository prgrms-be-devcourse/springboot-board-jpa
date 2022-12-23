package com.prgrms.board.service;

import com.prgrms.board.domain.Member;
import com.prgrms.board.domain.Post;
import com.prgrms.board.dto.CursorResult;
import com.prgrms.board.dto.request.PostCreateDto;
import com.prgrms.board.dto.response.PostResponseDto;
import com.prgrms.board.dto.request.PostUpdateDto;
import com.prgrms.board.repository.MemberRepository;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.service.converter.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    public static final String SESSION_MEMBER = "member";
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final EntityConverter converter;
    private final HttpSession httpSession;

    @Transactional
    @Override
    public Long register(PostCreateDto postCreateDto) {
        Member member = memberRepository.findById(postCreateDto.getWriterId())
                .orElseThrow(() -> new RuntimeException("잘못된 사용자 정보입니다."));

        httpSession.setAttribute(SESSION_MEMBER, member);

        Post newPost = converter.createPostFromDto(postCreateDto);
        newPost.registerMember(member);

        postRepository.save(newPost);

        return newPost.getId();
    }

    @Override
    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("잘못된 게시글 정보입니다."));
        return converter.PostEntityToDto(post);
    }

    @Override
    public CursorResult findAll(Long cursorId, Pageable pageable) {
        List<Post> posts = getPosts(cursorId, pageable);
        List<PostResponseDto> postResponseDtoList = posts.stream()
                .map(post -> converter.PostEntityToDto(post))
                .collect(Collectors.toList());

        Long lastIdOfList = posts.isEmpty() ? null : posts.get(posts.size() - 1).getId();

        Boolean hasNext = hasNext(lastIdOfList);
        return new CursorResult(postResponseDtoList, hasNext);
    }

    @Override
    @Transactional
    public Long update(PostUpdateDto updateDto) {
        Post post = postRepository.findById(updateDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        post.changeTitle(updateDto.getTitle());
        post.changeContent(updateDto.getContent());

        return post.getId();
    }

    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return postRepository.existsByIdLessThan(id);
    }

    private List<Post> getPosts(Long cursorId, Pageable pageable) {
        return cursorId == null ?
                postRepository.findAllByOrderByIdDesc(pageable) :
                postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
    }

}
