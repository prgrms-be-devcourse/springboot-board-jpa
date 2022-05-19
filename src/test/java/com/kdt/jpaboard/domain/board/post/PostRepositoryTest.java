package com.kdt.jpaboard.domain.board.post;

import com.kdt.jpaboard.domain.board.user.User;
import com.kdt.jpaboard.domain.board.user.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("게시물 CRUD 테스트")
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private Posts post;
    private User user;


    @BeforeEach
    void setup() {

        user = User.builder()
                .name("beomsic")
                .age(26)
                .hobby("soccer")
                .posts(new ArrayList<>())
                .build();
        userRepository.save(user);

        post = Posts.builder()
                .title("beomseok")
                .content("재밌는 글")
                .build();

        post.updateUserInfo(user);
    }

    @AfterEach
    void reset() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("save 테스트")
    void testSave() {
        // Given

        // When
        postRepository.save(post);

        // Then
        Posts savePost = postRepository.findById(post.getId())
                .orElseThrow(RuntimeException::new);
        assertThat(savePost.getUser().getName().equals("beomsic"), is(true));
    }

    @Test
    @DisplayName("특정 게시물 찾는 테스트")
    void testFindOne() {
        // Given

        // When
        postRepository.save(post);

        // Then
        Optional<Posts> findPost = postRepository.findById(post.getId());
        assertThat(findPost.isEmpty(), is(false));

    }

    @Test
    @DisplayName("모든 게시물 찾는 테스트")
    void testFindAll() {
        // Given
        Posts test = Posts.builder()
                .title("test")
                .content("testContent")
                .build();

        // When
        postRepository.save(post);
        Posts saveTest = postRepository.save(test);

        // Then
        List<Posts> all = postRepository.findAll();
        assertThat(all, hasSize(2));
        assertThat(postRepository.existsById(saveTest.getId()), is(true));
    }

    @Test
    @DisplayName("특정 게시물 수정 테스트")
    void testUpdate() throws NotFoundException {
        // Given

        // When
        postRepository.save(post);
        post.update("test", "게시물게시물게물게굴게굴");
        Posts update = postRepository.save(post);

        // Then
        postRepository.findById(update.getId())
                .orElseThrow(() -> new NotFoundException("찾는 게시물이 없습니다."));

        assertThat(update.getId().equals(post.getId()), is(true));
        assertThat(update.getUpdatedAt().compareTo(post.getUpdatedAt()) > 0, is(true));
    }

    @Test
    @DisplayName("특정 게시물 삭제 테스트")
    void testDelete() {
        // Given

        // When
        Posts save = postRepository.save(post);
        postRepository.deleteById(save.getId());

        // Then
        Optional<Posts> posts = postRepository.findById(save.getId());
        assertThat(posts.isEmpty(), is(true));
    }

}