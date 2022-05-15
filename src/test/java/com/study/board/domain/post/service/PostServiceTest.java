package com.study.board.domain.post.service;

import com.study.board.domain.exception.BoardRuntimeException;
import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.domain.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.study.board.fixture.Fixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User writer;

    @BeforeEach
    void setUp() {
        writer = createUser();
        userRepository.save(writer);

        postRepository.save(Post.create("제목1", "내용1", writer));
        postRepository.save(Post.create("제목2", "내용2", writer));
    }

    @Test
    void 전체조회_성공() {
        List<Post> posts = postService.findAll();

        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getId).doesNotContainNull();
        assertThat(posts).extracting(Post::getTitle).containsExactly("제목1", "제목2");
        assertThat(posts).extracting(Post::getContent).containsExactly("내용1", "내용2");
        assertThat(posts).extracting(Post::getWrittenDateTime).doesNotContainNull();

        Long writerId1 = posts.get(0).getWriter().getId();
        Long writerId2 = posts.get(0).getWriter().getId();
        assertThat(writerId1).isNotNull();
        assertThat(writerId1).isEqualTo(writerId2);
        assertThat(posts).extracting(Post::getWriter).extracting(User::getName).containsExactly("득윤", "득윤");
        assertThat(posts).extracting(Post::getWriter).extracting(User::getHobby).containsExactly("체스", "체스");
    }

    @Test
    void 아이디로조회_성공() {
        Long postId = postRepository.save(Post.create("제목3", "내용3", writer)).getId();

        Post post = postService.findById(postId);

        assertThat(post.getId()).isNotNull();
        assertThat(post.getTitle()).isEqualTo("제목3");
        assertThat(post.getContent()).isEqualTo("내용3");
        assertThat(post.getWrittenDateTime()).isNotNull();

        User foundWriter = post.getWriter();
        assertThat(foundWriter.getId()).isEqualTo(writer.getId());
        assertThat(foundWriter.getName()).isEqualTo("득윤");
        assertThat(foundWriter.getHobby()).isEqualTo("체스");
    }

    @Test
    void 없는_아이디로_조회하면_예외() {
        Long illegalId = -1L;

        assertThatThrownBy(() -> postService.findById(illegalId))
                .isInstanceOf(BoardRuntimeException.class);
    }
}