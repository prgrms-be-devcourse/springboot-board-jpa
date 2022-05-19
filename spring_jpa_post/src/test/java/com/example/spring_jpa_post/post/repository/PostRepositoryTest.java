package com.example.spring_jpa_post.post.repository;

import com.example.spring_jpa_post.post.entity.Post;
import com.example.spring_jpa_post.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("게시판을 저장할 수 있다.")
    void saveTest() {
        //given
        Post post = Post.builder().title("title").content("content").build();
        //when
        postRepository.save(post);
        //then
        List<Post> all = postRepository.findAll();
        assertThat(all).isNotEmpty();
    }
}