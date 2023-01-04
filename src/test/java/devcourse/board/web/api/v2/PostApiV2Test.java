package devcourse.board.web.api.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import devcourse.board.domain.post.PostRepository;
import devcourse.board.domain.post.model.Post;
import devcourse.board.domain.post.model.PostCreationRequest;
import devcourse.board.domain.post.model.PostUpdateRequest;
import devcourse.board.web.authentication.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostApiV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private Member dummyMember;

    @BeforeEach
    void setUp() {
        this.dummyMember = Member.create("example@email.com", "0000", "member");
    }

    @Test
    @DisplayName("게시글 생성 성공 - 로그인한 회원만 게시글을 작성할 수 있다.")
    void createPost() throws Exception {
        // given
        Member member = this.dummyMember;
        memberRepository.save(member);

        PostCreationRequest creationRequest =
                new PostCreationRequest("post-title", "post-content");

        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.registerNewSession(response, member.getId());

        // when & then
        mockMvc.perform(post("/api/v2/posts")
                        .cookie(response.getCookies())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-create-v2",
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("identifier").description("게시글 식별자")
                        )));
    }

    @Test
    @DisplayName("게시글 생성 실패 - 로그인하지 않은 회원은 게시글을 작성할 수 없다.")
    void should_return_unAuthorized_when_non_login_member_attempt_to_update_post() throws Exception {
        // given
        Member member = this.dummyMember;
        memberRepository.save(member);

        PostCreationRequest creationRequest =
                new PostCreationRequest("post-title", "post-content");

        // when & then
        mockMvc.perform(post("/api/v2/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationRequest)))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(document("post-create-fail-v2",
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("errorMessage").description("게시글 생성 불가 사유")
                        )));
    }

    @Test
    @DisplayName("게시글 수정 실패 - 작성자 외의 회원이 게시글을 수정 시 403을 리턴한다.")
    void should_return_forbidden_for_non_login_member() throws Exception {
        // given
        Member postAuthor = this.dummyMember;
        Member otherMember = Member.create("otherMember@email.com", "password", "name");
        memberRepository.save(postAuthor);
        memberRepository.save(otherMember);

        Post post = Post.create(postAuthor, "old-title", "old-content");
        postRepository.save(post);

        PostUpdateRequest updateRequest = new PostUpdateRequest("new-title", "new-content");

        MockHttpServletResponse response = new MockHttpServletResponse();
        sessionManager.registerNewSession(response, otherMember.getId());

        // when & then
        mockMvc.perform(patch("/api/v2/posts/{postId}", post.getId())
                        .cookie(response.getCookies())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andDo(document("post-update-fail-v2",
                        pathParameters(
                                parameterWithName("postId").description("게시글 식별자")
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("errorMessage").description("게시글 생성 불가 사유")
                        )));
    }
}