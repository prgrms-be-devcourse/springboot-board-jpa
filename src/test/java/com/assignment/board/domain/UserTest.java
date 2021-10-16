package com.assignment.board.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserTest {


    @Autowired
    EntityManagerFactory emf;

    @Test
    public void 유저테스터(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();


        User user = new User("chaewon", 11, "swim");
        em.persist(user);

        tx.commit();
        log.info("{}",user.getName());
    }

}