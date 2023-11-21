package kdt.jpa.board.post.fixture;

import kdt.jpa.board.post.domain.Post;
import kdt.jpa.board.user.fixture.UserFixture;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostFixture {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    public static Post getPost() {
        return new Post(TITLE, CONTENT, UserFixture.getUser());
    }

    public static Post getPost(Long id) {
        Post post = getPost();
        ReflectionTestUtils.setField(post, "id", id);
        return post;
    }
}
