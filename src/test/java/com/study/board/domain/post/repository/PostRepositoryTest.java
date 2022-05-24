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
    Post post1;
    Post post2;

    @BeforeEach
    void setUp() {
        writer = createUser();
        userRepository.save(writer);

        post1 = postRepository.save(new Post("제목1", "내용1", writer));
        post2 = postRepository.save(new Post("제목2", "내용2", writer));

        em.flush();
        em.clear();
    }

    @Test
    void 전체_조회_성공() {
        Page<Post> posts = postRepository.findAll(Pageable.ofSize(2));

        assertThat(posts.getContent()).containsExactly(post1, post2);
        assertThat(posts.getContent().stream().map(Post::getWriter)).containsExactly(writer, writer);
    }

    @Test
    void 아이디로_조회_성공() {
        Post post = postRepository.findById(post1.getId()).get();

        assertThat(post).isEqualTo(post1);
        assertThat(post.getWriter()).isEqualTo(writer);
    }

    @Test
    void 아이디로_조회_없는아이디면_empty() {
        long illegalPostId = -1L;

        assertThat(postRepository.findById(illegalPostId)).isEmpty();
    }
}