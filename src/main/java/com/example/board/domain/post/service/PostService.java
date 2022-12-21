package com.example.board.domain.post.service;

import com.example.board.common.exception.BadRequestException;
import com.example.board.common.exception.NotFoundException;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostRequest;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  public PostService(PostRepository postRepository, MemberRepository memberRepository){
    this.postRepository = postRepository;
    this.memberRepository = memberRepository;
  }


  @Transactional(readOnly = true)
  public PostResponse.Detail findById(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(
            () -> new NotFoundException(
                String.format("NotFoundException post id: %d", postId)));

    return PostResponse.Detail.from(post);
  }

  @Transactional(readOnly = true)
  public Page<PostResponse.Shortcut> findPage(Pageable pageable) {
    Page<Post> page = postRepository.findAll(pageable);

    return page.map(
        PostResponse.Shortcut::from);
  }

  public Long save(PostRequest postRequest) {
    Long memberId = postRequest.getMemberId();
    Member member = findMemberById(memberId);

    Post post = new Post(postRequest.getTitle(), postRequest.getContent(), member);
    Post savedPost = postRepository.save(post);

    return savedPost.getId();
  }

  public Long update(Long postId, PostRequest postRequest) {
    Long memberId = postRequest.getMemberId();
    Member member = findMemberById(memberId);

    Post post = postRepository.findById(postId)
        .orElseThrow(
            () -> new NotFoundException(
                String.format("NotFoundException post id: %d", postId)));

    post.changeTitle(postRequest.getTitle());
    post.changeContent(postRequest.getContent());
    post.preUpdate(member.getName());

    return post.getId();
  }

  private Member findMemberById(Long memberId){
    return memberRepository.findById(memberId)
        .orElseThrow(
            () -> new BadRequestException(
                String.format("Bad request: member_id %d isn't exist in database", memberId)));
  }
}
