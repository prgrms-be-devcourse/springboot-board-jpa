package com.study.board.domain.post.service;

import com.study.board.domain.exception.PostEditAccessDeniedException;
import com.study.board.domain.exception.PostNotFoundException;
import com.study.board.domain.exception.UserNotFoundException;
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
    Long writtenPostId1;
    Long writtenPostId2;

    @BeforeEach
    void setUp() {
        writer = createUser();
        userRepository.save(writer);

        writtenPostId1 = postRepository.save(Post.create("제목1", "내용1", writer)).getId();
        writtenPostId2 = postRepository.save(Post.create("제목2", "내용2", writer)).getId();
    }

    @Test
    void 전체_게시글_조회_성공() {
        List<Post> posts = postService.findAll();

        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getId).containsExactly(writtenPostId1, writtenPostId2);
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
    void 아이디로_게시글_조회_성공() {
        Long postId = postRepository.save(Post.create("제목", "내용", writer)).getId();
        Post post = postService.findById(postId);

        assertPost(post, "제목", "내용");
        assertWriter(post.getWriter());
    }


    @Test
    void 없는_아이디의_게시글_조회하면_실패() {
        Long illegalId = -1L;

        assertThatThrownBy(() -> postService.findById(illegalId))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void 게시글_작성_성공() {
        Post writtenPost = postService.write("제목", "내용", writer.getId());

        assertPost(writtenPost, "제목", "내용");
        assertWriter(writtenPost.getWriter());

        Post foundPost = postRepository.findById(writtenPost.getId()).get();
        assertPost(foundPost, "제목", "내용");
        assertWriter(foundPost.getWriter());
    }

    @Test
    void 없는_사용자_아이디로_게시글_작성하면_실패() {
        Long illegalUserId = -1L;

        assertThatThrownBy(() -> postService.write("제목", "내용", illegalUserId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void 게시글_수정_성공() {
        Post editedPost = postService.edit(writtenPostId1, "수정제목", "수정내용", writer.getId());

        assertThat(editedPost.getId()).isEqualTo(writtenPostId1);
        assertPost(editedPost, "수정제목", "수정내용");
        assertWriter(editedPost.getWriter());

        Post foundPostAfterEdit = postRepository.findById(writtenPostId1).get();
        assertPost(foundPostAfterEdit, "수정제목", "수정내용");
        assertWriter(foundPostAfterEdit.getWriter());
    }

    @Test
    void 없는_게시글_수정_하면_실패() {
        Long illegalPostId = -1L;

        assertThatThrownBy(() -> postService.edit(illegalPostId, "수정제목", "수정내용", writer.getId()))
                .isInstanceOf(PostNotFoundException.class);
    }

    @Test
    void 없는_사용자_아이디로_게시글_수정하면_실패() {
        Long illegalUserId = -1L;

        assertThatThrownBy(() -> postService.edit(writtenPostId1, "수정제목", "수정내용", illegalUserId))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void 남의_게시글_수정하려하면_실패() {
        Long anotherUserId = userRepository.save(User.create("다른 사용자", null)).getId();

        assertThatThrownBy(() -> postService.edit(writtenPostId1, "수정제목", "수정내용", anotherUserId))
                .isInstanceOf(PostEditAccessDeniedException.class);
    }

    void assertPost(Post post, String title, String content) {
        assertThat(post.getId()).isNotNull();
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getWrittenDateTime()).isNotNull();
    }

    void assertWriter(User writer) {
        assertThat(writer.getId()).isEqualTo(this.writer.getId());
        assertThat(writer.getName()).isEqualTo("득윤");
        assertThat(writer.getHobby()).isEqualTo("체스");
    }
}