package com.example.board.post;

import com.example.board.member.Member;
import com.example.board.member.MemberDto;
import com.example.board.member.MemberService;
import javassist.NotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class JPAPostServiceTest {

    @Autowired
    PostService service;
    @Autowired
    MemberService memberService;

    MemberDto newMemberDto;

    @BeforeEach
    void newMember(){
        newMemberDto = new MemberDto(null,"Sangsun", 26, "Game");
        memberService.createMember(newMemberDto);
    }


    @Test
    @DisplayName("post 생성 Test")
    public void create_post_test() throws Exception {
        //given
        PostDto post = new PostDto(null,"TestPost", "createTestPost", "Sangsun");
        service.createPost(post);
        //when
        var entity = service.findById(1L);
        //then
        assertThat(post.title().equals(entity.title()), is(true));
    }

    @Test
    @DisplayName("post 수정 Teset")
    public void update_test() throws Exception {
        //given
        PostDto post = new PostDto(null,"TestPost", "createTestPost", "Sangsun");
        var before = service.createPost(post);
        PostDto updatedPost = new PostDto(before.id(), "TestPost", "updated", before.memberName());
        service.updatePost(updatedPost);
        //when
        var after = service.findById(before.id());
        //then
        assertAll(
                () -> assertThat(before.id().equals(after.id()), is(true)),
                () -> assertThat(before.title().equals(after.title()), is(true)),
                () -> assertThat(before.content().equals(after.content()), is(false)),
                () -> assertThat(before.memberName().equals(after.memberName()), is(true))
        );
    }
}