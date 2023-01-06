package com.prgrms.boardjpa.domain.post.service;

import com.prgrms.boardjpa.domain.member.Member;
import com.prgrms.boardjpa.domain.member.repository.MemberJPARepository;
import com.prgrms.boardjpa.domain.post.Post;
import com.prgrms.boardjpa.domain.post.dto.PostCreateRequestDto;
import com.prgrms.boardjpa.domain.post.dto.PostResponseDto;
import com.prgrms.boardjpa.domain.post.dto.PostUpdateRequestDto;
import com.prgrms.boardjpa.domain.post.repository.PostJPARepository;
import com.prgrms.boardjpa.exception.EntityNotFoundFromRepositoryException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostJPARepository postJPARepository;
    private final MemberJPARepository memberJPARepository;

    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        Member member = memberJPARepository.findById(
                postCreateRequestDto.getMemberId())
                        .orElseThrow(() -> new EntityNotFoundFromRepositoryException(MessageFormat.format(
                                "Member Entity를 찾을 수 없습니다. memberId = {0}",
                                postCreateRequestDto.getMemberId())));
        Post post = postCreateRequestDto.toPost();
        post.enrollWriter(member);

        return PostResponseDto.from(postJPARepository.save(post));
    }

    public PostResponseDto findPostById(long id) {
        Post post = postJPARepository.findById(id).orElseThrow(() ->
                new EntityNotFoundFromRepositoryException(MessageFormat.format("Post Entity를 찾을 수 없습니다. postId = {0}", id)));
        return PostResponseDto.from(post);
    }

    public List<PostResponseDto> findAllPost(Pageable pageable) {
        Page<Post> posts = postJPARepository.findAll(pageable);

        return posts.map(PostResponseDto::from)
                .getContent();
    }

    @Transactional
    public PostResponseDto updatePost(long id, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postJPARepository.findById(id).orElseThrow(() ->
                new EntityNotFoundFromRepositoryException(MessageFormat.format("Post Entity를 찾을 수 없습니다. postId = {0}", id)));
        post.changePostInfo(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());
        return PostResponseDto.from(post);
    }
}
