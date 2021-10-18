package kdt.prgrms.devrun.post.repository;

import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import kdt.prgrms.devrun.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Post post;

    @BeforeAll
    void setup() {

        User user = User.builder()
            .loginId("kjt3520")
            .loginPw("1234")
            .age(27)
            .name("김지훈")
            .email("devrunner21@gmail.com")
            .build();
        userRepository.save(user);

        final List<Post> postListForTest = IntStream.range(0, 10).mapToObj(i -> Post.builder().title("제목 " + i).content("내용").user(user).build()).collect(Collectors.toList());
        postRepository.saveAll(postListForTest);

        post = postRepository.save(Post.builder().title("제목 XX").content("내용 XX").user(user).build());
    }

    @Test
    @DisplayName("Post 페이징 리스트를 조회 할 수 있다.")
    void test_findPostPaging() {

        final PageRequest pageRequest = PageRequest.of(0, 2);
        final Page<Post> pagingPost = postRepository.findAll(pageRequest);

        assertThat(pagingPost.getTotalPages(), is(31));
        assertThat(pagingPost.getTotalElements(), is(31L));

    }

    @Test
    @DisplayName("PostId로 단건 조회 할 수 있다.")
    void test_findPostById() {

        final Post foundPost = postRepository.findById(post.getId()).get();

        assertThat(foundPost, is(notNullValue()));
        assertThat(foundPost.getId(), is(post.getId()));

    }

}
