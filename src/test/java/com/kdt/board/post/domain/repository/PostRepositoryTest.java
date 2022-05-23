package com.kdt.board.post.domain.repository;

import com.kdt.board.post.domain.Post;
import com.kdt.board.user.domain.User;
import com.kdt.board.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {

        @Nested
        @DisplayName("해당 id의 Post가 존재하지 않을 경우")
        class Context_with_not_existed_post {

            @Test
            @DisplayName("비어있는 Optional을 반환한다.")
            void It_response_emtpy_Optional() {
                //given
                final User user = User.builder()
                        .name("test")
                        .email("test@test.com")
                        .build();
                userRepository.save(user);
                final Post post = Post.builder()
                        .title("test")
                        .content("test")
                        .user(user)
                        .build();
                postRepository.save(post);
                flushAndClear();

                //when
                final Optional<Post> foundPost = postRepository.findById(Long.MAX_VALUE);

                //then
                assertThat(foundPost).isEmpty();
            }
        }

        @Nested
        @DisplayName("해당 id의 글이 존재할 경우")
        class Context_with_existed_post {

            @Test
            @DisplayName("User를 fetch join 하여 정상적으로 Post을 조회한다.")
            void It_response_post() {
                //given
                final User user = User.builder()
                        .name("test")
                        .email("test@test.com")
                        .build();
                userRepository.save(user);
                final Post post = Post.builder()
                        .title("test")
                        .content("test")
                        .user(user)
                        .build();
                postRepository.save(post);
                flushAndClear();

                //when
                final Optional<Post> foundPost = postRepository.findById(post.getId());

                //then
                assertThat(foundPost).isNotEmpty();
                assertThat(foundPost.get()).usingRecursiveComparison().isEqualTo(post);
            }
        }
    }

    @Nested
    @DisplayName("findAllOrderByCreatedAtDesc 메소드는")
    class Describe_findAllOrderByCreatedAtDesc {

        @Nested
        @DisplayName("주어진 Pageable 조건에 따라 최신순으로")
        class Context_with_pageable {

            @Test
            @DisplayName("Post를 조회한다.")
            void It_response_found_post() {
                //given
                final PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "createdAt");
                final User firstUser = User.builder()
                        .name("first test")
                        .email("firstTest@test.com")
                        .build();
                final User secondUser = User.builder()
                        .name("second test")
                        .email("secondTest@test.com")
                        .build();
                final List<User> users = List.of(firstUser, secondUser);
                userRepository.saveAll(users);
                final Post firstPost = Post.builder()
                        .title("first test")
                        .content("first test")
                        .user(firstUser)
                        .build();
                final Post secondPost = Post.builder()
                        .title("second test")
                        .content("second test")
                        .user(secondUser)
                        .build();
                final Post thirdPost = Post.builder()
                        .title("third test")
                        .content("third test")
                        .user(secondUser)
                        .build();
                final List<Post> posts = List.of(firstPost, secondPost, thirdPost);
                postRepository.saveAll(posts);

                //when
                final List<Post> foundPosts = postRepository.findAllOrderByCreatedAtDesc(pageRequest);

                //then
                assertThat(foundPosts).usingRecursiveComparison().isEqualTo(List.of(posts.get(2), posts.get(1)));
            }
        }
    }

    private void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }
}
