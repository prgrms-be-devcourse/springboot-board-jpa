package dev.jpaboard.user.repository;

import dev.jpaboard.user.domain.Email;
import dev.jpaboard.user.domain.Password;
import dev.jpaboard.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        User user = User.builder()
                .email("qkrdmswl1018@naner.com")
                .password("Qwqer123?")
                .name("박은지")
                .age(23)
                .hobby("산책")
                .build();
        userRepository.save(user);

        User findUser = userRepository.findByEmailAndPassword(new Email("qkrdmswl1018@naner.com"), new Password("Qwqer123?")).get();
        System.out.println(String.format("Email: %s, Password: %s", findUser.getEmail(), findUser.getPassword()));
    }

}