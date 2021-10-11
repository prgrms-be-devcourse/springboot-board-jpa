package com.prgrms.dlfdyd96.board.post.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.dto.UserDto;
import com.prgrms.dlfdyd96.board.post.repository.PostRepository;
import com.prgrms.dlfdyd96.board.post.service.PostService;
import java.awt.print.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.ResultActions;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

  private static final Long MIN_POST_ID = 0L;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostService postService;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void save_test() {
    // GIVEN
    PostDto postDto = PostDto.builder()
        .title("제목임다.")
        .content("내용1 \n 내용2")
        .userDto(
            UserDto.builder()
                .name("일용")
                .age(26)
                .hobby("read book")
                .build()
        )
        .build();

    // WHEN
    Long postId = postService.save(postDto);

    // THEN
    assertThat(postId).isGreaterThanOrEqualTo(MIN_POST_ID);
  }

  @AfterEach
  void tearDown() {
    postRepository.deleteAll();
  }

  @Test
  @DisplayName("[POST] '/posts' 요청 성공")
  void saveCallTest() throws Exception {
    // GIVEN
    PostDto postDto = PostDto.builder()
        .title("제목임다.")
        .content("내용1 \n 내용2")
        .userDto(
            UserDto.builder()
                .name("일용")
                .age(26)
                .hobby("read book")
                .build()
        )
        .build();

    // WHEN // THEN
    ResultActions resultActions = mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-save",
            requestFields(
                fieldWithPath("id").type(JsonFieldType.NULL).description("id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("userDto.id"),
                fieldWithPath("userDto.name").type(JsonFieldType.STRING)
                    .description("userDto.name"),
                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING)
                    .description("userDto.hobby")
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
    PostDto postDto = PostDto.builder()
        .title("제목임다. 에러에러")
        .content("내용1 \n 내용2")
        .userDto(
            UserDto.builder()
//                .id(66L)
//                .name("일용알용에러")
                .age(26)
                .hobby("read book")
                .build()
        )
        .build();

    // WHEN // THEN
    ResultActions resultActions = mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
        .andExpect(status().isInternalServerError())
        .andDo(print())
        .andDo(document("post-save-error",
            requestFields(
                fieldWithPath("id").type(JsonFieldType.NULL).description("id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("userDto.id"),
                fieldWithPath("userDto.name").type(JsonFieldType.NULL)
                    .description("userDto.name"),
                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING)
                    .description("userDto.hobby")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("pageable 파라미터(page, size)와 함께 post들을 조회 할 수 있다.")
  void getAllWithPageAndSize() throws Exception {
    // GIVEN
    // WHEN
    // THEN
    mockMvc.perform(get("/posts")
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-get-all",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("컨텐츠"),
                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("작성자"),
                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("id"),
                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("나이"),
                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("취미"),
                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("요청한 pageable 정보"),
                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬 여부"),
                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 정보 빈 값 여부"),
                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("요청한 페이지 정보"),
                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("요청한 페이지 크기"),
                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("??"),
                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부"),
                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("미페이징 여부"),
                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 갯수"),
                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("데이터 갯수"),
                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("요청 페이지에서 조회 된 데이터의 갯수"),
                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("정렬 정보"),
                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬 여부"),
                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 정보 빈 값 여부"),
                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 정보"),
                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("한 페이지 당 몇 개의 목록이 표시 되는지"),
                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("페이지가 비어있는지 여부"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  // TODO: GET /posts  하면 data.pageable.paged : false인 경우 인가? -> service에서 테스트

  @Test
  @DisplayName("단건으로 게시물을 조회할 수 있다.")
  void getPost() throws Exception {
    // GIVEN
    Long postId = postService.save(
        PostDto.builder()
            .title("단건 제목")
            .content("컨텐츠")
            .userDto(
                UserDto.builder()
                    .name("일용")
                    .age(26)
                    .hobby("축구")
                    .build()
            )
            .build()
    );
    // WHEN
    // THEN
    mockMvc.perform(get("/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  @DisplayName("id로 게시물을 조회할 수 없는 경우 NotFoundException을 내뱉는다.")
  void getPostException() throws Exception {
    // GIVEN
    Long postId = postService.save(
        PostDto.builder()
            .title("단건 제목")
            .content("컨텐츠")
            .userDto(
                UserDto.builder()
                    .name("일용")
                    .age(26)
                    .hobby("축구")
                    .build()
            )
            .build()
    );
    Long noDataDummy = postId + 1;
    // WHEN
    // THEN
    mockMvc.perform(get("/posts/{id}", noDataDummy)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

}