package com.example.springbootboardjpa.domain.post.service;

import com.example.springbootboardjpa.domain.member.repository.MemberRepository;
import com.example.springbootboardjpa.domain.post.dto.request.PostSaveRequestDto;
import com.example.springbootboardjpa.domain.post.dto.request.PostUpdateRequestDto;
import com.example.springbootboardjpa.domain.post.entity.Post;
import com.example.springbootboardjpa.domain.post.repository.PostRepository;
import com.example.springbootboardjpa.global.exception.CustomException;
import com.example.springbootboardjpa.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.springbootboardjpa.domain.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public Post save(PostSaveRequestDto postSaveRequestDto) {
    Member member = getMember(postSaveRequestDto.getMemberId());

    Post post = PostSaveRequestDto.toPost(postSaveRequestDto, member);

    return postRepository.save(post);
  }

  public Page<Post> findAll(PageRequest pageRequest) {
    return postRepository.findAll(pageRequest);
  }

  public Post findById(long id) {
    return getPost(id);
  }

  @Transactional
  public Post update(Long postId,PostUpdateRequestDto postUpdateRequestDto) {
    Post post = getPost(postId);
    post.isOwner(postUpdateRequestDto.getMemberId());

    Post updatePost = postUpdateRequestDto.toPost(postUpdateRequestDto);
    post.update(updatePost);

    return post;
  }

  private Post getPost(long id) {
    return postRepository.findById(id).orElseThrow(() ->
        new CustomException(ErrorCode.POST_NOT_FOUND));
  }

  private Member getMember(long memberId) {
    return memberRepository.findById(memberId).orElseThrow(() ->
        new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }
}
