package devcourse.board.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcourse.board.api.authentication.CookieConst;
import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import devcourse.board.domain.post.PostRepository;
import devcourse.board.domain.post.model.Post;
import devcourse.board.domain.post.model.PostCreationRequest;
import devcourse.board.domain.post.model.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 생성")
    void call_createPost() throws Exception {
        // given
        Member member = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null);
        memberRepository.save(member);

        PostCreationRequest creationRequest =
                new PostCreationRequest(member.getId(), "title", "content");

        // when & then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("memberId").description("회원 식별자"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("identifier").description("게시글 식별자")
                        )));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void call_getPost() throws Exception {
        // given
        Member member = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null);
        memberRepository.save(member);

        Post post = Post.createPost(member, "post-title", "post-content");
        postRepository.save(post);

        // when & then
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-one",
                        pathParameters(
                                parameterWithName("postId").description("게시글 식별자")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("게시글 식별자"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("createdAt").description("게시글 작성 시각"),
                                fieldWithPath("createdBy").description("게시글 작성자 이름")
                        )));
    }

    @Test
    @DisplayName("게시글 페이징 조회")
    void call_getPosts() throws Exception {
        // given
        Member member = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null);
        memberRepository.save(member);

        Post post = Post.createPost(member, "post-title", "post-content");
        postRepository.save(post);

        // when & then
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-all",
                        requestParameters(
                                parameterWithName("page").description("시작 페이지"),
                                parameterWithName("size").description("조회할 게시글 개수")
                        ),
                        responseFields(
                                fieldWithPath("simplePostResponses").description("게시글 페이징 조회 결과"),

                                fieldWithPath("simplePostResponses[].postId").description("게시글 식별자"),
                                fieldWithPath("simplePostResponses[].title").description("게시글 제목")
                        )));
    }

    @Test
    @DisplayName("회원은 자신이 작성한 게시글에 대해서만 수정할 수 있다.")
    void call_updatePost() throws Exception {
        // given
        Member member = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null);
        memberRepository.save(member);

        Post post = Post.createPost(member, "old-title", "old-content");
        postRepository.save(post);

        PostUpdateRequest updateRequest = new PostUpdateRequest("new-title", "new-content");

        Cookie idCookie = new Cookie(CookieConst.MEMBER_ID, String.valueOf(member.getId()));

        // when & then
        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .cookie(idCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        pathParameters(
                                parameterWithName("postId").description("게시글 식별자")
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("게시글 식별자"),
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("createdAt").description("게시글 아이디"),
                                fieldWithPath("createdBy").description("게시글 작성자 이름")
                        )));
    }
}