package com.prgrms.hyuk;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostUpdateRequest;
import com.prgrms.hyuk.dto.UserDto;
import com.prgrms.hyuk.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    private Long savedPostId;

    @BeforeEach
    void setUp() {
        var postCreateRequest = new PostCreateRequest(
            "this is title...",
            "this is content...",
            new UserDto(
                "pang",
                26,
                "soccer"
            )
        );

        savedPostId = postService.create(postCreateRequest);
    }

    @Test
    @DisplayName("게시글 저장 API")
    void testPostSaveAPI() throws Exception {
        //given
        var postCreateRequest = new PostCreateRequest(
            "this is title...",
            "this is content...",
            new UserDto(
                "pang",
                26,
                "soccer"
            )
        );

        //when
        //then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andDo(document("post-save",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                    fieldWithPath("userDto.name").type(JsonFieldType.STRING)
                        .description("userDto.name"),
                    fieldWithPath("userDto.age").type(JsonFieldType.NUMBER)
                        .description("userDto.age"),
                    fieldWithPath("userDto.hobby").type(JsonFieldType.STRING)
                        .description("userDto.hobby")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간")
                )
            ));
    }

    @Test
    @DisplayName("게시글 단건 조회 API")
    void testPostGetOne() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/posts/{id}", String.valueOf(savedPostId))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.serverDatetime").exists())
            .andDo(document("post-getOne",
                    responseFields(
                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("게시글 정보"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                        fieldWithPath("data.userName").type(JsonFieldType.STRING)
                            .description("게시글 작성자"),
                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간")
                    )
                )
            );
    }

    @Test
    @DisplayName("게시글 전체 조회 API")
    void testPostGetAll() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/posts")
                .param("offset", String.valueOf(0))
                .param("limit", String.valueOf(5))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("200"))
            .andExpect(jsonPath("$.data", hasSize(1)))
            .andExpect(jsonPath("$.serverDatetime").exists())
            .andDo(document("post-getAll",
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("게시글 정보들"),
                    fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("data[].content").type(JsonFieldType.STRING)
                        .description("게시글 내용"),
                    fieldWithPath("data[].userName").type(JsonFieldType.STRING)
                        .description("게시글 작성자"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간")
                )
            ));
    }

    @Test
    @DisplayName("게시글 업데이트 API")
    void testPostUpdate() throws Exception {
        //given
        var postUpdateRequest = new PostUpdateRequest(
            "this is updated title...",
            "this is updated content..."
        );

        //when
        //then
        mockMvc.perform(patch("/posts/{id}", String.valueOf(savedPostId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequest)))
            .andExpect(jsonPath("$.statusCode").value(String.valueOf(200)))
            .andExpect(jsonPath("$.data").value(String.valueOf(savedPostId)))
            .andExpect(jsonPath("$.serverDatetime").exists())
            .andDo(document("post-update",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("업데이트 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("업데이트 내용")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("업데이트 게시글 아이디"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간")
                )
            ));
    }
}
