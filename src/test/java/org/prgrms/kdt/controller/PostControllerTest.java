package org.prgrms.kdt.controller;

import static java.text.MessageFormat.format;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.User;
import org.prgrms.kdt.dto.PostDto.PostRequest;
import org.prgrms.kdt.dto.PostDto.PostResponse;
import org.prgrms.kdt.dto.UserDto.CurrentUser;
import org.prgrms.kdt.mapper.UserMapper;
import org.prgrms.kdt.repository.UserRepository;
import org.prgrms.kdt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureRestDocs
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PostService postService;

  @MockBean
  private UserMapper userMapper;

  @MockBean
  private UserRepository userRepository;

  @Test
  @DisplayName("게시글을 페이징으로 조회한다.")
  void test_paging() throws Exception {
    List<PostResponse> postResponses = LongStream.range(0, 5).mapToObj(
        i -> new PostResponse(i,
            format("제목 {0}", i),
            format("내용 {0}", i),
            format("작성자 {0}", i),
            LocalDateTime.now(),
            LocalDateTime.now())).toList();
    Page<PostResponse> postResponsePage = new PageImpl<>(postResponses);
    when(postService.findPosts(PageRequest.of(2, 5)))
        .thenReturn(postResponsePage);

    mockMvc.perform(get("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .param("page", "2")
            .param("size", "5"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("find-posts",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("페이지 크기")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("serverDateTime").type(STRING).description("서버 응답 시간"),
                    fieldWithPath("data.content[].id").type(NUMBER).description("게시글 ID"),
                    fieldWithPath("data.content[].title").type(STRING).description("게시글 제목"),
                    fieldWithPath("data.content[].content").type(STRING).description("게시글 내용"),
                    fieldWithPath("data.content[].createdBy").type(STRING).description("게시글 생성자"),
                    fieldWithPath("data.content[].createdAt").type(STRING).description("게시글 생성시간"),
                    fieldWithPath("data.content[].updatedAt").type(STRING).description("게시글 수정시간"),
                    fieldWithPath("data.pageable").type(STRING).description("페이지"),
                    fieldWithPath("data.last").type(BOOLEAN).description("마지막 페이지 여부"),
                    fieldWithPath("data.totalPages").type(NUMBER).description("전체 페이지 수"),
                    fieldWithPath("data.totalElements").type(NUMBER).description("전체 게시글 수"),
                    fieldWithPath("data.size").type(NUMBER).description("페이지 크기"),
                    fieldWithPath("data.number").type(NUMBER).description("페이지 번호"),
                    fieldWithPath("data.sort.empty").type(BOOLEAN).description("정렬 설정 여부"),
                    fieldWithPath("data.sort.sorted").type(BOOLEAN).description("정렬 여부"),
                    fieldWithPath("data.sort.unsorted").type(BOOLEAN).description("비정렬 여부"),
                    fieldWithPath("data.first").type(BOOLEAN).description("첫 페이지 여부"),
                    fieldWithPath("data.numberOfElements").type(NUMBER).description("게시글 개수"),
                    fieldWithPath("data.empty").type(BOOLEAN).description("빈 값 여부")
                )
            )
        );
  }

  @Test
  @DisplayName("게시글을 단건 조회한다.")
  void test_vv() throws Exception {
    PostResponse response = new PostResponse(1L,
        "제목",
        "내용",
        "작성자",
        LocalDateTime.now(),
        LocalDateTime.now());
    when(postService.findPost(1L)).thenReturn(response);

    mockMvc.perform(get("/posts/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("find-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("data.id").type(NUMBER).description("게시글 ID"),
                    fieldWithPath("data.title").type(STRING).description("게시글 제목"),
                    fieldWithPath("data.content").type(STRING).description("게시글 내용"),
                    fieldWithPath("data.createdAt").type(STRING).description("게시글 생성 시간"),
                    fieldWithPath("data.updatedAt").type(STRING).description("게시글 수정 시간"),
                    fieldWithPath("data.createdBy").type(STRING).description("게시글 생성자"),
                    fieldWithPath("serverDateTime").type(STRING).description("서버 응답 시간")
                )
            )
        );
  }

  @Test
  @DisplayName("게시글을 생성한다.")
  void test_create_post() throws Exception {
    PostRequest postRequest = new PostRequest("제목은", "내용은");
    User user = mock(User.class);
    when(postService.createPost(1L, postRequest)).thenReturn(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(user.getId()).thenReturn(1L);
    when(userMapper.of(user)).thenReturn(new CurrentUser(1L, "작성자", 26));

    mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postRequest))
            .header(AUTHORIZATION, "1")
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("create-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName("Authorization").description("작성자 ID")
                ),
                requestFields(
                    fieldWithPath("title").type(STRING).description("게시글 제목"),
                    fieldWithPath("content").type(STRING).description("게시글 내용")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("data").type(NUMBER).description("게시글 ID"),
                    fieldWithPath("serverDateTime").type(STRING).description("서버 응답 시간")
                )
            )
        );
  }

  @Test
  @DisplayName("게시글 제목과 내용이 비워져있으면 에러가 발생한다.")
  void test_throws_create_post() throws Exception {
    PostRequest postRequest = new PostRequest("", "");
    when(postService.createPost(1L, postRequest)).thenReturn(1L);

    mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postRequest)))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andDo(document("invalid-create-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(STRING).description("게시글 제목"),
                    fieldWithPath("content").type(STRING).description("게시글 내용")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("data").type(STRING).description("에러 메시지"),
                    fieldWithPath("serverDateTime").type(STRING).description("서버 응답 시간")
                )
            )
        );
  }

  @Test
  @DisplayName("게시글을 수정한다.")
  void test_update_post() throws Exception {
    PostRequest postRequest = new PostRequest("새 제목", "바뀐 내용");
    when(postService.updatePost(1L, postRequest)).thenReturn(1L);

    mockMvc.perform(patch("/posts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("update-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(STRING).description("게시글 제목"),
                    fieldWithPath("content").type(STRING).description("게시글 내용")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("HTTP 상태 코드"),
                    fieldWithPath("data").type(NUMBER).description("게시글 ID"),
                    fieldWithPath("serverDateTime").type(STRING).description("서버 응답 시간")
                )
            )
        );
  }
}