package com.prgrms.board.service;

import com.prgrms.board.domain.Member;
import com.prgrms.board.domain.Post;
import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostResponseDto;
import com.prgrms.board.dto.PostUpdateDto;
import com.prgrms.board.repository.MemberRepository;
import com.prgrms.board.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;


import static com.prgrms.board.service.PostServiceImpl.SESSION_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
@Transactional
class PostServiceTest {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    HttpSession session;

    private Member savedMember;
    private Post savedPost;

    @BeforeEach
    void setup() {
        Member member = Member.builder()
                .name("기서")
                .age(26)
                .hobby("농구")
                .build();

        savedMember = memberRepository.save(member);

        Post post = Post.builder()
                .title("title")
                .content("this is content")
                .writer(member)
                .build();

        savedPost = postRepository.save(post);
    }

    @AfterEach
    void clear() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("저장되지 않은 회원이 게시글을 저장할 경우 예외가 발생한다.")
    void 게시글_저장_실패() {
        //given
        Long unknownId = 100L;
        PostCreateDto createDto = PostCreateDto.builder()
                .title("title1")
                .content("content1")
                .writerId(unknownId)
                .build();

        //when & then
        assertThrows(RuntimeException.class, () -> postService.register(createDto));
    }

    @Test
    @DisplayName("게시글을 단건 조회할 때 Member의 정보와 함께 조회할 수 있다.")
    void 게시글_조회_성공() {
        //given & when
        PostResponseDto postResponseDto = postService.findById(savedPost.getId());
        long count = postRepository.count();

        //then

        assertThat(postResponseDto)
                .hasFieldOrPropertyWithValue("title", savedPost.getTitle())
                .hasFieldOrPropertyWithValue("content", savedPost.getContent())
                .hasFieldOrPropertyWithValue("writerId", savedMember.getId());

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("저장되지 않은 게시글 Id로 조회할 경우 예외가 발생한다.")
    void 게시글_조회_실패() {
        //given
        Long unknownId = 100L;

        //when & then
        assertThrows(RuntimeException.class, () -> postService.findById(unknownId));
    }


    @Test
    @DisplayName("게시글의 정보를 수정할 수 있다.")
    void 게시글_수정_성공() {
        //given
        String updatedTitle = "update title";
        String updatedContent = "update content";

        PostUpdateDto updateDto = PostUpdateDto.builder()
                .postId(savedPost.getId())
                .title(updatedTitle)
                .content(updatedContent)
                .build();

        //when
        Long updatedPostId = postService.update(updateDto);
        PostResponseDto responseDto = postService.findById(updatedPostId);

        //then
        assertThat(responseDto)
                .hasFieldOrPropertyWithValue("title", updatedTitle)
                .hasFieldOrPropertyWithValue("content", updatedContent);
    }

    @Test
    @DisplayName("게시글의 CreatedBy는 세션에 저장된 Member의 name으로 자동 저장된다.")
    void 게시글_CreatedBy() {
        //given
        PostCreateDto createDto = PostCreateDto.builder().writerId(savedMember.getId())
                .title("title!")
                .content("content!")
                .build();


        //when
        Long postId = postService.register(createDto);
        Member sessionMember = (Member) session.getAttribute(SESSION_MEMBER);


        //then
        assertThat(sessionMember)
                .hasFieldOrPropertyWithValue("name", savedMember.getName())
                .hasFieldOrPropertyWithValue("age", savedMember.getAge())
                .hasFieldOrPropertyWithValue("hobby", savedMember.getHobby());

        Post findPost = postRepository.findById(postId).get();
        assertThat(findPost.getCreatedBy()).isEqualTo(sessionMember.getName());
    }
}