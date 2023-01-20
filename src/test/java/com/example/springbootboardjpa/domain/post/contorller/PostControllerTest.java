package com.example.springbootboardjpa.domain.post.contorller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootboardjpa.domain.member.entity.Member;
import com.example.springbootboardjpa.domain.member.repository.MemberRepository;
import com.example.springbootboardjpa.domain.post.dto.request.PostSaveRequestDto;
import com.example.springbootboardjpa.domain.post.dto.request.PostUpdateRequestDto;
import com.example.springbootboardjpa.domain.post.entity.Post;
import com.example.springbootboardjpa.domain.post.repository.PostRepository;
import com.example.springbootboardjpa.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
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

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  MemberRepository memberRepository;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private PostService postService;
  @Autowired
  private PostRepository postRepository;

  @BeforeEach
  void unitSetup() {
    postRepository.deleteAll();
    memberRepository.deleteAll();

    Member member = Member.builder()
        .name("장주영")
        .age(26)
        .hobby("풋살")
        .build();

    member = memberRepository.save(member);

    Post post = Post.builder()
        .title("제목")
        .content("내용")
        .member(member)
        .build();

    postRepository.save(post);
  }

  @Test
  @DisplayName("게시글을 전부 조회하면 Http 200상태 코드가 반환")
  void findAllTest() throws Exception {
    mockMvc.perform(
            get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document(
                "PostController/findAll",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("page").description("페이지"),
                    parameterWithName("size").description("페이지 사이즈")
                ),
                responseFields(
                    fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("게시글 아이디")
                        .optional(),
                    fieldWithPath("[].title").type(JsonFieldType.STRING).description("게시글 제목")
                        .optional(),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING)
                        .description("게시글 생성 시간").optional()
                )
            )
        );

  }

  @Test
  @DisplayName("게시글 상세 조회 api 성공 시 http status 200 반환")
  void findByIdTest() throws Exception {
    List<Post> posts = postRepository.findAll();

    mockMvc.perform(
            get("/posts/{postId}", String.valueOf(posts.get(0).getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        )
        .andExpect(status().isOk())
        .andDo(
            document(
                "PostController/findById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postId").description("조회할 게시글 아이디")
                ),
                responseFields(
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 작성 시간")
                )
            )
        );
  }

  @Test
  @DisplayName("게시글 생성에 성공하면 http 200 상태코드가 반환")
  void saveTest() throws Exception {
    List<Member> members = memberRepository.findAll();

    PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto(
        "title",
        "content",
        members.get(0).getId()
    );

    String json = objectMapper.writeValueAsString(postSaveRequestDto);

    mockMvc.perform(
            post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("title").value("title"))
        .andExpect(jsonPath("content").value("content"))
        .andDo(
            document(
                "PostController/save",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                ),
                responseFields(
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간")
                )
            )
        );

  }

  @Test
  @DisplayName("게시글 생성 시 없는 회원 아이디로 요청하면 http status 404 code가 반환 ")
  void saveNotFoundMemberTest() throws Exception {
    memberRepository.deleteAll();
    List<Member> members = memberRepository.findAll();

    PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto(
        "title",
        "content",
        members.get(0).getId()
    );

    String json = objectMapper.writeValueAsString(postSaveRequestDto);

    mockMvc.perform(
            post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        )
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        .andDo(
            document(
                "PostController/save",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                ),
                responseFields(
                    fieldWithPath("timestamp").type(JsonFieldType.STRING).description("에러 발생 시간"),
                    fieldWithPath("status").type(JsonFieldType.NUMBER)
                        .description("http status code"),
                    fieldWithPath("error").type(JsonFieldType.STRING).description("에러"),
                    fieldWithPath("code").type(JsonFieldType.STRING).description("에러 코드"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지")
                )
            )
        );
  }

  @Test
  @DisplayName("게시물을 업데이트를 성공적으로 완료하면 http status 200 code가 반환")
  void update() throws Exception {
    List<Member> members = memberRepository.findAll();
    List<Post> posts = postRepository.findAll();

    PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto(
        "수정된 제목",
        "수정 된 내용",
        members.get(0).getId()
    );

    String json = objectMapper.writeValueAsString(postUpdateRequestDto);

    mockMvc.perform(
            post("/posts/{postId}", String.valueOf(posts.get(0).getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json)
        )
        .andExpect(status().isOk())
        .andDo(
            document(
                "PostController/update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postId").description("조회할 게시글 아이디")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                ),
                responseFields(
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성 시간")
                )
            )
        );
  }
}