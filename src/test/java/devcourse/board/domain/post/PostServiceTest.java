package devcourse.board.domain.post;

import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import devcourse.board.domain.post.model.Post;
import devcourse.board.domain.post.model.PostCreationRequest;
import devcourse.board.domain.post.model.PostUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member dummyMember;

    @BeforeEach
    void setUp() {
        this.dummyMember = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null
        );
    }

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
        // given
        Member member = this.dummyMember;
        memberRepository.save(member);

        PostCreationRequest creationRequest =
                new PostCreationRequest(member.getId(), "post-title", "post-content");

        // when
        Long createdPostId = postService.createPost(creationRequest);

        // then
        Post findPost = postRepository.findById(createdPostId).get();
        Member actualPostWriter = findPost.getMember();

        assertThat(actualPostWriter).isSameAs(member);
    }

    @Test
    @DisplayName("게시글을 작성자 외의 사용자가 수정 시 예외가 발생한다.")
    void should_throw_exception_when_unauthorized_member_modifies_post() {
        // given
        Member member = this.dummyMember;
        memberRepository.save(member);

        Post post = Post.createPost(member, "post-title", "post-content");
        postRepository.save(post);

        Long unAuthorizedMemberId = -1L;
        Long authorId = member.getId();
        PostUpdateRequest updateRequest = new PostUpdateRequest("title", "content");

        // when & then
        assertThrows(IllegalStateException.class, () -> {
            postService.updatePost(unAuthorizedMemberId, authorId, updateRequest);
        });
    }
}