package kdt.cse0518.board.user.repository;

import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserFactory factory;

    @Test
    @DisplayName("User가 DB에 저장될 수 있다.")
    @Transactional
    void testSaveUser() {
        factory.createUser("최승은", 26, "weight training");
        final User userEntity = repository.findAll().get(0);

        assertThat(userEntity.getName(), is("최승은"));
        assertThat(userEntity.getAge(), is(26));
        assertThat(userEntity.getHobby(), is("weight training"));
    }

    @Test
    @DisplayName("User를 전체 조회할 수 있다.")
    @Transactional
    void testFindAll() {
        // Given
        factory.createUser("사람1", 26, "취미1");
        factory.createUser("사람2", 30, "취미2");

        // When
        final List<User> allUsers = repository.findAll();

        // Then
        assertThat(allUsers.size(), is(2));
        assertThat(allUsers.get(0).getName(), is("사람1"));
        assertThat(allUsers.get(1).getName(), is("사람2"));
    }
}