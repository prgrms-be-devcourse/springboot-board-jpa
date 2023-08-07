package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.dto.post.PageCursorDto;
import devcource.hihi.boardjpa.repository.PostRepository;
import devcource.hihi.boardjpa.test.PostRepositoryTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostServicePagenationTest {

    private final int postSize = 10;
    private final Long cursor = 10L;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("커서 방식으로 페이지를 조회한다.")
    public void testGetPostsByCursor() {

        List<Post> samplePosts = PostRepositoryTestHelper.createSamplePosts(postSize);

        // Repository 메소드 호출 시 테스트용 데이터 반환하도록 설정
        when(postRepository.findByCursor(null, postSize + 1)).thenReturn(samplePosts.subList(0, 11));
        when(postRepository.findByCursor(cursor, postSize + 1)).thenReturn(samplePosts.subList(10, 15));

        // 첫 페이지 조회 테스트
        PageCursorDto<Post> firstPage = postService.getPostsByCursor(null, 10);
        assertThat(firstPage.getData()).hasSize(10);
        assertThat(firstPage.getPrevCursor()).isNull();
        assertThat(firstPage.getNextCursor()).isEqualTo("10");

        // 두 번째 페이지 조회 테스트
        PageCursorDto<Post> secondPage = postService.getPostsByCursor(10L, 10);
        assertThat(secondPage.getData()).hasSize(5);
        assertThat(secondPage.getPrevCursor()).isEqualTo("10");
        assertThat(secondPage.getNextCursor()).isNull();
    }
}
