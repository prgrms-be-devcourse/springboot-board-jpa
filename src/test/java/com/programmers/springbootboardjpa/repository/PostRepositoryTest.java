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
        postList.add(new Post("name1", "title1", "content1"));
        postList.add(new Post("name2", "title2", "content2"));
        postList.add(new Post("name3", "title3", "content3"));
        postList.add(new Post("name4", "title4", "content4"));
        postList.add(new Post("name5", "title5", "content5"));
        postList.add(new Post("name6", "title6", "content6"));
        postList.add(new Post("name7", "title7", "content7"));
        postList.add(new Post("name8", "title8", "content8"));
        postList.add(new Post("name9", "title9", "content9"));
        postList.add(new Post("name10", "title10", "content10"));
        postList.add(new Post("name11", "title11", "content11"));
        postList.add(new Post("name12", "title12", "content12"));
        postList.add(new Post("name13", "title13", "content13"));
        postList.add(new Post("name14", "title14", "content14"));
        postList.add(new Post("name15", "title15", "content15"));
        postList.add(new Post("name16", "title16", "content16"));
        postList.add(new Post("name17", "title17", "content17"));
        postList.add(new Post("name18", "title18", "content18"));
        postList.add(new Post("name19", "title19", "content19"));
        postList.add(new Post("name20", "title20", "content20"));
        postList.forEach(post -> postRepository.save(post));
        postList.forEach(post -> post.setUser(user));

        Pageable pageable = PageRequest.of(0, 10);

        Page<Post> postPage = postRepository.findAllFetchJoinWithPaging(pageable);
        assertThat(postPage.getNumberOfElements(), is(10));
    }

}