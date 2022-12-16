package com.prgrms.be.app;


import com.prgrms.be.app.config.JpaConfig;
import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Import(value = {JpaConfig.class})
@DataJpaTest
public class PostRepositoryTest {


    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    EntityManager em;

    //todo : Post Create,read Test
    @Test
    void create_read_test() {

    }

    //todo : Post Update test
    @Test
    void update_test() {
        Post post = new Post("title", "content", null);

    }


}
