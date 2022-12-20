package com.prgrms.boardapp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@DataJpaTest
public class EntityManagerTest {
    @Autowired
    public EntityManagerFactory emf;
    public EntityManager entityManager;
    public EntityTransaction transaction;

    public void execWithTransaction(Runnable testCase) {
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        testCase.run();
        transaction.commit();  // entityManager.flush();
    }
}
