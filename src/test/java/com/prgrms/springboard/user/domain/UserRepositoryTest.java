package com.prgrms.springboard.user.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.prgrms.springboard.post.domain.Post;
import com.prgrms.springboard.post.domain.vo.Title;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원 객체를 생성한다.")
    @Test
    void user_create() {
        // given
        User user = createUser();

        // when
        // then
        assertThat(user).isNotNull();
        assertThat(user.getCreatedBy()).isEqualTo(user.getName().getName());
    }

    @Sql(scripts = {"classpath:db/data_user.sql", "classpath:db/data_post.sql"})
    @DisplayName("회원이 작성한 게시글들을 조회할 수 있다.")
    @Test
    void findPostsByUser() {
        // given
        User user = userRepository.findById(1L).get();
        Title expectedTitle = new Title("제목입니다.");

        // when
        List<Post> posts = user.getPosts();

        // then
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        // given
        User user = createUser();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(user);
    }

    @DisplayName("전체 회원을 조회한다.")
    @Test
    void findAll() {
        // given
        User user = userRepository.save(createUser());

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users).hasSize(1)
            .contains(user);
    }

    @DisplayName("Id로 회원을 조회한다.")
    @Test
    void findById() {
        // given
        User user = userRepository.save(createUser());

        // when
        Optional<User> findUser = userRepository.findById(user.getId());

        // then
        assertThat(findUser).isNotEmpty()
            .get()
            .isEqualTo(user);
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void deleteById() {
        // given
        User user = userRepository.save(createUser());

        // when
        userRepository.deleteById(user.getId());

        // then
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    private User createUser() {
        return User.of("유민환", 26, "낚시");
    }

}
