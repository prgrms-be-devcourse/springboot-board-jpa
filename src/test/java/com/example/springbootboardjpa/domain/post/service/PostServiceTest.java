package com.example.springbootboardjpa.domain.post.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootboardjpa.domain.member.entity.Member;
import com.example.springbootboardjpa.domain.member.repository.MemberRepository;
import com.example.springbootboardjpa.domain.post.dto.request.PostSaveRequestDto;
import com.example.springbootboardjpa.domain.post.dto.request.PostUpdateRequestDto;
import com.example.springbootboardjpa.domain.post.entity.Post;
import com.example.springbootboardjpa.domain.post.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @InjectMocks
  private PostService postService;
  @Mock
  private PostRepository postRepository;
  @Mock
  private MemberRepository memberRepository;

  private Member member;

  @BeforeEach
  void setup() {
    member = Member.builder()
        .id(1L)
        .name("장주영")
        .age(26)
        .hobby("풋살")
        .build();
  }

  @Test
  @DisplayName("게시글을 저장하면 ")
  void save() {
    //given
    String title = "제목";
    String content = "내용";

    Post post = Post.builder()
        .id(1L)
        .title(title)
        .content(content)
        .member(member)
        .build();

    PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
        .title(title)
        .content(content)
        .memberId(member.getId())
        .build();

    when(postRepository.save(any())).thenReturn(post);
    when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

    //when
    Post savedPost = postService.save(postSaveRequestDto);

    //then
    verify(postRepository).save(any());
    verify(memberRepository).findById(any());

    assertThat(savedPost.getTitle(), is(postSaveRequestDto.getTitle()));
    assertThat(savedPost.getContent(), is(postSaveRequestDto.getContent()));
    assertThat(savedPost.getMember().getId(), is(postSaveRequestDto.getMemberId()));
  }

  @Test
  @DisplayName("")
  void findAll() {
    //given
    PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
    Page<Post> posts = new PageImpl<>(List.of(Post.builder()
        .title("title")
        .content("content")
        .build()));

    when(postRepository.findAll(pageRequest)).thenReturn(posts);

    //when
    Page<Post> postPage = postService.findAll(pageRequest);

    //then
    assertThat(postPage.isEmpty(), is(false));
  }

  @Test
  @DisplayName("")
  void findById() {
    //given
    long postId = 1L;

    when(postRepository.findById(postId)).thenReturn(Optional.of(Post.builder()
        .title("title")
        .content("content")
        .build()));

    //when
    Post post = postService.findById(postId);

    //then
    assertThat(post.getId(), is(1));
  }

  @Test
  void update() {
    //given
    long postId = 1L;
    String title = "제목";
    String content = "내용";
    long memberId = 1L;

    PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
        .title("제목 수정된")
        .content(content)
        .memberId(memberId)
        .build();

    when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder()
        .id(1L)
        .title(title)
        .content(content)
        .member(member)
        .build()));

    //when
    Post post = postService.update(postId, postUpdateRequestDto);

    //then
    assertThat(post.getTitle(), is("제목 수정된"));
  }
}