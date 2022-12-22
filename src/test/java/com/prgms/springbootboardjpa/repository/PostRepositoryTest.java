package com.prgms.springbootboardjpa.repository;

import com.prgms.springbootboardjpa.model.Member;
import com.prgms.springbootboardjpa.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void update_test() {

        Member member = new Member("name1", 11, "hobby");
        Post post = new Post("title1", "content1", member);

        postRepository.save(post);

        Post found = postRepository.findById(post.getPostId()).get();

        found.updateContent("updated_content");

        assertThat(found.getContent(), is("updated_content"));

    }
}