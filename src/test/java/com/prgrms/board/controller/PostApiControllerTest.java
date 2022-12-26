package com.prgrms.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.dto.request.MemberCreateDto;
import com.prgrms.board.dto.request.PostCreateDto;
import com.prgrms.board.dto.request.PostUpdateDto;
import com.prgrms.board.service.MemberService;
import com.prgrms.board.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Slf4j
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class PostApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;

    @Autowired
    private PostService postService;


    private Long savedMemberId;
    private Long savedPostId;
    private PostCreateDto postCreateDto;

    @BeforeEach
    void setup() {
        MemberCreateDto createDto = MemberCreateDto.builder()
                .name("member1")
                .age(26)
                .hobby("youTube")
                .build();

        savedMemberId = memberService.join(createDto);

        postCreateDto = PostCreateDto.builder()
                .title("this is title")
                .content("this is content")
                .writerId(savedMemberId)
                .build();

        savedPostId = postService.register(postCreateDto);
    }

    @Test
    void 게시글_저장() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("writerId").description("작성자 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 PK"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }


    @Test
    void 게시글_단건_조회() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", savedPostId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(postCreateDto.getTitle()))
                .andExpect(jsonPath("$.data.content").value(postCreateDto.getContent()))
                .andExpect(jsonPath("$.data.writerId").value(savedMemberId))
                .andDo(print())
                .andDo(document("post-findOne",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 PK"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.writerId").type(JsonFieldType.NUMBER).description("작성자 PK"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    void 게시글_수정() throws Exception {
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .title("change title")
                .content("change content")
                .writerId(savedMemberId)
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 PK"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    void 게시글_페이징_조회() throws Exception {
        int cursorId = 10;
        int size = 10;

        mockMvc.perform(get("/api/v1/posts")
                        .param("cursorId", String.valueOf(cursorId))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.postDtoList[0].title").value(postCreateDto.getTitle()))
                .andExpect(jsonPath("$.data.postDtoList[0].content").value(postCreateDto.getContent()))
                .andExpect(jsonPath("$.data.postDtoList[0].writerId").value(postCreateDto.getWriterId()))
                .andDo(print())
                .andDo(
                        document("post-paging",
                                requestParameters(
                                        parameterWithName("cursorId").optional().description("cursorId"),
                                        parameterWithName("size").optional().description("size")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                                        fieldWithPath("data.postDtoList[].id").type(JsonFieldType.NUMBER).description("게시글 PK"),
                                        fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("hasNext"),
                                        fieldWithPath("data.postDtoList[].title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.postDtoList[].content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("data.postDtoList[].writerId").type(JsonFieldType.NUMBER).description("작성자 PK")
                                )
                        )
                );
    }
}