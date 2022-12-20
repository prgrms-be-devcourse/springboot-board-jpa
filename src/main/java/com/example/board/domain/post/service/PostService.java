package com.example.board.domain.post.service;

import com.example.board.domain.common.exception.BadRequestException;
import com.example.board.domain.common.exception.NotFoundException;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostRequest;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  public Long save(PostRequest postRequest) {
    Long memberId = postRequest.getMemberId();
    Member member = memberRepository.findById(memberId)
        .orElseThrow(
            () -> new BadRequestException(
                String.format("Bad request: member_id %d isn't exist in database", memberId)));

    Post post = new Post(postRequest.getTitle(), postRequest.getContent());
    post.addMember(member);

    Post saved = postRepository.save(post);

    return saved.getId();
  }

  @Transactional(readOnly = true)
  public PostResponse.Detail findById(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(
            () -> new NotFoundException(
                String.format("NotFoundException post id: %d", postId)));

    return PostResponse.Detail.from(post);
  }

  public Long update(Long postId, PostRequest postRequest) {
    Long memberId = postRequest.getMemberId();
    Member member = memberRepository.findById(memberId)
        .orElseThrow(
            () -> new BadRequestException(
                String.format("Bad request: member_id %d isn't exist in database", memberId)));

    Post post = postRepository.findById(postId)
        .orElseThrow(
            () -> new NotFoundException(
                String.format("NotFoundException post id: %d", postId)));

    post.changeTitle(postRequest.getTitle());
    post.changeContent(postRequest.getContent());
    post.preUpdate(member.getName());

    return post.getId();
  }
}
