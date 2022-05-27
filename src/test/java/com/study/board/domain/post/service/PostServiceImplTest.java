package com.study.board.domain.post.service;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.repository.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static com.study.board.fixture.Fixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class PostServiceImplTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    Post writtenPost1;
    Post writtenPost2;

    @BeforeEach
    void setUp() {
        User writer = createUser();
        userRepository.save(writer);

        writtenPost1 = postRepository.save(new Post("제목1", "내용1", writer));
        writtenPost2 = postRepository.save(new Post("제목2", "내용2", writer));
    }

    @Test
    void 전체_게시글_전체_조회_성공() {
        Page<Post> posts = postService.findAll(Pageable.ofSize(2));

        Post post1 = posts.getContent().get(0);
        Post post2 = posts.getContent().get(1);

        assertThat(posts.getTotalElements()).isEqualTo(2);

        assertThat(post1).isEqualTo(writtenPost1);
        assertThat(post2).isEqualTo(writtenPost2);
    }

    @Test
    void 아이디로_게시글_조회_성공() {
        Post post = postService.findById(writtenPost1.getId());

        assertThat(post).isEqualTo(writtenPost1);
    }


    @Test
    void 없는_아이디의_게시글_조회하면_실패() {
        Long illegalId = -1L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> postService.findById(illegalId));
    }

    @Test
    void 게시글_작성_성공() {
        Post writtenPost = postService.write("제목", "내용", "득윤");

        Post foundPost = postRepository.findById(writtenPost.getId()).get();

        assertThat(foundPost).isEqualTo(writtenPost);
        assertThat(foundPost.getTitle()).isEqualTo("제목");
        assertThat(foundPost.getContent()).isEqualTo("내용");
    }

    @Test
    void 게시글_수정_성공() {
        Long editPostId = writtenPost1.getId();

        Post editedPost = postService.edit(editPostId, "수정제목", "수정내용", "득윤");

        assertThat(editedPost).isEqualTo(writtenPost1);
        assertThat(editedPost.getTitle()).isEqualTo("수정제목");
        assertThat(editedPost.getContent()).isEqualTo("수정내용");
    }

    @Test
    void 없는_게시글_수정_실패() {
        Long illegalPostId = -1L;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> postService.edit(illegalPostId, "수정제목", "수정내용", "득윤"));
    }

    @Test
    void 남의_게시글_수정_실패() {
        User anotherUser = userRepository.save(new User("다른 사용자", null));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> postService.edit(writtenPost1.getId(), "수정제목", "수정내용", "다른 사용자"));
    }
}