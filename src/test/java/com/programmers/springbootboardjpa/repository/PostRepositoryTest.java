package com.programmers.springbootboardjpa.repository;

import com.programmers.springbootboardjpa.domain.post.Post;
import com.programmers.springbootboardjpa.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 생성 테스트")
    void savePostTest() {
        Post post = new Post("user1", "title1", "content1");
        Post savedPost = postRepository.save(post);

        Optional<Post> maybePost = postRepository.findById(savedPost.getId());
        assertThat(maybePost.isPresent(), is(true));
        assertThat(maybePost.get(), samePropertyValuesAs(savedPost));
    }

    @Test
    @DisplayName("post id로 계시물 조회 테스트")
    void findPostByIdTest() {
        Post post = new Post("user1", "title1", "content1");
        User user = new User("user1", "user1", 22L, "hobby1");

        Post savedPost = postRepository.save(post);
        savedPost.setUser(user);

        Optional<Post> maybePost = postRepository.findByIdWithUser(savedPost.getId());
        assertThat(maybePost.isPresent(), is(true));
        assertThat(maybePost.get(), samePropertyValuesAs(savedPost));
    }

    @Test
    @DisplayName("모든 게시물 조회 테스트")
    void findAllPostsTest() {
        User user = new User("user1", "user1", 22L, "hobby1");

        List<Post> postList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            postList.add(new Post(
                    MessageFormat.format("name{0}", i),
                    MessageFormat.format("title{0}", i),
                    MessageFormat.format("content{0}", i))
            );
        }

        postList.forEach(post -> postRepository.save(post));
        postList.forEach(post -> post.setUser(user));

        Pageable pageable = PageRequest.of(0, 10);

        Page<Post> postPage = postRepository.findAllFetchJoinWithPaging(pageable);
        assertThat(postPage.getNumberOfElements(), is(10));
    }

}