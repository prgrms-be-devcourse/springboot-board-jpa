package com.prgrms.be.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.be.app.domain.dto.PostDTO;
import com.prgrms.be.app.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    //toDo : 생성 테스트
    @Test
    @DisplayName("게시글 생성 시 제목이 입력되지 않는 경우, 메시지를 반환합니다.")
    void validSavePostTest() throws Exception {
        // given
        PostDTO.CreateRequest request = new PostDTO.CreateRequest("", "content", 1L);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        log.error("{}", response.getStatus());
        log.error("{}", response.getErrorMessage());
        log.error("{}", response.getContentAsString(Charset.forName("UTF-8")));

        // then
    }


    @Test
    @DisplayName("게시글 생성 성공 시 상태코드 200과 생성된 게시글 id가 반환된다.")
    void saveCallTest() throws Exception {
        // given
        PostDTO.CreateRequest request = new PostDTO.CreateRequest("title", "content", 1L);
        when(postService.createPost(any(PostDTO.CreateRequest.class)))
                .thenReturn(1L);

        // when
        mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("statusCode").value(200))
                .andExpect(jsonPath("data").value(1L))
                .andExpect(jsonPath("message").value(ResponseMessage.CREATED))
                .andExpect(jsonPath("serverDateTime").exists())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 글쓴이 Id")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 글쓴이 Id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 HTTP 상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("응답 결과 - 생성된 게시글 Id"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답 생성 시간")
                        ))
                );

        // then
        verify(postService).createPost(any(PostDTO.CreateRequest.class));
    }

    //toDo : 단건 조회 테스트
    @Test
    @DisplayName("게시글 ID로 게시글 단건 조회 시 게시글의 제목 및 본문과 같은 디테일한 내용을 볼 수 있다.")
    void getOneCallTest() throws Exception {
        // given
        Long id = 1L;
        PostDTO.PostDetailResponse postDetailResponse = new PostDTO.PostDetailResponse("title", "content", 1L, LocalDateTime.now(), 100L, "userName");
        when(postService.findById(anyLong()))
                .thenReturn(postDetailResponse);


        // when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", id)
                        .content(objectMapper.writeValueAsString(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("statusCode").value(200))
                .andExpect(jsonPath("message").value(ResponseMessage.FINDED_ONE))
                .andExpect(jsonPath("serverDateTime").exists())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.title").value("title"))
                .andExpect(jsonPath("data.content").value("content"))
                .andExpect(jsonPath("data.postId").value(1L))
                .andExpect(jsonPath("data.createdAt").exists())
                .andExpect(jsonPath("data.userId").value(100L))
                .andExpect(jsonPath("data.userName").value("userName"))
                .andDo(print())
                .andDo(document("find-post-one",
                        pathParameters(
                                parameterWithName("id").description("게시글 id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 HTTP 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답 생성 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터 - 게시글 관련 DTO"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성일자 및 시간"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("게시글 글쓴이 id"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("게시글 글쓴이 이름")
                        )
                ));

        // then
        verify(postService).findById(anyLong());
    }

    //toDo : 수정 테스트
    @Test
    @DisplayName("게시글 ID로 게시글 수정 시 제목과 세부내용을 바꿀 수 있고 데이터로 수정된 게시글 ID가 반환된다.")
    void updateCallTest() throws Exception {
        // given
        Long id = 1L;
        PostDTO.UpdateRequest request = new PostDTO.UpdateRequest("title", "content");
        when(postService.updatePost(anyLong(), any(PostDTO.UpdateRequest.class)))
                .thenReturn(id);
        // when
        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{id}", id)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("statusCode").value(200))
                .andExpect(jsonPath("data").value(1L))
                .andExpect(jsonPath("message").value(ResponseMessage.UPDATED))
                .andDo(print())
                .andDo(document("post-update",
                        pathParameters(
                                parameterWithName("id").description("게시글 id")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 HTTP 상태 코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("응답 결과 - 생성된 게시글 Id"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답 생성 시간")
                        ))
                );
        // then
        verify(postService).updatePost(anyLong(), any(PostDTO.UpdateRequest.class));
    }

    //toDo : 페이징 조회 테스트
    @Test
    @DisplayName("현재 페이지 정보와 한 페이지 당 게시글 수를 입력 시 페이징된 게시글이 반환되며 게시글의 정보는 게시글 ID, 제목, 생성일자가 반환된다.")
    void getAllCallTest() throws Exception {
        // given
        PostDTO.PageRequest pageRequest = new PostDTO.PageRequest(0, 5, Sort.Direction.DESC);
        Pageable request = PageRequest.of(0, 5);
        List<PostDTO.PostsResponse> postDtos = List.of(
                new PostDTO.PostsResponse("title1", 1L, LocalDateTime.now()),
                new PostDTO.PostsResponse("title2", 2L, LocalDateTime.now()),
                new PostDTO.PostsResponse("title3", 3L, LocalDateTime.now())
        );
        Page<PostDTO.PostsResponse> response = new PageImpl(postDtos, request, 3);
        when(postService.findAll(any(Pageable.class)))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/posts")
                        .content(objectMapper.writeValueAsString(pageRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("statusCode").value(200))
                .andExpect(jsonPath("message").value(ResponseMessage.FINDED_ALL))
                .andExpect(jsonPath("serverDateTime").exists())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.content[0].postId").value(postDtos.get(0).postId()))
                .andExpect(jsonPath("data.content[0].title").value(postDtos.get(0).title()))
                .andExpect(jsonPath("data.content[0].createdAt").exists())
                .andExpect(jsonPath("data.content[1].postId").value(postDtos.get(1).postId()))
                .andExpect(jsonPath("data.content[1].title").value(postDtos.get(1).title()))
                .andExpect(jsonPath("data.content[1].createdAt").exists())
                .andExpect(jsonPath("data.content[2].postId").value(postDtos.get(2).postId()))
                .andExpect(jsonPath("data.content[2].title").value(postDtos.get(2).title()))
                .andExpect(jsonPath("data.content[2].createdAt").exists())
                .andDo(print())
                .andDo(document("find-post-all",
                        requestFields(
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("현재 페이지 위치"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("한 페이지의 게시물 수"),
                                fieldWithPath("direction").type(JsonFieldType.STRING).description("정렬 방법")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("응답 HTTP 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답 생성 시간"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터 - 게시글 관련 페이지 정보(게시글 정보 집합)"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("응답 데이터 - 게시글 정보"),
                                fieldWithPath("data.content.[].postId").type(JsonFieldType.NUMBER).description("응답 데이터 - 게시글 id"),
                                fieldWithPath("data.content.[].title").type(JsonFieldType.STRING).description("응답 데이터 - 게시글 제목"),
                                fieldWithPath("data.content.[].createdAt").type(JsonFieldType.STRING).description("응답 데이터 - 게시글 생성시간"),

                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 되었는지 여부"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 안되었는지 여부"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("데이터가 비었는지 여부"),

                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("한 페이지당 조회할 데이터 개수"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("몇번째 데이터인지 (0부터 시작)"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 정보를 포함하는지 여부"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 정보를 안포함하는지 여부"),

                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지인지 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 개수"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("테이블 총 데이터 개수"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지인지 여부"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("요청 페이지에서 조회된 데이터 개수"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("한 페이지당 조회할 데이터 개수"),

                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 됐는지 여부"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 안됐는지 여부"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("데이터가 비었는지 여부"),

                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("데이터가 비었는지 여부")
                        )
                ));

        // then
        verify(postService).findAll(any(Pageable.class));
    }
}