package com.su.gesipan.user;

import com.su.gesipan.post.PostRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.List;
import static com.su.gesipan.helper.Helper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User makeUserAndSave() {
        return userRepository.save(User.of("su",24L,"게임"));
    }

    @Nested
    class 유저_저장 {
        @Test
        void 한명() {
            var user = makeUser();
            var savedUser = userRepository.save(user);
            assertThat(userRepository.findAll()).contains(savedUser);
        }

        @Test
        void 여러명() {
            List<User> users = new ArrayList<>();
            users.add(makeUser());
            users.add(makeUser());
            users.add(makeUser());
            users.add(makeUser());
            userRepository.saveAll(users);

            assertThat(userRepository.count()).isEqualTo(4);
        }
    }

    @Nested
    class 유저_조회 {
        @Test
        void 작성한_포스트들도_같이_조회할_수_있다() {
            var user = makeUserAndSave();

            var post1 = makePost();
            var post2 = makePost();
            var post3 = makePost();

            user.addPosts(post1, post2, post3);

            var findUser = userRepository.findById(user.getId()).get();
            var posts = findUser.getPosts();
            var post = posts.get(0);

            assertAll(
                    () -> assertThat(findUser).usingRecursiveComparison().isEqualTo(user),
                    () -> assertThat(posts).contains(post1, post2, post3)
            );
        }
    }

    @Nested
    class 유저_수정 {
        @Test
        void 취미를_변경할_수_있다() {
            var user = makeUserAndSave();
            var findUser = userRepository.findById(user.getId()).get();
            findUser.setHobby("유튜브보기");

            var editUser = userRepository.findById(user.getId()).get();
            assertThat(editUser.getHobby()).isEqualTo("유튜브보기");
        }

        @Test
        void 취미길이가_50자가_넘어가면_변경에_실패한다() {
            var user = makeUserAndSave();
            var findUser = userRepository.findById(user.getId()).get();
            var newHobby = "유튜브보기".repeat(100);
            assertThrows(IllegalArgumentException.class, () -> findUser.setHobby(newHobby));
        }
    }

    @Nested
    class 유저_삭제 {

        @Test
        void 유저가_삭제되면_포스트도_삭제된다_CASCADE() {
            var user = makeUserAndSave();
            var post = makePost();
            user.addPost(post);

            assertThat(postRepository.count()).isOne();
            assertThat(userRepository.count()).isOne();

            userRepository.deleteById(user.getId());

            assertAll(
                    () -> assertThat(userRepository.count()).isZero(),
                    () -> assertThat(postRepository.count()).isZero()
            );
        }
    }


}