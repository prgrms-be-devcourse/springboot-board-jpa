package com.prgrms.hyuk.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import com.prgrms.hyuk.domain.user.Age;
import com.prgrms.hyuk.domain.user.Hobby;
import com.prgrms.hyuk.domain.user.Name;
import com.prgrms.hyuk.domain.user.User;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class MappingTest {

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
        em.persist(post);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("User, Post 연관관계 매핑")
    void testUserAndPostMapping() {
        //given
        //when
        var postEntity = em.find(Post.class, this.post.getId());
        var userEntity = em.find(User.class, this.user.getId());

        //then
        assertAll(
            () -> assertThat(postEntity.getUser()).isSameAs(userEntity),
            () -> assertThat(userEntity.getPosts().getAllPost().get(0)).isSameAs(postEntity)
        );
    }
}
