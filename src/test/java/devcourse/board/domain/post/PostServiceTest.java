package devcourse.board.domain.post;

import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.MemberJoinRequest;
import devcourse.board.domain.post.model.MultiplePostResponse;
import devcourse.board.domain.post.model.PostCreationRequest;
import devcourse.board.domain.post.model.PostResponse;
import devcourse.board.domain.post.model.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
        // given
        String postWriter = "post-writer";
        Long savedMemberId = saveMember(postWriter);
        PostCreationRequest creationRequest =
                new PostCreationRequest(savedMemberId, "post-title", "post-content");

        // when
        Long savedPostId = postService.createPost(creationRequest);

        // then
        PostResponse findPost = postService.findOne(savedPostId);
        String actualPostWriter = findPost.createdBy();

        assertThat(actualPostWriter).as("게시글 작성자 이름이 같아야 한다.")
                .isEqualTo(postWriter);
    }

    @Test
    @DisplayName("잘못된 게시글 ID로 게시글 조회 시 예외가 발생한다.")
    void should_throw_exception_when_finding_post_for_invalid_postId() {
        // given
        Long savedMemberId = saveMember("post-writer");
        PostCreationRequest creationRequest =
                new PostCreationRequest(savedMemberId, "post-title", "post-content");
        Long savedPostId = postService.createPost(creationRequest);

        // when & then
        Long invalidPostId = -1L;

        assertThrows(EntityNotFoundException.class, () -> {
            postService.findOne(invalidPostId);
        });
    }

    @Test
    @DisplayName("게시글 페이징 조회")
    void findWithPaging() {
        // given
        createDummyPosts();

        // when
        MultiplePostResponse multiplePostResponse = postService.findWithPaging(0, 50);

        // then
        int actualResultCount = multiplePostResponse.postResponses().size();
        int expectedResultCount = 50;

        assertThat(actualResultCount).isEqualTo(expectedResultCount);
    }

    @Test
    @DisplayName("게시글 업데이트")
    void updatePost() {
        // given
        Long savedMemberId = saveMember("writer");
        PostCreationRequest creationRequest =
                new PostCreationRequest(savedMemberId, "old-title", "old-content");
        Long createdPostId = postService.createPost(creationRequest);

        PostUpdateRequest updateRequest =
                new PostUpdateRequest("new-title", "new-content");

        // when
        postService.updatePost(createdPostId, updateRequest);

        // then
        PostResponse responseDto = postService.findOne(createdPostId);
        String actualUpdatedTitle = responseDto.title();
        String expectedUpdatedTitle = "new-title";

        assertThat(actualUpdatedTitle).isEqualTo(expectedUpdatedTitle);
    }

    private Long saveMember(String name) {
        return memberService.join(new MemberJoinRequest(name));
    }

    private void createDummyPosts() {
        for (int i = 0; i < 100; i++) {
            Long savedMemberId = saveMember("writer" + i);
            PostCreationRequest creationRequest
                    = new PostCreationRequest(savedMemberId, "title" + i, "content" + i);

            postService.createPost(creationRequest);
        }
    }
}