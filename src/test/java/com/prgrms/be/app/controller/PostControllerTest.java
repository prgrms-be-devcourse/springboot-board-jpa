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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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

    @Test
    @DisplayName("게시글 생성 성공 시 생성된 게시글 id가 반환된다.")
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
                .andExpect(jsonPath("statusCode").value(201))
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

    @Test
    @DisplayName("게시글 ID로 게시글 수정 시 제목과 세부내용을 바꿀 수 있고 데이터로 수정된 게시글 ID가 반환된다.")
    void updateCallTest() throws Exception {
        // given
        Long id = 1L;
        PostDTO.UpdateRequest request = new PostDTO.UpdateRequest("title", "content");
        when(postService.updatePost(anyLong(), any(PostDTO.UpdateRequest.class)))
                .thenReturn(id);
        // when
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/posts/{id}", id)
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

    @Test
    @DisplayName("현재 페이지 정보와 한 페이지 당 게시글 수를 입력 시 페이징된 게시글이 반환되며 게시글의 정보는 게시글 ID, 제목, 생성일자가 반환된다.")
    void getAllCallTest() throws Exception {
        // given
        PostDTO.PostPageRequest postPageRequest = new PostDTO.PostPageRequest(0, 5, Sort.Direction.DESC);
        Pageable request = PageRequest.of(0, 5);
        List<PostDTO.PostDetailResponse> postDtos = List.of(
                new PostDTO.PostDetailResponse("title1", "content1", 1L, LocalDateTime.now(), 1L, "user1"),
                new PostDTO.PostDetailResponse("title2", "content2", 2L, LocalDateTime.now(), 2L, "user2"),
                new PostDTO.PostDetailResponse("title3", "content3", 3L, LocalDateTime.now(), 3L, "user3")
        );
        PostDTO.PostsResponse response = new PostDTO.PostsResponse(postDtos, 1, true);
        when(postService.findAll(any(Pageable.class)))
                .thenReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(get("/posts")
                        .content(objectMapper.writeValueAsString(postPageRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("statusCode").value(200))
                .andExpect(jsonPath("message").value(ResponseMessage.FINDED_ALL))
                .andExpect(jsonPath("serverDateTime").exists())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.totalPages").value(1))
                .andExpect(jsonPath("data.hasNext").value(true))
                .andExpect(jsonPath("data.posts").exists())
                .andExpect(jsonPath("data.posts[0].postId").value(postDtos.get(0).postId()))
                .andExpect(jsonPath("data.posts[0].title").value(postDtos.get(0).title()))
                .andExpect(jsonPath("data.posts[0].content").value(postDtos.get(0).content()))
                .andExpect(jsonPath("data.posts[0].createdAt").exists())
                .andExpect(jsonPath("data.posts[0].userId").value(postDtos.get(0).userId()))
                .andExpect(jsonPath("data.posts[0].userName").value(postDtos.get(0).userName()))
                .andExpect(jsonPath("data.posts[1].postId").value(postDtos.get(1).postId()))
                .andExpect(jsonPath("data.posts[1].title").value(postDtos.get(1).title()))
                .andExpect(jsonPath("data.posts[1].content").value(postDtos.get(1).content()))
                .andExpect(jsonPath("data.posts[1].createdAt").exists())
                .andExpect(jsonPath("data.posts[1].userId").value(postDtos.get(1).userId()))
                .andExpect(jsonPath("data.posts[1].userName").value(postDtos.get(1).userName()))
                .andExpect(jsonPath("data.posts[2].postId").value(postDtos.get(2).postId()))
                .andExpect(jsonPath("data.posts[2].title").value(postDtos.get(2).title()))
                .andExpect(jsonPath("data.posts[2].content").value(postDtos.get(2).content()))
                .andExpect(jsonPath("data.posts[2].createdAt").exists())
                .andExpect(jsonPath("data.posts[2].userId").value(postDtos.get(2).userId()))
                .andExpect(jsonPath("data.posts[2].userName").value(postDtos.get(2).userName()))
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
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("응답 데이터 - 게시글 다음 게시글 존재 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("응답 데이터 - 게시글을 포함한 페이지의 개수"),
                                fieldWithPath("data.posts").type(JsonFieldType.ARRAY).description("응답 데이터 - 게시글 정보"),
                                fieldWithPath("data.posts.[].postId").type(JsonFieldType.NUMBER).description("응답 데이터 - 게시글 id"),
                                fieldWithPath("data.posts.[].title").type(JsonFieldType.STRING).description("응답 데이터 - 게시글 제목"),
                                fieldWithPath("data.posts.[].content").type(JsonFieldType.STRING).description("응답 데이터 - 게시글 본문내용"),
                                fieldWithPath("data.posts.[].createdAt").type(JsonFieldType.STRING).description("응답 데이터 - 게시글 생성시간"),
                                fieldWithPath("data.posts.[].userId").type(JsonFieldType.NUMBER).description("응답 데이터 - 게시글 작성자 id"),
                                fieldWithPath("data.posts.[].userName").type(JsonFieldType.STRING).description("응답 데이터 - 게시글 작성자 이름")
                        )
                ));

        // then
        verify(postService).findAll(any(Pageable.class));
    }
}