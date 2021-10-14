package com.example.board;

import com.example.board.member.Member;
import com.example.board.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@Slf4j
@SpringBootTest
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("member, order 관계 확인")
    void member_order_test(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();
        Member member = new Member("SangSun", 26, "Game");

        Post post = new Post("Test", "test content");
        post.setMember(member);
        em.persist(post);

        et.commit();

        em.clear();

        Member entity = em.find(Member.class, member.getId());
        assertThat(entity.getPosts().isEmpty(), is(false));
        assertThat(entity.getPosts().get(0).getId(), is(post.getId()));
    }
}
