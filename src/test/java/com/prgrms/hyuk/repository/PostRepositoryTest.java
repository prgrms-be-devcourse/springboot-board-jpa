package com.prgrms.hyuk.repository;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import com.prgrms.hyuk.domain.user.Age;
import com.prgrms.hyuk.domain.user.Hobby;
import com.prgrms.hyuk.domain.user.Name;
import com.prgrms.hyuk.domain.user.User;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {

    //쿼리 확인용
    private static final Logger log = LoggerFactory.getLogger(PostRepositoryTest.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager em;

    private User user;

    private Post post;

    @BeforeEach
    void setUp() {
        user = new User(
            new Name("eunhyuk"),
            new Age(26),
            Hobby.SOCCER);
        em.persist(user);

        post = new Post(
            new Title("hello world title...."),
            new Content("blah blah...")
        );
        post.assignUser(user);
    }

    @AfterEach
    void flush() {
        em.flush();
    }

    @Test
    @DisplayName("save() 쿼리 확인")
    void testSaveQuery() {
        postRepository.save(post);
    }
}
