package com.prgrms.be.app;


import com.prgrms.be.app.config.JpaConfig;
import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(value = {JpaConfig.class})
@DataJpaTest
public class PostRepositoryTest {


    @Autowired
    private PostRepository postRepository;


    @Test
    void test() {
        Post post = new Post("title", "content", null);

        postRepository.save(post);
    }

}
