package com.example.jpaboard.post.service;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.service.MemberRepository;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.domain.PostRepository;
import com.example.jpaboard.post.service.dto.PostResponse;
import com.example.jpaboard.post.service.dto.SaveRequest;
import com.example.jpaboard.post.service.dto.UpdateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    Long setupId1;
    Long setupId2;
    Long setupId3;
    Long setupPostId2;

    @BeforeEach
    void setup() {
        Member member1 = new Member("김별", new Age(27), "락 부르기");
        Member member2 = new Member("윤영운", new Age(27), "저글링 돌리기");
        Member member3 = new Member("박세영", new Age(27), "산책");

        Post post1 = new Post("별의 포스트 제목", "별의 포스트 내용", member1);
        Post post2 = new Post("영운의 포스트 제목", "영운의 포스트 내용", member2);
        Long memberId = member3.getId();

        postRepository.save(post1);
        postRepository.save(post2);
        memberRepository.save(member3);

        setupId1 = post1.getId();
        setupId2 = post2.getId();
        setupId3 = member3.getId();
    }

    @Test
    void findAllBy_pageable_PostResponses() {
    }

    @Test
    @DisplayName("setup에서 저장한 post를 조회하여 title과 memberName을 확인한다.")
    void findById_correctPostId_PostResponse() {
        //when
        PostResponse response = postService.findById(setupId1);

        //then
        String findTitle = response.title();
        String findMemberName = response.memberName();
        assertThat(findTitle).isEqualTo("별의 포스트 제목");
        assertThat(findMemberName).isEqualTo("김별");
    }

    @Test
    @DisplayName("post 저장 후 title과 memberName을 확인한다.")
    void savePost() {
        //given
        SaveRequest saveRequest = new SaveRequest(setupId3, "산책의 정석", "산책의 정석 내용");

        //when
        PostResponse postResponse = postService.savePost(saveRequest);

        //then
        String savedTitle = postResponse.title();
        String savedMemberName = postResponse.memberName();
        assertThat(savedTitle).isEqualTo("산책의 정석");
        assertThat(savedMemberName).isEqualTo("박세영");
    }

    @Test
    @DisplayName("setUp에서 저장한 post를 update후 title과 content를 확인한다.")
    void updatePost() {
        //given
        UpdateRequest updateRequest = new UpdateRequest("영운의 변경된 postTitle", "영운의 변경된 content", setupId2);

        //when
        PostResponse postResponse = postService.updatePost(setupPostId2, updateRequest);

        //then
        String updatedTitle = postResponse.title();
        String updatedContent = postResponse.content();
        assertThat(updatedTitle).isEqualTo("영운의 변경된 postTitle");
        assertThat(updatedContent).isEqualTo("영운의 변경된 content");
    }
}