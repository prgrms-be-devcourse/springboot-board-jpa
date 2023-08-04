package dev.jpaboard.user;

import dev.jpaboard.user.domain.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @ParameterizedTest
    @CsvSource(value = {"qkrdmswl1018@naver.com|Qwe12|박은지|21|공부", "qwer2345gmail.com|Dfg23456?|고범준|25|놀기", "oiue345345|2345|원건희|25|산책"}, delimiter = '|')
    void user_Create_Fail(String email, String password, String name, int age, String hobby) {
        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();

    }

}
