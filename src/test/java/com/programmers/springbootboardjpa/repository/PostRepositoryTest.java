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

import java.time.LocalDateTime;
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
        Post post = new Post("user1", LocalDateTime.now(), "title1", "content1");
        Post savedPost = postRepository.save(post);

        Optional<Post> maybePost = postRepository.findById(savedPost.getId());
        assertThat(maybePost.isPresent(), is(true));
        assertThat(maybePost.get(), samePropertyValuesAs(savedPost));
    }

    @Test
    @DisplayName("post id로 계시물 조회 테스트")
    void findPostByIdTest() {
        Post post = new Post("user1", LocalDateTime.now(), "title1", "content1");
        User user = new User("user1", LocalDateTime.now(), "user1", 22L, "hobby1");

        Post savedPost = postRepository.save(post);
        savedPost.setUser(user);

        Optional<Post> maybePost = postRepository.findByIdWithUser(savedPost.getId());
        assertThat(maybePost.isPresent(), is(true));
        assertThat(maybePost.get(), samePropertyValuesAs(savedPost));
    }

    @Test
    @DisplayName("모든 게시물 조회 테스트")
    void findAllPostsTest() {
        User user = new User("user1", LocalDateTime.now(), "user1", 22L, "hobby1");

        List<Post> postList = new ArrayList<>();
        postList.add(new Post("name1", LocalDateTime.now(), "title1", "content1"));
        postList.add(new Post("name2", LocalDateTime.now(), "title2", "content2"));
        postList.add(new Post("name3", LocalDateTime.now(), "title3", "content3"));
        postList.add(new Post("name4", LocalDateTime.now(), "title4", "content4"));
        postList.add(new Post("name5", LocalDateTime.now(), "title5", "content5"));
        postList.add(new Post("name6", LocalDateTime.now(), "title6", "content6"));
        postList.add(new Post("name7", LocalDateTime.now(), "title7", "content7"));
        postList.add(new Post("name8", LocalDateTime.now(), "title8", "content8"));
        postList.add(new Post("name9", LocalDateTime.now(), "title9", "content9"));
        postList.add(new Post("name10", LocalDateTime.now(), "title10", "content10"));
        postList.add(new Post("name11", LocalDateTime.now(), "title11", "content11"));
        postList.add(new Post("name12", LocalDateTime.now(), "title12", "content12"));
        postList.add(new Post("name13", LocalDateTime.now(), "title13", "content13"));
        postList.add(new Post("name14", LocalDateTime.now(), "title14", "content14"));
        postList.add(new Post("name15", LocalDateTime.now(), "title15", "content15"));
        postList.add(new Post("name16", LocalDateTime.now(), "title16", "content16"));
        postList.add(new Post("name17", LocalDateTime.now(), "title17", "content17"));
        postList.add(new Post("name18", LocalDateTime.now(), "title18", "content18"));
        postList.add(new Post("name19", LocalDateTime.now(), "title19", "content19"));
        postList.add(new Post("name20", LocalDateTime.now(), "title20", "content20"));
        postList.forEach(post -> postRepository.save(post));
        postList.forEach(post -> post.setUser(user));

        Pageable pageable = PageRequest.of(0, 10);

        Page<Post> postPage = postRepository.findAllFetchJoinWithPaging(pageable);
        assertThat(postPage.getNumberOfElements(), is(10));
    }

}