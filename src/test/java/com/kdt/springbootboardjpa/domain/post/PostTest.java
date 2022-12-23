package com.kdt.springbootboardjpa.domain.post;

import com.kdt.springbootboardjpa.domain.member.Hobby;
import com.kdt.springbootboardjpa.domain.member.Member;
import com.kdt.springbootboardjpa.repository.member.MemberJpaRepository;
import com.kdt.springbootboardjpa.repository.post.PostJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@DataJpaTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class PostTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    PostJpaRepository postJpaRepository;

    Member member;

    Post basicPost;

    @BeforeAll
    void setData() {
        member = Member.builder()
                .name("최은비")
                .age(24)
                .hobby(Hobby.MOVIE)
                .build();

        Member saveMember = memberJpaRepository.save(member);

        basicPost = Post.builder()
                .title("첫번째 게시판")
                .content("첫번째 게시판 생성!")
                .member(saveMember)
                .build();

        postJpaRepository.save(basicPost);
    }

    @Test
    @DisplayName("게시판(Post) 정보를 전체 조회한다.")
    void selectPost() {
        List<Post> posts = postJpaRepository.findAll();

        assertThat(posts).hasSize(1);
    }

    @Test
    @DisplayName("새로운 게시판(Post) 정보를 저장한다.")
    void insertPost() {
        Post newPost = Post.builder()
                .title("새로운 게시판")
                .content("새로운 게시판 등록합니다.")
                .member(member)
                .build();

        Post savePost = postJpaRepository.save(newPost);

        assertThat(savePost)
                .hasFieldOrPropertyWithValue("title", newPost.getTitle())
                .hasFieldOrPropertyWithValue("content", newPost.getContent());
    }

    @Test
    @DisplayName("게시판(Post) 정보를 수정한다.")
    void updatePost() {
        String updateTitle = "수정된 게시판";
        String updateContent = "게시판을 수정하였습니다.";

        postJpaRepository.findById(basicPost.getId())
                .ifPresent(post -> post.changePost(updateTitle, updateContent));

        Optional<Post> updatedPost = postJpaRepository.findById(basicPost.getId());

        assertThat(updatedPost)
                .hasValueSatisfying(p -> assertAll(
                        () -> assertThat(p.getTitle()).isEqualTo(updateTitle),
                        () -> assertThat(p.getContent()).isEqualTo(updateContent)
                ));
    }

    @Test
    @DisplayName("기존 게시판(Post) 정보를 삭제한다.")
    void deletePost() {
        postJpaRepository.delete(basicPost);

        Optional<Post> deletedPost = postJpaRepository.findById(basicPost.getId());

        assertThat(deletedPost).isEmpty();
    }
}