package com.kdt;

import com.kdt.post.model.Post;
import com.kdt.post.repository.PostRepository;
import com.kdt.post.service.PostService;
import com.kdt.user.dto.UserDto;
import com.kdt.user.model.User;
import com.kdt.user.repository.UserRepository;
import com.kdt.user.service.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
public class RelationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @BeforeEach
    void setUp(){
        //Add a user
        User user = User.builder()
                .name("son")
                .age(20)
                .hobby("soccer")
                .build();
        userRepository.save(user);

        //Add a post written by the user
        Post post = Post.builder()
                .title("test-title")
                .content("this is a sample post. nice to meet you!")
                .build();
        post.setUser(user);
        postRepository.save(post);
    }

    @Test
    @Transactional
    @DisplayName("사용자 정보를 수정한다.")
    void updateUserTest(){
        //Given
        User user = userRepository.findAll().get(0);
        log.info("{}", user.toString());

        //When
        user.update("julie", user.getAge(), user.getHobby());

        //Then
        User savedUser = userRepository.findById(user.getId()).get();
        assertThat(savedUser, samePropertyValuesAs(savedUser, "posts"));
        log.info("{}", savedUser.toString());

        Post savedPost = postRepository.findById(user.getPosts().get(0).getId()).get();
        assertThat(savedPost.getUser(), samePropertyValuesAs(user, "posts"));
        log.info("{}", savedPost.getUser().toString());
    }

    @Test
    @Transactional
    @DisplayName("사용자를 삭제한다.")
    void deleteUserTest(){
        //Given
        //When
        userRepository.deleteAll();

        //Then
        assertThat(userRepository.findAll().size(), is(0));
        assertThat(postRepository.findAll().size(), is(0));
    }

    @Test
    @Transactional
    @DisplayName("게시물을 수정한다.")
    void updatePostTest(){
        //Given
        User user = userRepository.findAll().get(0);
        Post updatePost = user.getPosts().get(0);

        //When
        updatePost.update("update-title", "update-contents");

        //Then
        User savedUser = userRepository.findById(user.getId()).get();
        assertThat(savedUser.getPosts().get(0), samePropertyValuesAs(updatePost, "user"));

        Post savedPost = postRepository.findById(updatePost.getId()).get();
        assertThat(savedPost, samePropertyValuesAs(savedPost, "user"));
    }

    @Test
    @Transactional
    @DisplayName("게시물을 삭제한다.")
    void deletePostTest(){
        //Given
        Post post = postRepository.findAll().get(0);
        User user = post.getUser();

        //When
        user.getPosts().clear();

        //Then
        User savedUser = userRepository.findById(user.getId()).get();
        assertThat(savedUser.getPosts().size(), is(0));
        assertThat(postRepository.findAll().size(), is(0));
    }
}
