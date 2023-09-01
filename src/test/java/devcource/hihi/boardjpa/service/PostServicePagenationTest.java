package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.CursorResult;
import devcource.hihi.boardjpa.test.PostRepositoryTestHelper;
import devcource.hihi.boardjpa.test.UserRepositoryTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PostServicePagenationTest {

    private static final Pageable page = PageRequest.of(0, 10);

    @Autowired
    private PostService postService;

    @Test
    void testGetPostsWithPagenation() {
        CursorResult<Post> firstPage = this.postService.get(null, page);
        Long cursorId = firstPage.getValues().get(9).getId();
        assertThat(firstPage.getValues().size()).isEqualTo(10);
        assertTrue(firstPage.getHasNext());

        CursorResult<Post> secondPage = this.postService.get(cursorId, page);
        Long nextCursorId = secondPage.getValues().get(9).getId();
        assertThat(secondPage.getValues().size()).isEqualTo(10);
        assertTrue(secondPage.getHasNext());

        CursorResult<Post> thirdPage = this.postService.get(nextCursorId, page);
        assertFalse(thirdPage.getHasNext());
    }

    @Test
    void testGetPosts() {
        final CursorResult<Post> result = this.postService.get(0L, page);
        assertThat(result.getValues()).isEmpty();
        assertFalse(result.getHasNext());
    }

}