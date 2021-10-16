package kdt.prgms.springbootboard.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import kdt.prgms.springbootboard.global.config.JpaAuditingConfiguration;
import kdt.prgms.springbootboard.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan.Filter;


@Slf4j
@DataJpaTest(includeFilters = @Filter(
    type = ASSIGNABLE_TYPE,
    classes = JpaAuditingConfiguration.class
))
class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PostRepository postRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.createUser("tester#1", 1);
    }


    @Test
    void 게시글이_없으면_빈_결과값이_정상() {
        //when
        var allPosts = postRepository.findAll();

        //then
        assertThat(allPosts, empty());
    }

    @Test
    void 게시글_생성_성공() {
        //given
        entityManager.persist(user);
        var postTitle = "testTitle#1";
        var postContent = "testContent#1";

        //when
        var newPost = postRepository.save(Post.createPost(postTitle, postContent, user));

        //then
        log.info("created post: {}", newPost);
        assertThat(newPost, hasProperty("id", notNullValue()));
        assertThat(newPost, hasProperty("title", is(postTitle)));
        assertThat(newPost, hasProperty("content", is(postContent)));
        assertThat(newPost, hasProperty("deleted", is(false)));
        assertThat(newPost, hasProperty("createdBy", notNullValue()));
        assertThat(newPost, hasProperty("createdDate", notNullValue()));
        assertThat(newPost, hasProperty("lastModifiedBy", notNullValue()));
        assertThat(newPost, hasProperty("lastModifiedDate", notNullValue()));
    }

    @Test
    void 전체_게시글_조회_성공() {
        //given
        entityManager.persist(user);

        var post1 = Post.createPost("testTitle#1", "testContent#1", user);
        entityManager.persist(post1);

        var post2 = Post.createPost("testTitle#2", "testContent#2", user);
        entityManager.persist(post2);

        //when
        var allPosts = postRepository.findAll();

        //then
        assertThat(allPosts, hasSize(2));
        assertThat(
            allPosts,
            containsInAnyOrder(
                samePropertyValuesAs(post1),
                samePropertyValuesAs(post2)
            )
        );
    }

    @Test
    void 게시글_아이디로_조회_성공() {
        //given
        entityManager.persist(user);

        var post1 = Post.createPost("testTitle#1", "testContent#1", user);
        entityManager.persist(post1);

        var post2 = Post.createPost("testTitle#2", "testContent#2", user);
        entityManager.persist(post2);

        //when
        var foundPost = postRepository.findById(post2.getId()).get();

        //then
        assertThat(foundPost, samePropertyValuesAs(post2));
    }

    @Test
    void 게시글_제목으로_조회_성공() {
        //given
        entityManager.persist(user);

        var post1 = Post.createPost("test#1", "testContent#1", user);
        entityManager.persist(post1);

        var post2 = Post.createPost("testTitle#2", "testContent#2", user);
        entityManager.persist(post2);

        var post3 = Post.createPost("testTitle#3", "testContent#3", user);
        entityManager.persist(post3);

        //when
        var posts = postRepository.findByTitleContaining("Title");

        //then
        assertThat(posts, hasSize(2));
    }

    @Test
    void 게시글_정보_변경_성공() {
        //given
        entityManager.persist(user);

        var post = Post.createPost("testTitle#1", "testContent#1", user);
        entityManager.persist(post);

        //when
        post.changePost(
            "changed" + post.getTitle(),
            "changed" + post.getContent()
        );
        var foundPost = postRepository.findById(post.getId()).get();

        //then
        assertThat(post, samePropertyValuesAs(foundPost));

    }

    @Test
    void 아이디로_게시글_삭제_성공() {
        //given
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        var post1 = Post.createPost("testTitle#1", "testContent#1", user);
        entityManager.persist(post1);

        var post2 = Post.createPost("testTitle#2", "testContent#2", user);
        entityManager.persist(post2);

        //when
        postRepository.deleteById(post1.getId());
        var allPosts = postRepository.findAll();

        //then
        assertThat(allPosts, hasSize(1));
        assertThat(allPosts, containsInAnyOrder(samePropertyValuesAs(post2)));

    }

}