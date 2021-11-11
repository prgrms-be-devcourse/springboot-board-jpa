package kdt.prgms.springbootboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import kdt.prgms.springbootboard.global.config.JpaAuditingConfiguration;
import kdt.prgms.springbootboard.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(JpaAuditingConfiguration.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("BaseEntity 생성 테스트")
    void baseEntityAuditingTest() {
        //Given
        var now = LocalDateTime.now().minusMinutes(1);
        var user = new User("tester#1", 1);
        entityManager.persist(user);

        //When
        var foundPost = userRepository.findById(user.getId()).get();

        //Then
        SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(foundPost.getCreatedDate()).isAfter(now);
                softAssertions.assertThat(foundPost.getLastModifiedDate()).isAfter(now);
            }
        );
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void saveUserTest() {
        //given
        var userName = "test";
        var userAge = 20;

        //when
        var newUser = userRepository.save(new User(userName, userAge));

        //then
        log.info("created user: {}", newUser);
        assertThat(newUser.getId()).isNotNull();
    }


    @Test
    @DisplayName("유저 이름으로 조회 테스트")
    void findUsersByNameTest() {
        //given
        var user1 = new User("tester#1", 1);
        entityManager.persist(user1);

        var user2 = new User("tester#2", 2);
        entityManager.persist(user2);

        //when
        var foundUser = userRepository.findByName(user2.getName()).get();

        //then
        assertThat(foundUser.getName()).isEqualTo(user2.getName());
    }

    @Test
    @DisplayName("유저 프로필 정보 변경 테스트")
    void changeUserProfileTest() {
        //given
        var user = new User("tester#1", 1);
        entityManager.persist(user);

        //when
        user.changeUserProfile(
            "changed" + user.getName(),
            user.getAge() + 1,
            new Hobby("soccer", HobbyType.SPORTS)
        );
        var foundUser = userRepository.findById(user.getId()).get();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(foundUser.getName()).isEqualTo(user.getName());
                softAssertions.assertThat(foundUser.getAge()).isEqualTo(user.getAge());
                softAssertions.assertThat(foundUser.getHobby()).isEqualTo(user.getHobby());
            }
        );

    }

    @Test
    @DisplayName("아이디로 유저 soft delete 테스트")
    void softDeleteUserByIdTest() {
        //given
        var user1 = new User("tester#1", 1);
        entityManager.persist(user1);

        var user2 = new User("tester#2", 2);
        entityManager.persist(user2);

        //when
        userRepository.deleteById(user2.getId());

        var allUsers = userRepository.findAll();

        //then
        assertThat(allUsers).hasSize(1);
    }
}
