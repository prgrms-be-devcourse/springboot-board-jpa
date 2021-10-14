package com.prgrms.dlfdyd96.board.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.dlfdyd96.board.domain.Post;
import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.post.dto.CreatePostRequest;
import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import com.prgrms.dlfdyd96.board.post.dto.UpdatePostRequest;
import com.prgrms.dlfdyd96.board.post.service.PostService;
import java.util.List;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PostService postService;

  @Autowired
  ObjectMapper objectMapper;

  private User user;
  private Post post;

  @BeforeEach
  void setUp() {
    user = User.builder()
        .id(1L)
        .name("yong")
        .age(11)
        .hobby("running")
        .build();

    post = Post.builder()
        .id(1L)
        .title("ㅈㅔ목")
        .content("내용임다")
        .user(user)
        .build();
  }

  @Test
  @DisplayName("[POST] '/posts' 요청 성공")
  void saveCallTest() throws Exception {
    // GIVEN
    Long stubPostId = post.getId();
    given(postService.save(any())).willReturn(stubPostId);

    CreatePostRequest requestDto = CreatePostRequest.builder()
        .title(post.getTitle())
        .content(post.getContent())
        .userId(post.getUser().getId())
        .build();

    RequestBuilder request = MockMvcRequestBuilders.post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto));

    // WHEN // THEN
    mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(document("post-save",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("user id")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("[POST] '/posts' 요청 실패")
  void saveCallExceptionTest() throws Exception {
    // GIVEN
    given(postService.save(any())).willThrow(new NotFoundException("사용자를 찾을 수 없습니다."));

    CreatePostRequest requestDto = CreatePostRequest.builder()
        .title(post.getTitle())
        .content(post.getContent())
        .userId(post.getUser().getId())
        .build();
    RequestBuilder request = MockMvcRequestBuilders.post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto));

    // WHEN // THEN
    ResultActions resultActions = mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(print())
        .andDo(document("post-save-error",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("user id")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("[POST] '/posts' 요청 Request DTO 유효성 테스트 title")
  void saveCallRequestDTOValidationTitleTest() throws Exception {
    // GIVEN
    Long stubPostId = post.getId();
    given(postService.save(any())).willReturn(stubPostId);

    CreatePostRequest requestNullDto = CreatePostRequest.builder()
        // .title(post.getTitle())
        .content(post.getContent())
        .userId(post.getUser().getId())
        .build();
    RequestBuilder requestTitleIsNull = MockMvcRequestBuilders.post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestNullDto));

    // WHEN
    // THEN
    mockMvc.perform(requestTitleIsNull)
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  @DisplayName("[POST] '/posts' 요청 Request DTO 유효성 테스트 Content")
  void saveCallRequestDTOValidationContentTest() throws Exception {
    // GIVEN
    Long stubPostId = post.getId();
    given(postService.save(any())).willReturn(stubPostId);

    CreatePostRequest requestDto1 = CreatePostRequest.builder()
        .title(post.getTitle())
        .content("")
        .userId(post.getUser().getId())
        .build();
    RequestBuilder requestContentEmpty = MockMvcRequestBuilders.post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto1));

    CreatePostRequest requestDto2 = CreatePostRequest.builder()
        .title(post.getTitle())
        // .content("")
        .userId(post.getUser().getId())
        .build();
    RequestBuilder requestContentNull = MockMvcRequestBuilders.post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto2));

    // WHEN
    // THEN
    mockMvc.perform(requestContentEmpty)
        .andExpect(status().isCreated())
        .andDo(print());
    mockMvc.perform(requestContentNull)
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  @DisplayName("[GET] '/posts' 테스트")
  void getAllCallTest() throws Exception {
    // GIVEN
    Page<PostResponse> stubFindPosts = new PageImpl<>(
        List.of(
            PostResponse.builder()
                .content(post.getContent())
                .userName(post.getUser().getName())
                .id(post.getId())
                .title(post.getTitle())
                .build(),
            PostResponse.builder()
                .content("새로운 내용")
                .userName("min_jung")
                .id(post.getId() + 1)
                .title("제목2")
                .build()
        )
    );

    given(postService.findPosts(any())).willReturn(stubFindPosts);
    RequestBuilder request = MockMvcRequestBuilders.get("/posts")
        .param("page", String.valueOf(0))
        .param("size", String.valueOf(10))
        .contentType(MediaType.APPLICATION_JSON);

    // WHEN
    // THEN
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-get-all", // 문서화
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("컨텐츠"),
                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                    .description("내용"),
                fieldWithPath("data.content[].userName").type(JsonFieldType.STRING)
                    .description("작성자 이름"),
                fieldWithPath("data.pageable").type(JsonFieldType.STRING)
                    .description("요청한 pageable 정보"),
                // TODO: 테스트 코드에선 "INSTANCE"가 나오고 실제 실행할땐 밑에 있는 객체로 나온다....모징?
//                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT)
//                    .description("요청한 pageable 정보"),
//                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
//                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
//                    .description("정렬 여부"),
//                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
//                    .description("미정렬 여부"),
//                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
//                    .description("정렬 정보 빈 값 여부"),
//                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
//                    .description("요청한 페이지 정보"),
//                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
//                    .description("요청한 페이지 크기"),
//                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("??"),
//                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
//                    .description("페이징 여부"),
//                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
//                    .description("미페이징 여부"),
                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
                    .description("전체 페이지 갯수"),
                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                    .description("데이터 갯수"),
                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("요청 페이지에서 조회 된 데이터의 갯수"),
                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("미정렬 여부"),
                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("정렬 정보 빈 값 여부"),
                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 정보"),
                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                    .description("한 페이지 당 몇 개의 목록이 표시 되는지"),
                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN)
                    .description("페이지가 비어있는지 여부"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("[GET] '/posts/{id}' 테스트")
  void getOneCallTest() throws Exception {
    // GIVEN
    Long givenPostId = post.getId();
    PostResponse stubFindOne = PostResponse.builder()
        .title(post.getTitle())
        .content(post.getContent())
        .id(post.getId())
        .userName(post.getUser().getName())
        .build();
    given(postService.findOne(givenPostId)).willReturn(stubFindOne);

    RequestBuilder request = get("/posts/{id}", givenPostId)
        .contentType(MediaType.APPLICATION_JSON);
    // WHEN
    // THEN
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-get-one",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("post id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("작성자 이름"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("[GET] '/posts/{id}' not found exception 테스트")
  void getOneCallExceptionTest() throws Exception {
    // GIVEN
    Long givenWrongPostId = 9899L;
    given(postService.findOne(givenWrongPostId)).willThrow(
        new NotFoundException("게시물을 찾을 수 없습니다."));

    RequestBuilder request = get("/posts/{id}", givenWrongPostId)
        .contentType(MediaType.APPLICATION_JSON);

    // WHEN
    // THEN
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(print())
        .andDo(document("post-get-one-error",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("에러 메세지"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("[PUT] '/posts/{id} 요청 성공")
  void updateCallTest() throws Exception {
    // GIVEN
    Long givenPostId = post.getId();
    UpdatePostRequest givenRequest = UpdatePostRequest.builder()
        .userId(user.getId())
        .title("수정할 제목")
        .content("수정할 내용")
        .build();
    PostResponse stubUpdate = PostResponse.builder()
        .id(givenPostId)
        .title(givenRequest.getTitle())
        .content(givenRequest.getContent())
        .userName(post.getUser().getName())
        .build();

    given(postService.update(anyLong(), any())).willReturn(stubUpdate);

    RequestBuilder request = put("/posts/{id}", givenPostId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(givenRequest));

    // WHEN
    // THEN
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-update",
            requestFields(
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("user Id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("post id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("작성자 이름"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("[PUT] '/posts/{id} 요청 postId 예외")
  void updateCallExceptionTest() throws Exception {
    // GIVEN
    Long givenWrongPostId = post.getId();
    UpdatePostRequest givenRequest = UpdatePostRequest.builder()
        .userId(user.getId())
        .title("수정할 제목")
        .content("수정할 내용")
        .build();

    given(postService.update(anyLong(), any())).willThrow(new NotFoundException("게시물을 찾을 수 없습니다."));

    RequestBuilder request = put("/posts/{id}", givenWrongPostId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(givenRequest));

    // WHEN
    // THEN
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(print())
        .andDo(document("post-update-error",
            requestFields(
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("user Id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("예외 메세지"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));

  }

  @Test
  @DisplayName("[PUT] '/posts' 요청 Request DTO 유효성 테스트 title")
  void updateCallRequestDTOValidationTitleTest() throws Exception {
    // GIVEN
    Long givenPostId = post.getId();
    UpdatePostRequest requestNullDto = UpdatePostRequest.builder()
        // .title(post.getTitle())
        .content(post.getContent())
        .userId(post.getUser().getId())
        .build();
    RequestBuilder requestTitleIsNull = MockMvcRequestBuilders.put("/posts/{id}", givenPostId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestNullDto));

    // WHEN
    // THEN
    mockMvc.perform(requestTitleIsNull)
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  @DisplayName("[PUT] '/posts' 요청 Request DTO 유효성 테스트 - Content")
  void updateCallRequestDTOValidationContentTest() throws Exception {
    // GIVEN
    Long givenPostId = post.getId();

    UpdatePostRequest requestContentIsNullDto = UpdatePostRequest.builder()
        .title(post.getTitle())
        // .content(post.getContent())
        .userId(post.getUser().getId())
        .build();
    RequestBuilder requestContentIsNull = MockMvcRequestBuilders.put("/posts/{id}", givenPostId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestContentIsNullDto));

    UpdatePostRequest requestEmptyDto = UpdatePostRequest.builder()
        .title(post.getTitle())
        .content("")
        .userId(post.getUser().getId())
        .build();
    RequestBuilder requestContentIsEmpty = MockMvcRequestBuilders.put("/posts/{id}", givenPostId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestEmptyDto));

    // WHEN
    // THEN
    mockMvc.perform(requestContentIsNull)
        .andExpect(status().isBadRequest())
        .andDo(print());

    mockMvc.perform(requestContentIsEmpty)
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  @DisplayName("[DELETE] '/posts/{id} 요청 성공")
  void deleteCallTest() throws Exception {
    // GIVEN
    Long givenPostId = post.getId();
    RequestBuilder request = delete("/posts/{id}", givenPostId)
        .contentType(MediaType.APPLICATION_JSON);

    // WHEN
    // THEN
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-delete",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("affected Item"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("[DELETE] '/posts/{id} 요청 예외")
  void deleteCallExceptionTest() throws Exception {
    // GIVEN
    Long givenPostId = post.getId();
    RequestBuilder request = delete("/posts/{id}", givenPostId)
        .contentType(MediaType.APPLICATION_JSON);
    willThrow(new NotFoundException("게시물을 찾을 수 없습니다.")).given(postService).delete(anyLong());

    // WHEN
    // THEN
    mockMvc.perform(request)
        .andExpect(status().isNotFound())
        .andDo(print())
        .andDo(document("post-delete-error",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("예외 메세지"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }
}