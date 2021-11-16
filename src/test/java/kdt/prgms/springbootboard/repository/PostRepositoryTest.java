package kdt.prgms.springbootboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import kdt.prgms.springbootboard.global.config.JpaAuditingConfiguration;
import kdt.prgms.springbootboard.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


@Slf4j
@Import(JpaAuditingConfiguration.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("tester#1", 1);
        entityManager.persist(user);
    }

    @Test
    @DisplayName("BaseEntity 생성 테스트")
    void baseEntityAuditingTest() {
        //Given
        var now = LocalDateTime.now().minusMinutes(1);
        var post = new Post("testTitle#1", "testContent#1", user);
        entityManager.persist(post);

        //When
        var foundPost = postRepository.findById(post.getId()).get();

        //Then
        SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(foundPost.getCreatedDate()).isAfter(now);
                softAssertions.assertThat(foundPost.getLastModifiedDate()).isAfter(now);
            }
        );
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void savePostTest() {
        //given
        var postTitle = "testTitle#1";
        var postContent = "testContent#1";

        //when
        var newPost = postRepository.save(new Post(postTitle, postContent, user));

        //then
        log.info("created post: {}", newPost);
        assertThat(newPost.getId()).isNotNull();
    }


    @Test
    @DisplayName("게시글 제목으로 조회 테스트")
    void findPostsByTitleTest() {
        //given
        var post1 = new Post("testTitle#1", "testContent#1", user);
        entityManager.persist(post1);

        var post2 = new Post("testTitle#2", "testContent#2", user);
        entityManager.persist(post2);

        var post3 = new Post("test#3", "testContent#3", user);
        entityManager.persist(post3);

        //when
        var foundPosts = postRepository.findByTitleContaining("Title");

        //then
        assertThat(foundPosts).hasSize(2);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void changePostTest() {
        //given
        entityManager.persist(user);

        var post = new Post("testTitle#1", "testContent#1", user);
        entityManager.persist(post);

        //when
        post.changePost(
            "changed" + post.getTitle(),
            "changed" + post.getContent()
        );
        var foundPost = postRepository.findById(post.getId()).get();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(foundPost.getTitle()).isEqualTo(post.getTitle());
                softAssertions.assertThat(foundPost.getContent()).isEqualTo(post.getContent());
            }
        );
    }

    @Test
    @DisplayName("게시글 아이디로 soft delete 테스트")
    void softDeletePostByIdTest() {
        //given
        var post1 = new Post("testTitle#1", "testContent#1", user);
        entityManager.persist(post1);

        var post2 = new Post("testTitle#2", "testContent#2", user);
        entityManager.persist(post2);

        //when
        postRepository.deleteById(post1.getId());

        var allPosts = postRepository.findAll();

        //then
        assertThat(allPosts).hasSize(1);
    }
}
