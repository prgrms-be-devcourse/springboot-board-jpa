package com.prgrms.springbootboardjpa.post.application;

import com.prgrms.springbootboardjpa.post.dto.*;
import com.prgrms.springbootboardjpa.post.exception.PostNotFoundByIdException;
import com.prgrms.springbootboardjpa.member.domain.Member;
import com.prgrms.springbootboardjpa.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
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

    Member tmpMember;

    @BeforeEach
    void setup() {
        tmpMember = new Member("email1", "password1", "name1", 10, "hobby1");
        memberRepository.save(tmpMember);
    }

    @Test
    @DisplayName("post를 jpa연동하여 생성할 수 있다.")
    void creatPostTest() {
        PostInsertRequest postInsertRequest = new PostInsertRequest("title1", "content1");
        PostResponse postResponse = postService.save(postInsertRequest, tmpMember);
        PostResponse foundPostResponse = postService.findById(postResponse.getPostId());
        assertThat(postResponse).usingRecursiveComparison().isEqualTo(foundPostResponse);
    }

    @Test
    @DisplayName("Id값을 통해 find시 해당하는 post가 없는 경우,NotFoundByIdPostException이 발생한다.")
    void findByIdSuccessTest() {
        PostInsertRequest postInsertRequest = new PostInsertRequest("title1", "content1");
        postService.save(postInsertRequest, tmpMember);
        assertThatThrownBy(() -> postService.findById(9999)).isInstanceOf(PostNotFoundByIdException.class);
    }

    @Test
    @DisplayName("페이징 처리를 할 수 있다.")
    void findPageTest() {
        Member tmpMember2 = new Member("email2", "password2", "name2", 20, "hobby2");
        memberRepository.save(tmpMember2);

        PostInsertRequest postInsertRequest1 = new PostInsertRequest("title1", "content1");
        PostInsertRequest postInsertRequest2 = new PostInsertRequest("title2", "content2");

        PostResponse postResponse1 = postService.save(postInsertRequest1, tmpMember);
        PostResponse postResponse2 = postService.save(postInsertRequest2, tmpMember2);

        PostOnePage postOnePage = postService.findOnePagePost(0);
        PostOnePage resultPage = new PostOnePage(2, 1, List.of(postResponse1, postResponse2));

        assertThat(postOnePage).usingRecursiveComparison().isEqualTo(resultPage);
    }

    @Test
    @DisplayName("모든 post들을 list형태로 가져올 수 있다")
    void findAllTest() {
        Member tmpMember2 = new Member("email2", "password2", "name2", 20, "hobby2");
        memberRepository.save(tmpMember2);

        PostInsertRequest postInsertRequest1 = new PostInsertRequest("title1", "content1");
        PostInsertRequest postInsertRequest2 = new PostInsertRequest("title2", "content2");

        PostResponse postResponse1 = postService.save(postInsertRequest1, tmpMember);
        PostResponse foundPostResponse1 = postService.findById(postResponse1.getPostId());
        PostResponse postResponse2 = postService.save(postInsertRequest2, tmpMember2);
        PostResponse foundPostResponse2 = postService.findById(postResponse2.getPostId());

        PostAllResponse postResponses = postService.findAll();

        //assertThat(postResponses.getPostResponses()).contains(foundPostResponse1, foundPostResponse2);
        assertThat(postResponses.getPostResponses()).contains(foundPostResponse1, foundPostResponse2);
    }

    @Test
    @DisplayName("post를 변경 할 수 있다")
    @Transactional
    void updateTest() {
        PostInsertRequest postInsertRequest = new PostInsertRequest("title1", "content1");
        PostResponse postResponse = postService.save(postInsertRequest, tmpMember);

        String newTitle = "changeTitle";
        String newContent = "changeContent";

        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(postResponse.getPostId(), tmpMember.getMemberId(), newTitle, newContent);
        postService.update(postUpdateRequest);

        PostResponse changePostResponse = postService.findById(postResponse.getPostId());

        assertThat(changePostResponse.getContent()).isEqualTo(newContent);
        assertThat(changePostResponse.getTitle()).isEqualTo(newTitle);
    }
}