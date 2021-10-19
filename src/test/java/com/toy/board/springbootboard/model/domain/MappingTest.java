package com.toy.board.springbootboard.model.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("Post와 User관계 확인")
    void PostUserTest(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        User user = new User("Minkyu",26,"programming");

        Post post = new Post("JAVA","java is good", user);
        em.persist(post);

        transaction.commit();
        em.clear();;

        User findUser = em.find(User.class,user.getId());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        log.info("DBuser : {} , user :{}",findUser,user);
    }
}
