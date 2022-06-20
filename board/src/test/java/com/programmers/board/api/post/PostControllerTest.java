package com.programmers.board.api.post;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.board.core.post.application.PostService;
import com.programmers.board.core.post.application.dto.PostCreateRequest;
import com.programmers.board.core.post.application.dto.PostResponse;
import com.programmers.board.core.post.application.dto.PostUpdateRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = PostController.class)
@AutoConfigureRestDocs
class PostControllerTest {

  @MockBean
  private PostService postService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String BASE_URL = "/api/v1";

  @Test
  @DisplayName("Happy Path: 포스팅 생성 테스트")
  void savePost() throws Exception {

    //Given
    PostCreateRequest postCreateRequest = PostCreateRequest.builder()
        .title("happyTitle")
        .content("happyContent")
        .userId(1L)
        .build();

    when(postService.save(postCreateRequest))
        .thenReturn(new PostResponse(1L, "happyTitle", "happyContent"));

    mockMvc.perform(post(BASE_URL + "/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postCreateRequest))
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-save",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                fieldWithPath("body").type(JsonFieldType.OBJECT).description("body"),
                fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("포스트 ID"),
                fieldWithPath("body.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("body.content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 응답시간")
            )
        ));

  }

  @Test
  @DisplayName("Happy Path: 포스팅 단 건 조회")
  void getOnePost() throws Exception {

    PostCreateRequest createRequestPost = PostCreateRequest.builder()
        .title("happyTitle")
        .content("happyContent")
        .userId(1L)
        .build();
    PostResponse responsePost = postService.save(createRequestPost);

    mockMvc.perform(get(BASE_URL + "/posts/{id}", responsePost.getId())
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-get-one",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                fieldWithPath("body").type(JsonFieldType.OBJECT).description("body"),
                fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("포스트 ID"),
                fieldWithPath("body.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("body.content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 응답시간")
            )
        ));

  }

  @Test
  @DisplayName("Happy Path: 포스팅 다 건 조회")
  void getMultiplePost() throws Exception {

    List<PostCreateRequest> createRequestList = new ArrayList<>();
    for (int i = 1; i <= 20; i++) {
      PostCreateRequest createRequestPost = PostCreateRequest.builder()
          .title("happyTitle" + i)
          .content("happyContent" + i)
          .userId(1L)
          .build();
      createRequestList.add(createRequestPost);
    }

    createRequestList.forEach(postService::save);

    mockMvc.perform(get(BASE_URL + "/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .param("page", "1")
            .param("size", "5")
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-get-multiple",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                fieldWithPath("body").type(JsonFieldType.OBJECT).description("body"),
                fieldWithPath("body.content[]").type(JsonFieldType.ARRAY).description("포스트 리스트"),
                fieldWithPath("body.content[].id").type(JsonFieldType.NUMBER).description("Id"),
                fieldWithPath("body.content[].title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("body.content[].content").type(JsonFieldType.STRING)
                    .description("내용"),
                fieldWithPath("body.sort").type(JsonFieldType.OBJECT).description("페이지 정보"),
                fieldWithPath("body.sort.sorted").type(JsonFieldType.BOOLEAN)
                    .description("페이징 정렬 여부"),
                fieldWithPath("body.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("페이징 미정렬 여부"),
                fieldWithPath("body.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("페이징 속성 존재 여부"),
                fieldWithPath("body.pageable.pageNumber").type(JsonFieldType.NUMBER)
                    .description("페이지 인덱스"),
                fieldWithPath("body.pageable.pageSize").type(JsonFieldType.NUMBER)
                    .description("페이지 크기"),
                fieldWithPath("body.pageable.offset").type(JsonFieldType.NUMBER)
                    .description("offset"),
                fieldWithPath("body.pageable.paged").type(JsonFieldType.BOOLEAN)
                    .description("Pageable paging 여부"),
                fieldWithPath("body.pageable.unpaged").type(JsonFieldType.BOOLEAN)
                    .description("Pageable not paging 여부"),
                fieldWithPath("body.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                    .description("페이지 정렬 여부"),
                fieldWithPath("body.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("페이지 미정렬 여부"),
                fieldWithPath("body.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("페이지 정렬 값의 여부"),
                fieldWithPath("body.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                fieldWithPath("body.totalElements").type(JsonFieldType.NUMBER)
                    .description("전체 포스트 수"),
                fieldWithPath("body.last").type(JsonFieldType.BOOLEAN)
                    .description("현재 페이지가 마지막 여부"),
                fieldWithPath("body.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("현재 페이지 내의 원소 개수"),
                fieldWithPath("body.first").type(JsonFieldType.BOOLEAN)
                    .description("현재 페이지가 첫번째 페이지인지 여부"),
                fieldWithPath("body.size").type(JsonFieldType.NUMBER).description("현재 페이지의 크기"),
                fieldWithPath("body.number").type(JsonFieldType.NUMBER)
                    .description("현재 페이지의 index"),
                fieldWithPath("body.empty").type(JsonFieldType.BOOLEAN).description("페이지 비어있는지 여부"),
                fieldWithPath("body.empty").type(JsonFieldType.BOOLEAN).description("페이지 비어있는지 여부"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 응답시간")
            )
        ));

  }

  @Test
  @DisplayName("Happy Path: 포스팅 수정 테스트")
  void updatedPost() throws Exception {
    PostCreateRequest createRequestPost = PostCreateRequest.builder()
        .title("happyTitle")
        .content("happyContent")
        .userId(1L)
        .build();
    PostResponse responsePost = postService.save(createRequestPost);

    PostUpdateRequest updateRequestPost = PostUpdateRequest.builder()
        .title("수정된 제목")
        .content("수정된 내용")
        .build();

    mockMvc.perform(put(BASE_URL + "/posts/{id}", responsePost.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequestPost))
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-update",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                fieldWithPath("body").type(JsonFieldType.OBJECT).description("body"),
                fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("포스트 ID"),
                fieldWithPath("body.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("body.content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 응답시간")
            )
        ));
  }

}