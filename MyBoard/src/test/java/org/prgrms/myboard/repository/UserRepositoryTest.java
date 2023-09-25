package org.prgrms.myboard.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    User user;

    @BeforeAll
    void setup() {
        user = User.builder()
            .name("hi")
            .age(23)
            .build();
        userRepository.save(user);
        for(int i = 0; i < 5; i++) {
            Post post = new Post("test", "tset", user);
            postRepository.save(post);
        }
    }

    @DisplayName("sample test")
    @Test
    void testMethodNameHere() {
        // given

        User usertwo = userRepository.findById(user.getId()).get();

        // when
        List<Post> userPost = usertwo.getPosts();
        for (int i = 0; i < userPost.size(); i++) {
            System.out.println("usertwo post : " + userPost.get(i).getTitle());
        }

        // then

    }

}