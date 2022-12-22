package com.prgms.springbootboardjpa.facade;

import com.prgms.springbootboardjpa.dto.CreatePostRequest;
import com.prgms.springbootboardjpa.dto.DtoMapper;
import com.prgms.springbootboardjpa.dto.MemberDto;
import com.prgms.springbootboardjpa.model.Member;
import com.prgms.springbootboardjpa.service.MemberService;
import com.prgms.springbootboardjpa.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostFacadeTest {

    @Mock
    private PostService postService;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private PostFacade postFacade;

    @Test
    void createPost() {
        // given
        Long memberId = 1L;
        Long postId = 1L;
        String title = "title";
        String content = "content";

        MemberDto memberDto = new MemberDto(memberId, "name", 11, "hobby");
        Member member = DtoMapper.memberDtoToMember(memberDto);
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        // when
        when(memberService.getMemberById(memberId))
                .thenReturn(memberDto);
        when(postService.createPost(any(Member.class), eq(title), eq(content)))
                .thenReturn(postId);

        // then
        Long savedPostId = postFacade.createPost(new CreatePostRequest(memberId, title, content));

        assertThat(postId, is(savedPostId));
        verify(memberService, times(1)).getMemberById(memberId);
        verify(postService, times(1)).createPost(memberArgumentCaptor.capture(), eq(title), eq(content));
        assertThat(member, samePropertyValuesAs(memberArgumentCaptor.getValue()));
    }
}