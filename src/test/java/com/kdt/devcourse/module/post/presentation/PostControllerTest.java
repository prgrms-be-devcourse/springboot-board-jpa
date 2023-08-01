package com.kdt.devcourse.module.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.devcourse.module.post.application.PostService;
import com.kdt.devcourse.module.post.presentation.dto.PostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {
    private static final String BASE_URL = "/posts";

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String title = "title";
    private String content = "content";
    private PostRequest postRequest = new PostRequest(title, content);

    @Test
    @DisplayName("생성 요청이 오면 201 응답과 함께 서비스를 한번만 호출해야 한다.")
    void createPost_Test() throws Exception {
        // given

        // when
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.payload").exists())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("payload").type(JsonFieldType.VARIES).description("생성된 게시글 URI")
                        )
                ));

        // then
        then(postService).should(times(1)).create(anyString(), anyString());
    }

    @Test
    @DisplayName("수정 요청을 하면 200응답과 함께 서비스가 한번만 호출되어야 한다.")
    void updatePost_Test() throws Exception {
        // given
        Long id = 1L;

        // when
        mockMvc.perform(put(BASE_URL + "/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        pathParameters(
                                parameterWithName("id").description("게시글 번호")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        )
                ));

        // then
        then(postService).should(times(1)).update(any(), any(), any());
    }

    @Test
    @DisplayName("페이지 요청을 하면 200 응답과 호출이 잘 이루어져야 한다.")
    void findAll_Test() throws Exception {
        // given
        willReturn(Page.empty()).given(postService).findAll(any());

        // when
        mockMvc.perform(get(BASE_URL)
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.payload").isNotEmpty())
                .andDo(print())
                .andDo(document("post-pagination",
                        queryParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지당 게시글 수")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("payload").type(JsonFieldType.VARIES).description("응답 데이터"),
                                fieldWithPath("payload.content").type(JsonFieldType.ARRAY).description("조회된 게시글"),
                                fieldWithPath("payload.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("payload.totalElements").type(JsonFieldType.NUMBER).description("전체 게시글 수"),
                                fieldWithPath("payload.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("payload.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("payload.size").type(JsonFieldType.NUMBER).description("페이지 내 게시글 개수"),
                                fieldWithPath("payload.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지에서 조회된 아이템 수"),
                                fieldWithPath("payload.sort.empty").type(JsonFieldType.BOOLEAN).description("비어있는지 여부"),
                                fieldWithPath("payload.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                fieldWithPath("payload.sort.unsorted").ignored(),
                                fieldWithPath("payload.pageable").ignored(),
                                fieldWithPath("payload.number").ignored(),
                                fieldWithPath("payload.empty").ignored()
                        )
                ));

        // then
        then(postService).should(times(1)).findAll(any());
    }
}
