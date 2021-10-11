package com.kdt.programmers.forum;

import com.kdt.programmers.forum.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Slf4j
class PostJpaRepositoryTest {

    @Autowired
    PostJpaRepository postRepository;

    Post post1;
    Post post2;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();

        post1 = Post.createNewPost("테스트1", "");
        post2 = Post.createNewPost("테스트2", "");

        postRepository.save(post1);
        postRepository.save(post2);
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다")
    void testSavePost() {
        // Given
        Post newPost = Post.createNewPost("new post", "");

        // When
        postRepository.save(newPost);

        // Then
        Post maybeNewPost = postRepository.findById(newPost.getId()).orElseThrow(() -> new DataRetrievalFailureException("post not saved"));
        assertThat(maybeNewPost, samePropertyValuesAs(maybeNewPost));
    }

    @Test
    @DisplayName("모든 게시글을 조회할 수 있다")
    void testFindAllPosts() {
        // Given When
        List<Post> posts = postRepository.findAll();

        // Then
        assertThat(posts.size(), equalTo(2));
    }

    @Test
    @DisplayName("특정 ID로 게시글을 조회할 수 있다")
    void testFindPostById() {
        // Given When
        Post maybePost1 = postRepository.findById(post1.getId())
            .orElseThrow(() -> new DataRetrievalFailureException("post does not exist"));

        // Then
        assertThat(maybePost1, samePropertyValuesAs(post1));
    }

    @Test
    @DisplayName("게시글을 페이지로 조회할 수 있다")
    void testPostPagination() {
        // Given
        int PAGE_NUM = 1;
        int PAGE_SIZE = 2;
        var pageRequest = PageRequest.of(PAGE_NUM, PAGE_SIZE);

        Post post3 = Post.createNewPost("테스트3", "");
        postRepository.save(post3);

        // When
        Page<Post> page = postRepository.findAll(pageRequest);

        // Then
        assertThat(page.get().count(), equalTo(1L));
        assertThat(page.get().findFirst().orElseThrow(() -> new DataRetrievalFailureException("page is empty")),
            samePropertyValuesAs(post3));
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void testUpdatePost() {
        // Given
        Post post = Post.createNewPost("테스트", "");
        postRepository.save(post);

        // When
        post.setTitle("수정 테스트");
        postRepository.save(post);

        // Then
        Post updatedPost = postRepository.findById(post.getId())
            .orElseThrow(() -> new DataRetrievalFailureException("post does not exist"));
        assertThat(updatedPost.getTitle(), equalTo("수정 테스트"));
    }

    @Test
    @DisplayName("게시글을 삭제할 수 있다")
    void testDeletePost() {
        // Given When
        postRepository.deleteById(post1.getId());

        // Then
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size(), equalTo(1));
    }
}
