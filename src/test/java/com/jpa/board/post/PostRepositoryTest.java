package com.jpa.board.post;

import com.jpa.board.post.Post;
import com.jpa.board.post.repository.PostRepository;
import com.jpa.board.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Slf4j
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.save(Post.builder()
                .title("test")
                .content("hello this is post.")
                .user(User.builder()
                        .name("minhee")
                        .age(30)
                        .hobby("read a book.")
                        .build())
                .build());
    }

    @Test
    @Transactional
    @DisplayName("게시글 전체 조회")
    void shouldFindAll(){
        List<Post> posts = postRepository.findAll();

        assertThat(posts.get(0).getUser().getName(), is("minhee"));
        posts.forEach(post -> log.info("posts: {}",post));
    }

    @Test
    @Transactional
    @DisplayName("ID로 post 단건 조회")
    void shouldFindById(){
        Optional<Post> posts = postRepository.findById(1L);

        assertThat(posts.orElseThrow(() -> new NullPointerException("null 발생")).getUser().getName(),  is("minhee"));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("게시글 수정(DB에 저장된거 보기위해 Rollback 설정)")
    void shouldUpdate(){
        Post post = postRepository.findById(1L).get();
        post.setContent("수정한 데이터 저장");

        postRepository.save(post);
        Post retrievedPost =  postRepository.findById(1L).get();

        assertThat(retrievedPost.getContent(), is("수정한 데이터 저장"));
    }
}




