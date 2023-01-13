package com.prgrms.springbootboardjpa.post.application;

import com.prgrms.springbootboardjpa.member.domain.Member;
import com.prgrms.springbootboardjpa.member.domain.MemberRepository;
import com.prgrms.springbootboardjpa.post.dto.*;
import com.prgrms.springbootboardjpa.post.exception.NotFoundByIdPostException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("post를 jpa연동하여 생성할 수 있다.")
    void creatPostTest() {
        Member tmpMember = new Member("kiwoong", 20, "reading");
        memberRepository.save(tmpMember);
        PostInsertRequest postInsertRequest = new PostInsertRequest(tmpMember, "title1", "content1");
        PostResponse postResponse = postService.save(postInsertRequest);
        PostResponse foundPostResponse = postService.findById(postResponse.getPostId());
        assertThat(postResponse).usingRecursiveComparison().isEqualTo(foundPostResponse);
    }

    @Test
    @DisplayName("Id값을 통해 find시 해당하는 post가 없는 경우,NotFoundByIdPostException이 발생한다.")
    void findByIdSuccessTest() {
        Member tmpMember = new Member("kiwoong", 20, "reading");
        memberRepository.save(tmpMember);
        PostInsertRequest postInsertRequest = new PostInsertRequest(tmpMember, "title1", "content1");
        postService.save(postInsertRequest);
        assertThatThrownBy(() -> postService.findById(9999)).isInstanceOf(NotFoundByIdPostException.class);
    }

    @Test
    @DisplayName("페이징 처리를 할 수 있다.")
    void findAllTest() {
        Member tmpMember1 = new Member("kiwoong", 20, "reading");
        Member tmpMember2 = new Member("kiwoong22", 202, "reading22");
        memberRepository.save(tmpMember1);
        memberRepository.save(tmpMember2);

        PostInsertRequest postInsertRequest1 = new PostInsertRequest(tmpMember1, "title1", "content1");
        PostInsertRequest postInsertRequest2 = new PostInsertRequest(tmpMember2, "title2", "content2");

        PostResponse postResponse1 = postService.save(postInsertRequest1);
        PostResponse postResponse2 = postService.save(postInsertRequest2);

        PostOnePage postOnePage = postService.findOnePagePost(0);
        PostOnePage resultPage = new PostOnePage(2, 1, List.of(postResponse1, postResponse2));

        assertThat(postOnePage).usingRecursiveComparison().isEqualTo(resultPage);
    }

    @Test
    @DisplayName("모든 post들을 list형태로 가져올 수 있다")
    void findPageTest() {
        Member tmpMember1 = new Member("kiwoong", 20, "reading");
        Member tmpMember2 = new Member("kiwoong22", 202, "reading22");
        memberRepository.save(tmpMember1);
        memberRepository.save(tmpMember2);

        PostInsertRequest postInsertRequest1 = new PostInsertRequest(tmpMember1, "title1", "content1");
        PostInsertRequest postInsertRequest2 = new PostInsertRequest(tmpMember2, "title2", "content2");

        PostResponse postResponse1 = postService.save(postInsertRequest1);
        PostResponse foundPostResponse1 = postService.findById(postResponse1.getPostId());
        PostResponse postResponse2 = postService.save(postInsertRequest2);
        PostResponse foundPostResponse2 = postService.findById(postResponse2.getPostId());

        PostAllResponse postResponses = postService.findAll();

        assertThat(postResponses.getPostResponses()).contains(foundPostResponse1, foundPostResponse2);
    }

    @Test
    @DisplayName("post를 변경 할 수 있다")
    @Transactional
    void updateTest() {
        Member tmpMember = new Member("kiwoong", 20, "reading");
        memberRepository.save(tmpMember);
        PostInsertRequest postInsertRequest = new PostInsertRequest(tmpMember, "title1", "content1");
        PostResponse postResponse = postService.save(postInsertRequest);

        String newTitle = "changeTitle";
        String newContent = "changeContent";

        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(postResponse.getPostId(), newTitle, newContent);
        postService.update(postUpdateRequest);

        PostResponse changePostResponse = postService.findById(postResponse.getPostId());

        assertThat(changePostResponse.getContent()).isEqualTo(newContent);
        assertThat(changePostResponse.getTitle()).isEqualTo(newTitle);
    }
}