package devcource.hihi.boardjpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.repository.PostRepository;
import devcource.hihi.boardjpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest
public class PostServiceTest {


    @Mock
    @Autowired
    private PostRepository postRepository;

    private List<Post> posts = new ArrayList<>();
    @InjectMocks
    @Autowired
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        for (int i = 1; i <= 5; i++) {
            Post post = Post.builder()
                    .title("Title " )
                    .content("Content ")
                    .build();
            posts.add(post);
        }
    }

//    @AfterEach
//    public void clear() {
//        postRepository.deleteAll();
//    }
    @Test
    public void SaveTest() {
        //given
        User user = User.builder()
                .name("yejin")
                .age(25)
                .hobby("walking")
                .build();

        Post post = Post.builder()
                .title("test")
                .content("content")
                .build();

        post.allocateUser(user);
        //when
        postService.createDto(Post.toCreateDto(post));
//        postRepository.save(post);

        //then
        assertEquals(1,postRepository.findAll().size());

    }


    @Test
    public void testGetPostsFirstPage() {
        // Given
        int limit = 5;
        log.info("posts.size():{}", posts.size());
        when(postRepository.findTopByOrderByIdDesc(limit)).thenReturn(posts);

        // When
        List<Post> result = postService.getPosts(null, limit);
        log.info("{}",result.size());
        // Then
        assertEquals(limit, result.size());
        for (int i = 0; i < limit; i++) {
            log.info("posts:{} ",posts.get(i));
            log.info("results:{}", result.get(i));
        }
//        verify(postRepository, times(1)).findTopByOrderByIdDesc(limit);
//        verifyNoMoreInteractions(postRepository);
    }

    @Test
    public void testGetPostsNextPage() {
        // Given
        int limit = 5;
        Long cursor = 10L;
        List<Post> posts = createMockPosts(limit);
        when(postRepository.findByIdLessThanOrderByIdDesc(cursor, limit)).thenReturn(posts);

        // When
        List<Post> result = postService.getPosts(cursor, limit);

        // Then
        assertEquals(limit, result.size());
        for (int i = 0; i < limit; i++) {
            assertEquals(posts.get(i), result.get(i));
        }
        verify(postRepository, times(1)).findByIdLessThanOrderByIdDesc(cursor, limit);
        verifyNoMoreInteractions(postRepository);
    }

}


