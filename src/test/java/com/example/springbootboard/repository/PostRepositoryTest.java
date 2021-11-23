package com.example.springbootboard.repository;

import com.example.springbootboard.domain.Hobby;
import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.Title;
import com.example.springbootboard.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User user = null;
    @BeforeAll
    void setup() {
        user = createUser("name", 27, Hobby.SPORTS);
        userRepository.save(user);
    }

    @Test
    public void testAutowired() throws Exception {
        assertThat(postRepository).isNotNull();
    }

    @Test
    @DisplayName("Post가 저장된다")
    public void testSavePost() throws Exception {
        //given
        Post post = Post.createPost(new Title("title"), "content", user);

        //when
        Post entity = postRepository.save(post);

        //then
        assertThat(postRepository.count()).isEqualTo(1);
        assertThat(entity.getId()).isEqualTo(post.getId());
        assertThat(entity.getTitle()).isEqualTo(post.getTitle());
        assertThat(entity.getContent()).isEqualTo(post.getContent());
        assertThat(entity.getUser()).isEqualTo(post.getUser());
    }

    @Test
    @DisplayName("Post가 수정된다")
    public void testUpdatePost() throws Exception {
        //given
        Post post = Post.createPost(new Title("title"), "content", user);
        Post entity = postRepository.save(post);
        String title = "update title";
        String content = "update content";

        //when
        entity.update(new Title(title), content);

        //then
        Optional<Post> actual = postRepository.findById(entity.getId());
        assertThat(actual).isPresent();
        assertThat(actual.get().getTitle()).isEqualTo(new Title(title));
        assertThat(actual.get().getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("Post가 삭제된다")
    public void testDeletePost() throws Exception {
        //given
        Post post = Post.createPost(new Title("title"), "content", user);
        postRepository.save(post);

        //when
        postRepository.delete(post);

        //then
        Optional<Post> actual = postRepository.findById(post.getId());
        assertThat(actual).isEmpty();
    }

    private User createUser(String name, int age, Hobby hobby) {
        return User.builder()
                .createdAt(LocalDateTime.now())
                .createdBy(name)
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

}