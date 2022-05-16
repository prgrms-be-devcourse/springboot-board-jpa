package com.study.board.domain.post.repository;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

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

        em.flush();
        em.clear();
    }

    @Test
    void 전체_조회_성공() {
        Page<Post> posts = postRepository.findAll(Pageable.ofSize(2));

        assertThat(posts.getTotalElements()).isEqualTo(2);
        Post post1 = posts.getContent().get(0);
        Post post2 = posts.getContent().get(1);

        assertPost(post1, "제목1", "내용1");
        assertWriter(post1.getWriter(), writer.getId());

        assertPost(post2, "제목2", "내용2");
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