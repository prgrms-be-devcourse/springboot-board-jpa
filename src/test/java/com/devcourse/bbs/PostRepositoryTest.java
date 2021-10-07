package com.devcourse.bbs;

import com.devcourse.bbs.domain.post.Post;
import com.devcourse.bbs.domain.user.User;
import com.devcourse.bbs.repository.post.PostRepository;
import com.devcourse.bbs.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PlatformTransactionManager transactionManager;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void postCreateTest() {
        User user = User.builder()
                .name("USER1")
                .age(25)
                .hobby("Spring")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("TITLE")
                .content("CONTENT")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        postRepository.save(post);

        assertNotNull(post.getId());
    }

    @Test
    void postReadTest() {
//        EntityTransaction transaction = entityManager.getTransaction();
        TransactionStatus transaction = transactionManager.getTransaction(TransactionDefinition.withDefaults());
//        transaction.begin();
        User user = User.builder()
                .name("USER1")
                .age(25)
                .hobby("Spring")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        entityManager.persist(user);

        Post post = Post.builder()
                .user(user)
                .title("TITLE")
                .content("CONTENT")
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        entityManager.persist(post);
        transaction.flush();
        entityManager.clear();

        postRepository.findById(post.getId()).ifPresentOrElse(
                foundPost -> assertEquals(post, foundPost),
                () -> fail("Post not exists."));
        assertTrue(postRepository.findAllByUser(user).stream().anyMatch(post::equals));
        assertEquals(user, post.getUser());
    }

    @Test
    void postUpdateTest() {
        TransactionStatus transaction = transactionManager.getTransaction(TransactionDefinition.withDefaults());
        User user = User.builder()
                .name("USER1")
                .age(25)
                .hobby("Spring")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        entityManager.persist(user);

        Post post = Post.builder()
                .user(user)
                .title("TITLE")
                .content("CONTENT")
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        entityManager.persist(post);
        post.changeTitle("UPDATED_TITLE");
        post.changeContent("UPDATED_CONTENT");
        transaction.flush();
        entityManager.clear();

        postRepository.findById(post.getId()).ifPresentOrElse(
                foundPost -> {
                    assertEquals("UPDATED_TITLE", foundPost.getTitle());
                    assertEquals("UPDATED_CONTENT", foundPost.getContent());
                },
                () -> fail("Post not found."));
    }

    @Test
    void postDeleteTest() {
        TransactionStatus transaction = transactionManager.getTransaction(TransactionDefinition.withDefaults());
        User user = User.builder()
                .name("USER1")
                .age(25)
                .hobby("Spring")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        entityManager.persist(user);

        Post post = Post.builder()
                .user(user)
                .title("TITLE")
                .content("CONTENT")
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        entityManager.persist(post);
        transaction.flush();
        entityManager.clear();

        postRepository.deleteById(post.getId());
        assertTrue(postRepository.findById(post.getId()).isEmpty());
    }
}
