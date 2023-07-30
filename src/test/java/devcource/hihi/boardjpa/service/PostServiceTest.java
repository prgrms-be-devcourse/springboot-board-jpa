package devcource.hihi.boardjpa.service;

import static org.mockito.Mockito.*;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBoardsWithNullId() {
        Long id = null;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Post> expectedBoards = new ArrayList<>();

        when(postRepository.findAllByOrderByIdDesc(pageable)).thenReturn(expectedBoards);

        List<Post> result = postService.getPosts(id, pageable);

    }

    @Test
    public void testGetBoardsWithNonNullId() {

        Long id = 123L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Post> expectedBoards = new ArrayList<>();

        when(postRepository.findByIdLessThanOrderByIdDesc(id, pageable)).thenReturn(expectedBoards);

        List<Post> result = postService.getPosts(id, pageable);

    }
}
