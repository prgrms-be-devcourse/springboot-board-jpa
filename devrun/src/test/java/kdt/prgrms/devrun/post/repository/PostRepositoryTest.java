package kdt.prgrms.devrun.post.repository;

import kdt.prgrms.devrun.domain.Post;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeAll
    void setup() {

        final Post post1 = Post.builder()
            .title("title1")
            .content("content1")
            .build();
        postRepository.save(post1);

        final Post post2 = Post.builder()
            .title("title2")
            .content("content2")
            .build();
        postRepository.save(post2);

        final Post post3 = Post.builder()
            .title("title3")
            .content("content3")
            .build();
        postRepository.save(post3);

        final Post post4 = Post.builder()
            .title("title4")
            .content("content4")
            .build();
        postRepository.save(post4);

    }

    @Test
    @DisplayName("Post 페이징 리스트를 조회 할 수 있다.")
    void findPostPagingTest() {

        final PageRequest pageRequest = PageRequest.of(0, 2);
        final Page<Post> pagingPost = postRepository.findAll(pageRequest);

        assertThat(pagingPost.getTotalPages(), is(2));
        assertThat(pagingPost.getTotalElements(), is(4L));

    }

    @Test
    @DisplayName("PostId로 단건 조회 할 수 있다.")
    void findPostByIdTest() {

        final Post foundPost = postRepository.findById(1L).get();

        assertThat(foundPost, is(notNullValue()));
        assertThat(foundPost.getId(), is(1L));

    }

}
