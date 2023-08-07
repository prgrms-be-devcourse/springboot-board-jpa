package prgrms.board.post.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import prgrms.board.post.application.dto.request.PostSaveRequest;
import prgrms.board.post.application.dto.request.PostUpdateRequest;
import prgrms.board.post.application.dto.response.PostFindResponse;
import prgrms.board.post.application.dto.response.PostSaveResponse;
import prgrms.board.user.domain.User;
import prgrms.board.user.domain.UserRepository;
import prgrms.board.user.exception.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private PostSaveRequest postSaveRequest;
    private String title = "게시판 테스트";
    private String content = "게시판임!!!";

    @BeforeEach
    void setUp() {
        user = new User("고예성", 28);
        userRepository.save(user);
        Long userId = user.getId();

        postSaveRequest = new PostSaveRequest(
                userId, title, content
        );
    }

    @Test
    @DisplayName("새 Post 생성 성공 테스트")
    void saveNewPostTest() {
        // when
        var saveResponse = postService.savePost(postSaveRequest);
        Long postId = saveResponse.postId();

        // then
        assertThat(postService.findByPostId(postId))
                .isInstanceOf(PostFindResponse.class);
    }

    @Test
    @DisplayName("새 Post 생성 실패 테스트")
    void savePostFailTest() {
        // given
        var request = new PostSaveRequest(
                500L,
                "이거 저장안됨ㅎ",
                "제곧내 저장 안됨~"
        );

        // when, then
        assertThatThrownBy(() -> postService.savePost(request))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("게시물 ID를 통한 조회 성공")
    void findByPostIdTest() {
        // given
        PostSaveResponse response = postService.savePost(postSaveRequest);
        Long postId = response.postId();

        // when
        PostFindResponse foundResponse = postService.findByPostId(postId);
        String responseTitle = foundResponse.title();
        String responseContent = foundResponse.content();

        // then
        assertThat(responseTitle).isEqualTo(title);
        assertThat(responseContent).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물 전체 조회 성공")
    void findAllPostTest() {
        // given
        for (int i = 0; i < 3; i++) {
            postService.savePost(postSaveRequest);
        }
        Pageable unpaged = Pageable.unpaged();

        // when
        Page<PostFindResponse> all = postService.findAll(unpaged);
        List<PostFindResponse> postFoundList = all.getContent();
        int listSize = postFoundList.size();
        String postTitle = postFoundList.get(1).title();
        String postContent = postFoundList.get(1).content();

        // then
        assertThat(listSize).isEqualTo(3);
        assertThat(postTitle).isEqualTo(title);
        assertThat(postContent).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물 업데이트 성공")
    void updatePostTest() {
        // given
        var saveResponse = postService.savePost(postSaveRequest);
        Long postId = saveResponse.postId();
        String titleForUpdate = "업데이트된 제목임~";
        String contentForUpdate = "업데이트 성공이라구~~";
        PostUpdateRequest updateRequest = new PostUpdateRequest(
                titleForUpdate, contentForUpdate
        );

        // when
        var updateResponse = postService.updatePost(postId, updateRequest);
        Long responseId = updateResponse.postId();
        String responseTitle = updateResponse.title();
        String responseContent = updateResponse.content();

        // then
        assertThat(responseId).isEqualTo(postId);
        assertThat(responseTitle).isEqualTo(titleForUpdate);
        assertThat(responseContent).isEqualTo(contentForUpdate);
    }
}
