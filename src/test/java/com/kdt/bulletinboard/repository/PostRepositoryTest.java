package com.kdt.bulletinboard.repository;

import com.kdt.bulletinboard.entity.Hobby;
import com.kdt.bulletinboard.entity.Post;
import com.kdt.bulletinboard.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

@Transactional
@SpringBootTest
class PostPersistenceTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("양방향 저장 테스트")
    void testSave() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        User user = new User("user1", Hobby.CLIMBING, "user1");
        Post post1 = new Post("first test post", "this is first test post!!!", "user1");
        Post post2 = new Post("second test post", "this is second test post!!!", "user1");
        entityManager.persist(post1);
        entityManager.persist(post2);

        user.addPost(post1);
        user.addPost(post2);
        entityManager.persist(user);

        transaction.commit();
        entityManager.clear();

        User foundUser = entityManager.find(User.class, user.getId());
        Post foundPost1 = entityManager.find(Post.class, post1.getId());
        Post foundPost2 = entityManager.find(Post.class, post2.getId());

        transaction.begin();
        entityManager.remove(foundUser);
        entityManager.remove(foundPost1);
        entityManager.remove(foundPost2);
        transaction.commit();
    }

}
