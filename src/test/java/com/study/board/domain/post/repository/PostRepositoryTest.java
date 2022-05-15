package com.study.board.domain.post.repository;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.repository.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static com.study.board.domain.post.domain.PostTest.assertPost;
import static com.study.board.domain.post.domain.PostTest.assertWriter;
import static com.study.board.fixture.Fixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    User writer;

    @BeforeEach
    void setUp() {
        writer = createUser();
        userRepository.save(writer);

        postRepository.save(Post.create("제목1", "내용1", writer));
        postRepository.save(Post.create("제목2", "내용2", writer));
    }

    @Test
    void 전체_조회_성공() {
        List<Post> posts = postRepository.findAll();

        assertThat(posts).hasSize(2);
        Post post1 = posts.get(0);
        Post post2 = posts.get(1);

        assertPost(post1, "제목1", "내용1");
        assertPost(post2, "제목2", "내용2");

        assertWriter(post1.getWriter(), writer.getId());
        assertWriter(post2.getWriter(), writer.getId());
    }

    @Test
    void 아이디로_조회_성공() {
        Long postId = postRepository.save(Post.create("제목", "내용", writer)).getId();
        //clear persistence context to see generated query
        em.flush();
        em.clear();

        Post post = postRepository.findById(postId).get();

        assertPost(post, "제목", "내용");
        assertWriter(post.getWriter(), writer.getId());
    }

    @Test
    void 아이디로_조회_없는아이디면_empty() {
        long illegalPostId = -1L;

        assertThat(postRepository.findById(illegalPostId)).isEmpty();
    }
}