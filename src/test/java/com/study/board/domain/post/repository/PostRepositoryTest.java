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

import static com.study.board.domain.post.AssertPost.assertPostWithWriter;
import static com.study.board.fixture.Fixture.sampleUser;
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
    Long writtenPostId1;
    Long writtenPostId2;

    @BeforeEach
    void setUp() {
        writer = sampleUser();
        userRepository.save(writer);

        writtenPostId1 = postRepository.save(new Post("제목1", "내용1", writer)).getId();
        writtenPostId2 = postRepository.save(new Post("제목2", "내용2", writer)).getId();

        em.flush();
        em.clear();
    }

    @Test
    void 전체_조회_성공() {
        Page<Post> posts = postRepository.findAll(Pageable.ofSize(2));

        assertThat(posts.getTotalElements()).isEqualTo(2);
        assertPostWithWriter(posts.getContent().get(0), "제목1", "내용1", writer.getId());
        assertPostWithWriter(posts.getContent().get(1), "제목2", "내용2", writer.getId());
    }

    @Test
    void 아이디로_조회_성공() {
        Post post = postRepository.findById(writtenPostId2).get();

        assertPostWithWriter(post, "제목2", "내용2", writer.getId());
    }

    @Test
    void 아이디로_조회_없는아이디면_empty() {
        long illegalPostId = -1L;

        assertThat(postRepository.findById(illegalPostId)).isEmpty();
    }
}