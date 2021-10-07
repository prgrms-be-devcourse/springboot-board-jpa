package com.devcourse.bbs;

import com.devcourse.bbs.domain.user.User;
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
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    PlatformTransactionManager transactionManager;

    @Test
    void userCreateTest() {
        User user = User.builder()
                .name("USER")
                .age(25)
                .hobby("spring")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        userRepository.save(user);

        User foundUser = entityManager.find(User.class, user.getId());
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    void userReadTest() {
        TransactionStatus transaction = transactionManager.getTransaction(TransactionDefinition.withDefaults());
        entityManager.persist(User.builder()
                .age(25)
                .hobby("hobby")
                .name("name")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build());
        transaction.flush();
        entityManager.clear();

        userRepository.findByName("name").ifPresentOrElse(
                user -> {
                    assertEquals("hobby", user.getHobby());
                    assertEquals("name", user.getName());
                    assertEquals(25, user.getAge());
                },
                () -> fail("User not found."));
    }

    @Test
    void userUpdateTest() {
        User user = User.builder()
                .name("name")
                .age(25)
                .hobby("hob")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        userRepository.save(user);
        user.updateName("UPDATED_NAME");
        user.updateAge(100);

        TransactionStatus transaction = transactionManager.getTransaction(TransactionDefinition.withDefaults());
        transaction.flush();

        User foundUser = entityManager.find(User.class, user.getId());
        assertEquals(foundUser, user);
        assertEquals("UPDATED_NAME", foundUser.getName());
        assertEquals(100, foundUser.getAge());
    }

    @Test
    void userDeleteTest() {
        User user = User.builder()
                .name("name")
                .age(25)
                .hobby("hob")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.clear();

        userRepository.deleteByName("name"); // executes SELECT query to get details and call remove by id.
        assertTrue(userRepository.findByName("name").isEmpty());
    }
}
