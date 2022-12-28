package com.prgrms.board.service;

import com.prgrms.board.converter.EntityConverter;
import com.prgrms.board.domain.Member;
import com.prgrms.board.domain.Post;
import com.prgrms.board.dto.CursorResult;
import com.prgrms.board.dto.request.PostCreateDto;
import com.prgrms.board.dto.request.PostUpdateDto;
import com.prgrms.board.dto.response.PostResponseDto;
import com.prgrms.board.repository.MemberRepository;
import com.prgrms.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
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
                .orElseThrow(() -> new IllegalArgumentException("exception.member.id.null"));

        httpSession.setAttribute(SESSION_MEMBER, member);

        Post newPost = converter.createPostFromDto(postCreateDto);
        newPost.registerMember(member);

        postRepository.save(newPost);

        return newPost.getId();
    }

    @Override
    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("exception.post.id.null"));
        return converter.postEntityToDto(post);
    }

    @Override
    @Transactional
    public Long update(Long postId, PostUpdateDto updateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("exception.post.id.null"));

        Member postUpdateMember = memberRepository.findById(updateDto.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("exception.member.id.null"));

        Member registeredMember = post.getWriter();

        if (!Objects.equals(registeredMember, postUpdateMember)) {
            throw new IllegalArgumentException("exception.post.writer.notEqual");
        }

        post.changePost(updateDto);

        return post.getId();
    }

    @Override
    public CursorResult findAll(Long cursorId, Pageable pageable) {
        List<Post> posts = getPosts(cursorId, pageable);

        List<PostResponseDto> postResponseDtoList = posts.stream()
                .map(post -> converter.postEntityToDto(post))
                .collect(Collectors.toList());

        Long lastIdOfList = posts.isEmpty() ? null : posts.get(posts.size() - 1).getId();

        Boolean hasNext = hasNext(lastIdOfList);
        return new CursorResult(postResponseDtoList, hasNext);
    }

    private List<Post> getPosts(Long cursorId, Pageable pageable) {
        return cursorId == null ?
                postRepository.findAllByOrderByIdDesc(pageable) :
                postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);
    }

    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return postRepository.existsByIdLessThan(id);
    }

}
