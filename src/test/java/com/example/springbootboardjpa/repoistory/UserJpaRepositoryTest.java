package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.domian.Post;
import com.example.springbootboardjpa.domian.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Slf4j
class UserJpaRepositoryTest {


    @Autowired
    PostJpaRepository postRepository;

    @Autowired
    UserJpaRepository userRepository;


    User user;
    Post post;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        user = new User("영지", 28);
        post = new Post("초밥 만드는 법", "좋은 사시미가 필요하다.", user);
    }

    @Test
    @DisplayName("user를 정상 저장한다.")
    public void saveUser() {
        // When
        var insertUser = userRepository.save(user);

        // Then
        var users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0)).isEqualTo(insertUser);
    }


    @Test
    @DisplayName("저장된 user를 id에 의해 정상 조회한다.")
    public void findUserById() {
        // Given
        var insertUser = userRepository.save(user);

        // When
        var findUser = userRepository.findById(1);

        // Then
        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get()).isEqualTo(insertUser);
        assertThat(findUser.get().getPosts().get(0)).isEqualTo(post);
    }

    @Test
    @DisplayName("저장된 user를 name에 의해 정상 조회한다.")
    public void findUserByName() {
        // Given
        var insertUser = userRepository.save(user);

        // When
        var findUser = userRepository.findByName("영지");

        // Then
        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get()).isEqualTo(insertUser);
        assertThat(findUser.get().getPosts().get(0)).isEqualTo(post);
    }

    @Test
    @DisplayName("user name을 정상 업데이트 한다.")
    public void updateUser() {
        // Given
        var insertUser = userRepository.save(user);
        insertUser.changeName("영지2");

        // When
        var findUser = userRepository.findById(1);

        // Then
        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get().getName()).isEqualTo("영지2");
        assertThat(findUser.get().getPosts().get(0)).isEqualTo(post);
    }

    @Test
    @DisplayName("user post 삭제시 해당 post 객체가 삭제된다.")
    public void deleteUserPost() {
        // Given
        var insertUser = userRepository.save(user);
        insertUser.getPosts().remove(0);

        // When
        var findUser = userRepository.findById(1);
        var findPost = postRepository.findAll();

        // Then
        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.get().getPosts().size()).isEqualTo(0);
        assertThat(findPost.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("user를 정상 삭제한다.")
    public void deleteUser() {
        // Given
        userRepository.save(user);
        userRepository.delete(user);

        // When
        var findUser = userRepository.findById(1);
        var findPost = postRepository.findById(1);

        // Then
        assertThat(findUser.isEmpty()).isTrue();
        assertThat(findPost.isEmpty()).isTrue();
    }

}