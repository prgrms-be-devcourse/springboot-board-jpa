package com.maenguin.kdtbbs.controller;

import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@Profile("local")
public class InitDB {

    private final InitDBService initDBService;

    public InitDB(InitDBService initDBService) {
        this.initDBService = initDBService;
    }

    @PostConstruct
    public void init() {
        initDBService.init();
    }

    @Service
    @Transactional
    static class InitDBService {

        private final EntityManager entityManager;

        InitDBService(EntityManager entityManager) {
            this.entityManager = entityManager;
        }

        public void init() {
            User user = new User("maeng", "piano");
            Post post = new Post("hello", "world");
            Post post2 = new Post("Hi", "world");
            user.addPost(post);
            user.addPost(post2);
            entityManager.persist(user);
        }
    }
}
