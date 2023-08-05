package com.example.jpaboard;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.service.MemberRepository;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.domain.PostRepository;
import com.example.jpaboard.post.service.PostService;
import com.example.jpaboard.post.service.dto.PostResponse;
import com.example.jpaboard.post.service.dto.SaveRequest;
import com.example.jpaboard.post.service.dto.UpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class Test1 {
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setup() {}

    @Test
    void findAllBy_pageable_PostResponses() {
    }

//    @Test
//    @DisplayName("post 저장 후 title과 memberName을 확인한다.")
//    void savePost() {
//        //given
//        Member member = new Member("james", new Age(20), "기타치기");
//        memberRepository.save(member);
//
//        SaveRequest saveRequest = new SaveRequest(member.getId(), "산책의 정석", "산책의 정석 내용");
//
//        //when
//        PostResponse postResponse = postService.savePost(saveRequest);
//
//        //then
//        String savedTitle = postResponse.title();
//        String savedMemberName = postResponse.memberName();
//        assertThat(savedTitle).isEqualTo("산책의 정석");
//        assertThat(savedMemberName).isEqualTo("박세영");
//    }

}
