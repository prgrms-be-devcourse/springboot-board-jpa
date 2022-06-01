package com.hyunji.jpaboard.domain.post.domain;

import com.hyunji.jpaboard.domain.user.domain.User;
import com.hyunji.jpaboard.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private void comparePost(Post actual, Post expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getUser().getId()).isEqualTo(expected.getUser().getId());
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
    }

    private void compareUser(User actual, User expected) {
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getAge()).isEqualTo(expected.getAge());
        assertThat(actual.getHobby()).isEqualTo(expected.getHobby());
        assertThat(actual.getCreatedAt()).isEqualTo(expected.getCreatedAt());
    }

    @Test
    void 상태저장_테스트() {
        User user = new User("user01", 10, "hobby");
        userRepository.save(user);
        Post post = new Post("title01", "content...", user);
        postRepository.save(post);

        Optional<Post> maybePost = postRepository.findById(post.getId());

        assertThat(maybePost).isPresent();
        comparePost(maybePost.get(), post);
    }

    @Test
    void findPostByIdWithUser() {
        User user = new User("user01", 10, "hobby");
        userRepository.save(user);
        Post post = new Post("title01", "content...", user);
        postRepository.save(post);

        Optional<Post> maybePost = postRepository.findPostByIdWithUser(post.getId());

        assertThat(maybePost).isPresent();
        comparePost(maybePost.get(), post);
        compareUser(maybePost.get().getUser(), user);
    }

    @Test
    void 페이징_조회() {
        IntStream.range(1, 100).forEach(i ->
                {
                    User user = new User("user" + i, i, "hobby");
                    userRepository.save(user);
                    postRepository.save(new Post("title" + i, "content..." + i, user));
                }
        );
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Post> postPage = postRepository.findPageWithUser(pageRequest);

        assertThat(postPage).hasSize(10);
        assertThat(postPage.getTotalElements()).isEqualTo(99);
        assertThat(postPage.getTotalPages()).isEqualTo(10);
        assertThat(postPage.getNumber()).isEqualTo(0);
    }
}