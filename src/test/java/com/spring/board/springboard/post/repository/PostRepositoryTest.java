package com.spring.board.springboard.post.repository;


import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class PostRepositoryTest {

    private static Member member;
    private static final LocalDateTime createdAt = LocalDateTime.now();

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUser() {
        member = new Member("user@naver.com", "password1234", "이수린", 24, Hobby.SLEEP);
        memberRepository.save(member);
    }

    @Test
    @DisplayName("게시물을 생성할 수 있다.")
    void create_post_test() {
        // given
        String title = "새 게시물 제목입니다.";
        String content = "새 게시물 내용입니다.";
        Post post = new Post(title, content, createdAt, member);

        // when
        postRepository.save(post);

        Integer id = post.getId();

        Post findPost = postRepository.findById(id).get();

        // then
        assertThat(post).isEqualTo(findPost);
    }

    @Test
    @DisplayName("게시물을 삭제할 수 있다.")
    void delete_post_test() {
        // given
        String title = "삭제할 게시물";
        String content = "곧 삭제됩니다.";
        Post post = new Post(title, content, createdAt, member);

        postRepository.save(post);

        // when
        Integer id = post.getId();
        postRepository.deleteById(id);

        // then
        Optional<Post> findPost = postRepository.findById(1);
        assertThat(findPost).isEmpty();
    }

    @Test
    @DisplayName("게시물의 제목을 수정할 수 있다.")
    void update_title_test() {
        // given
        String beforeTitle = "수정 전 제목입니다.";
        String afterTitle = "수정 후 제목입니다.";
        String content = "내용은 변하지 않습니다.";

        Post post = new Post(beforeTitle, content, createdAt, member);

        postRepository.save(post);

        // when
        post.change(afterTitle, content);

        // then
        Integer id = post.getId();
        Post findPost = postRepository.findById(id).get();
        String findTitle = findPost.getTitle();
        String findContent = findPost.getContent();

        assertThat(findTitle).isEqualTo(afterTitle);
        assertThat(findContent).isEqualTo(content);
        assertThat(post).isEqualTo(findPost);
    }

    @Test
    @DisplayName("게시물의 내용을 수정할 수 있다.")
    void update_content_test() {
        // given
        String beforeContent = "수정 전 제목입니다.";
        String afterContent = "수정 후 제목입니다.";
        String title = "제목은 변하지 않습니다.";

        Post post = new Post(title, beforeContent, createdAt, member);

        postRepository.save(post);

        // when
        post.change(title, afterContent);

        // then
        Integer id = post.getId();
        Post findPost = postRepository.findById(id).get();
        String findTitle = findPost.getTitle();
        String findContent = findPost.getContent();

        assertThat(findTitle).isEqualTo(title);
        assertThat(findContent).isEqualTo(afterContent);
        assertThat(post).isEqualTo(findPost);
    }

    @Test
    @DisplayName("모든 게시물을 조회할 수 있다.")
    void find_all_test() {
        // given
        String title1 = "첫번째 게시물입니다.";
        String title2 = "두번째 게시물입니다.";
        String content1 = "첫번째 게시물 내용입니다.";
        String content2 = "두번째 게시물 내용입니다.";

        Post post1 = new Post(title1, content1, createdAt, member);

        Post post2 = new Post(title2, content2, createdAt, member);

        postRepository.saveAll(
                Lists.newArrayList(post1, post2));

        // when
        List<Post> postList = postRepository.findAll(Sort.by("id"));

        // then
        Integer expectedSize = 2;
        Integer realPostListSize = postList.size();

        Post findFirstPost = postList.get(0);
        Post findSecondPost = postList.get(1);

        assertThat(expectedSize).isEqualTo(realPostListSize);
        assertThat(findFirstPost).isEqualTo(post1);
        assertThat(findSecondPost).isEqualTo(post2);
    }

    @Test
    @DisplayName("모든 게시물을 삭제할 수 있다.")
    void find_all_delete_test() {
        // given
        String title1 = "첫번째 게시물입니다.";
        String title2 = "두번째 게시물입니다.";
        String content1 = "첫번째 게시물 내용입니다.";
        String content2 = "두번째 게시물 내용입니다.";

        Post post1 = new Post(title1, content1, createdAt, member);

        Post post2 = new Post(title2, content2, createdAt, member);

        postRepository.saveAll(
                Lists.newArrayList(post1, post2));

        // when
        postRepository.deleteAll();

        // then
        List<Post> findPostList = postRepository.findAll();

        assertThat(findPostList).isEmpty();
    }
}