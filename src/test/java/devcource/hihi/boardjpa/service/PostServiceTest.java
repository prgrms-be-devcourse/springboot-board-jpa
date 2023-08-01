package devcource.hihi.boardjpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostServiceTest {


    @Mock
    @Autowired
    private PostRepository postRepository;

    @InjectMocks
    @Autowired
    private PostService postService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        for (int i = 1; i <= 10; i++) {
            Post post = Post.builder()
                    .title("Title " + i )
                    .content("Content " + i)
                    .build();
            postRepository.save(post);
        }
    }



    @Test
    public void testGetPostsFirstPage() {
        // Given
        int limit = 5;
        List<Post> all = postRepository.findAll();
        log.info("posts.size():{}", all.size());
        when(postRepository.findTopByOrderByIdDesc(limit)).thenReturn(all);

        // When
        List<Post> result = postService.getPosts(null, limit);
        log.info("result.size() : {}",result.size());

        for (int i = 0; i < limit; i++) {
            log.info("result : {}",result.get(i));
            log.info("post : {}", all.get(i));
        }
        // Then
        assertEquals(limit, result.size());

    }

    @Test
    public void testGetPostsNextPage() {
        // Given
        int limit = 5;
        Long cursor = 10L;

        List<Post> all = postRepository.findAll();

        when(postRepository.findByIdLessThanOrderByIdDesc(cursor, limit)).thenReturn(all);

        // When
        List<Post> result = postService.getPosts(cursor, limit);


        // Then
        assertEquals(limit, result.size());
    }

}


