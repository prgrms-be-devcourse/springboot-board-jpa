package com.example.jpaboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.user.domain.User;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired EntityManager em;

    @Test
    @DisplayName("유저에 포스트를 추가하는 경우 포스트의 FK에 잘 등록이 되는가")
    void test_add_post() {
        // given
        User user = new User("awesomeo", "coding");
        Post post = new Post("test title", "test content");
        post.setAuthor(user);

        em.persist(user);
        em.persist(post);


        // when
        Post findPost = em.find(Post.class, post.getId());
        User author = findPost.getAuthor();

        // then
        assertThat(author).isEqualTo(user);
    }

    @Test
    @DisplayName("유저를 삭제할 경우 해당 유저가 작성한 글이 잘 삭제 되는가")
    void test_delete_post_when_user_removed() {
        // given
        User user = new User("awesomeo", "coding");
        Post post = new Post("test title", "test content");
        post.setAuthor(user);
        em.persist(user);
        em.persist(post);

        // when
        em.remove(user);

        // then
        Post shouldBeRemoved = em.find(Post.class, post.getId());
        assertThat(shouldBeRemoved).isNull();
    }
}
