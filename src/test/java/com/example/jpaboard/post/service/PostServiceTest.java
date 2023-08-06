package com.example.jpaboard.post.service;

import com.example.jpaboard.member.domain.Age;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.service.MemberRepository;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.domain.PostRepository;
import com.example.jpaboard.global.exception.EntityNotFoundException;
import com.example.jpaboard.global.exception.PermissionDeniedEditException;
import com.example.jpaboard.post.service.dto.FindAllRequest;
import com.example.jpaboard.post.service.dto.PostResponse;
import com.example.jpaboard.post.service.dto.SaveRequest;
import com.example.jpaboard.post.service.dto.UpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;


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


    Long setupPostId1;

    Long setupPostId2;
    Long setupMemberId2;

    Long setupMemberId3;

    @BeforeEach
    void setup() {
        setupData();
    }

    @Test
    @DisplayName("setup에서 저장한 post를 필터 없이 10개 조회하여 개수를 확인한다.")
    void findAllBy_pageable_PostResponses() {
        //given
        FindAllRequest findAllRequest = new FindAllRequest("", "");

        //when
        Slice<PostResponse> findPosts = postService.findAllByFilter(findAllRequest, PageRequest.of(0, 10));

        //then
        assertThat(findPosts.getContent().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("setup에서 저장한 post를 조회하여 title과 memberName을 확인한다.")
    void findById_correctPostId_PostResponse() {
        //when
        PostResponse response = postService.findById(setupPostId1);

        //then
        String findTitle = response.title();
        String findMemberName = response.memberName();
        assertThat(findTitle).isEqualTo("별의 포스트 제목");
        assertThat(findMemberName).isEqualTo("김별");
    }

    @Test
    @DisplayName("존재하지 않는 postId를 통해 post를 조회할 때 EntityNotFoundException이 발생하는지 확인한다.")
    void findById_inCorrectPostId_EntityNotFoundException() {
        //when
        Exception exception = catchException(() -> postService.findById(setupMemberId3));

        //then
        assertThat(exception).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("post 저장 후 title과 memberName을 확인한다.")
    void savePost_correctSaveRequest_postResponse() {
        //given
        SaveRequest saveRequest = new SaveRequest(setupMemberId3, "산책의 정석", "산책의 정석 내용");

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
    void updatePost_IdAndUpdateRequest_PostResponse() {
        //given
        UpdateRequest updateRequest = new UpdateRequest("영운의 변경된 postTitle", "영운의 변경된 content", setupMemberId2);

        //when
        PostResponse postResponse = postService.updatePost(setupPostId2, updateRequest);

        //then
        String updatedTitle = postResponse.title();
        String updatedContent = postResponse.content();
        assertThat(updatedTitle).isEqualTo("영운의 변경된 postTitle");
        assertThat(updatedContent).isEqualTo("영운의 변경된 content");
    }

    @Test
    @DisplayName("setUp에서 저장한 post를 올바르지 않은 memberId로 수정할 때 PermissionDeniedEditException이 발생하는지 확인한다.")
    void updatePost_IdAndIncorrectUpdateRequest_PermissionDeniedEditException() {
        //given
        UpdateRequest updateRequest = new UpdateRequest("영운의 변경된 postTitle", "영운의 변경된 content", setupMemberId3);

        //when
        Exception exception = catchException(() -> postService.updatePost(setupPostId2, updateRequest));

        //then
        assertThat(exception).isInstanceOf(PermissionDeniedEditException.class);
    }

    @Test
    @DisplayName("존재하지 않는 postId로 post를 수정할 때 EntityNotFoundException이 발생하는지 확인한다.")
    void updatePost_incorrectIdAndUpdateRequest_EntityNotFoundException() {
        //given
        UpdateRequest updateRequest = new UpdateRequest("영운의 변경된 postTitle", "영운의 변경된 content", setupMemberId2);
        Random random = new Random();

        //when
        Exception exception = catchException(() -> postService.updatePost(random.nextLong(), updateRequest));

        //then
        assertThat(exception).isInstanceOf(EntityNotFoundException.class);
    }

    private void setupData() {
        Member member1 = new Member("김별", new Age(27), "락 부르기");
        Member member2 = new Member("윤영운", new Age(27), "저글링 돌리기");
        Member member3 = new Member("박세영", new Age(27), "산책");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        Post post1 = new Post("별의 포스트 제목", "별의 포스트 내용", member1);
        Post post2 = new Post("영운의 포스트 제목", "영운의 포스트 내용", member2);

        postRepository.save(post1);
        postRepository.save(post2);

        setupPostId1 = post1.getId();
        setupPostId2 = post2.getId();
        setupMemberId2 = member2.getId();
        setupMemberId3 = member3.getId();
    }

}
