package com.example.springbootboard.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
//import static org.hamcrest.Matchers.is;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("User와 Post의 양방향 관계를 확인한다")
    void 양방향관계_저장_조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 회원 엔티티
        User user = new User("허승연",26,"운동");

        entityManager.persist(user);

        // 게시글 엔티티
        Post post = new Post("안녕하세요","글입니다");

        entityManager.persist(post);

        // dirty checking
        user.addPost(post); // 연관관계 편의 메소드 사용

        transaction.commit();

        // 양방향 테스트
        log.info("user -> post 접근 가능 여부 : {}",user.getPosts().get(0).equals(post));
        log.info("post -> user 접근 가능 여부 : {}",post.getCreatedBy().equals(user));
        // 게시글 조회 -> 유저의 첫번째 게시글 조회
        Post findPost = entityManager.find(Post.class,post.getId());
        assertThat(post, samePropertyValuesAs(findPost));
        // 작성자 조회 -> 게시글의 작성자 조회
        User findUser = entityManager.find(User.class, findPost.getCreatedBy().getId());
        assertThat(user,samePropertyValuesAs(findUser));
    }
}
