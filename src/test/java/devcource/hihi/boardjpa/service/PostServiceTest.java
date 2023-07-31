package devcource.hihi.boardjpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.repository.PostRepository;
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

    @InjectMocks
    @Autowired
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void clear() {
        postRepository.deleteAll();
    }

    @Test
    public void testGetBoardsWithNullId() {
        //given
        Long id = null;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Post> expectedBoards = new ArrayList<>();

        when(postRepository.findAllByOrderByIdDesc(pageable)).thenReturn(expectedBoards);

        List<Post> result = postService.getPosts(id, pageable);

        assertThat(result).isNotNull();

    }

    @Test
    public void testGetBoardsWithNonNullId() {
        //given
        Long id = 100L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Post> expectedBoards = new ArrayList<>();

        //when
        when(postRepository.findByIdLessThanOrderByIdDesc(id, pageable)).thenReturn(expectedBoards);
        List<Post> result = postService.getPosts(id, pageable);

        //then
        assertThat(result).isNotNull();

    }

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
        postService.saveDto(Post.toDto(post));

        //then
        Assertions.assertEquals(1,postRepository.findAll());

    }
}
