package com.example.jpaboard.domain.post;

import com.example.jpaboard.domain.user.User;
import com.example.jpaboard.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class PostRepositoryTest {
    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Nested
    class findWithPagination메서드는 {
        @Test
        @DisplayName("게시글과 유저를 페치 조인하여 페이징 조회한 결과를 반환한다")
        void 게시글과_유저를_페치_조인하여_페이징_조회한_결과를_반환한다() {
            //given
            User user = userRepository.save(new User("이름", 10, "운동"));
            postRepository.saveAllAndFlush(Arrays.asList(new Post("제목1", "내용1", user), new Post("제목1", "내용1", user)));
            PageRequest pageRequest = PageRequest.of(0, 10);

            //when
            List<Post> posts = postRepository.findWithPagination(pageRequest);

            //then
            Assertions.assertThat(posts.size()).isEqualTo(2);
            Assertions.assertThat(em.contains(posts.get(0).getUser())).isTrue();
            Assertions.assertThat(em.contains(posts.get(1).getUser())).isTrue();
        }
    }

    @Nested
    class findById메서드는 {
        @Test
        @DisplayName("게시글과 유저를 페치 조인하여 한건 조회한 결과를 반환한다")
        void 게시글과_유저를_페치_조인하여_한건_조회한_결과를_반환한다() {
            //given
            User user = userRepository.save(new User("이름", 10, "운동"));
            Post createdPost = new Post("제목1", "내용1", user);
            postRepository.save(createdPost);

            //when
            Optional<Post> post = postRepository.findById(createdPost.getId());

            //then
            Assertions.assertThat(post).isNotEmpty();
            Assertions.assertThat(em.contains(post.get().getUser())).isTrue();
        }
    }
}
