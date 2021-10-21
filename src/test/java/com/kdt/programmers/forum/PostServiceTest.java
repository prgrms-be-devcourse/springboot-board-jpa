package com.kdt.programmers.forum;

import com.kdt.programmers.forum.exception.PostNotFoundException;
import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.transfer.request.PostRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostJpaRepository repository;

    @Autowired
    PostService service;

    @AfterEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다")
    void testSavePost() {
        // Given
        PostRequest request = new PostRequest("test", "");

        // When
        service.savePost(request);

        // Then
        assertThat(repository.count(), equalTo(1L));
        assertThat(repository.findAll().get(0).getTitle(), equalTo("test"));
    }

    @Test
    @DisplayName("ID로 게시글을 조회할 수 있다")
    void testFindPostById() throws PostNotFoundException {
        // Given
        PostRequest request = new PostRequest("find by id test", "");
        PostDto post = service.savePost(request);

        // When
        PostDto entity = service.findPostById(post.getId());

        // Then
        assertThat(entity.getTitle(), equalTo("find by id test"));
    }

    @Test
    @DisplayName("게시글을 페이지 단위로 조회할 수 있다")
    void testFindPostPage() {
        // Given
        service.savePost(new PostRequest("post1", ""));
        service.savePost(new PostRequest("post2", ""));
        service.savePost(new PostRequest("post3", ""));
        service.savePost(new PostRequest("post4", ""));

        // When
        Page<PostDto> posts = service.findPostsByPage(PageRequest.of(1, 2));

        // Then
        assertThat(posts.get().count(), equalTo(2L));
        assertThat(posts.get()
            .findFirst()
            .orElseThrow(() -> new DataRetrievalFailureException("page is empty"))
            .getTitle(), equalTo("post3"));
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void testUpdatePost() throws PostNotFoundException {
        // Given
        PostDto post = service.savePost(new PostRequest("new post", ""));

        // When
        service.updatePost(post.getId(), new PostRequest("updated post", ""));

        // Then
        PostDto maybeUpdatedPost = service.findPostById(post.getId());
        assertThat(maybeUpdatedPost.getTitle(), equalTo("updated post"));
    }
}
