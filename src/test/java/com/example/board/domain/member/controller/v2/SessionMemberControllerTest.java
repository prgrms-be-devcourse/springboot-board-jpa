package com.example.board.domain.member.controller.v2;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.board.domain.member.controller.v2.session.SessionManager;
import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.member.service.MemberService;
import com.example.board.domain.post.dto.PostRequest;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class SessionMemberControllerTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private SessionManager sessionManager;

  @Autowired
  private MemberService memberService;

  @Autowired
  private PostService postService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void tearDown(){
    postRepository.deleteAll();
    memberRepository.deleteAll();
  }

  @Test
  @DisplayName("사용자를 등록할 수 있습니다")
  void signUp() throws Exception {
    //given
    MemberRequest.SignUp signUpRequest = new MemberRequest.SignUp("김환", "email123@naver.com", "password123!", 25, "게임");

    //when & then
    mockMvc.perform(post("/members/v2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(signUpRequest)))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(document("member-save",
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
            )));
  }

  @Test
  @DisplayName("이메일과 비밀번호로 로그인할 수 있습니다.")
  void loginSuccess() throws Exception {
    //given
    MemberRequest.SignUp signUpRequest = new MemberRequest.SignUp("김환", "email123@naver.com", "password123!", 25, "게임");
    Long savedMemberId = memberService.save(signUpRequest);

    MemberRequest.Login loginMember = new MemberRequest.Login("email123@naver.com", "password123!");

    //when & then
    mockMvc.perform(post("/members/v2/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginMember)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("member-login",
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("password")
            )));
  }

  @Test
  @DisplayName("사용자를 조회할 수 있습니다")
  void findMemberById() throws Exception {
    //given
    MemberRequest.SignUp signUpRequest = new MemberRequest.SignUp("김환", "email123@naver.com", "password123!", 25, "게임");
    Long savedMemberId = memberService.save(signUpRequest);
    postService.save(new PostRequest("게시글", "내용내용", savedMemberId));

    //when & then
    UUID sessionId = testLogin(savedMemberId, "email123@naver.com");
    mockMvc.perform(get("/members/v2/mypage")
            .cookie(new Cookie("sessionId", sessionId.toString())))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("member-find-by-id",
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("member id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                fieldWithPath("posts[]").type(JsonFieldType.ARRAY).optional().description("list of posts"),
                fieldWithPath("posts[].id").type(JsonFieldType.NUMBER).description("post id"),
                fieldWithPath("posts[].title").type(JsonFieldType.STRING).description("title of post"),
                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("title of post"),
                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("title of post")
            )));
  }

  private UUID testLogin(Long savedMemberId, String email) {

    return sessionManager.login(savedMemberId, email);
  }
}