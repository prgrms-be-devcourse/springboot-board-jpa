package com.board.springbootboard.domain.posts.controller;
import com.board.springbootboard.domain.posts.dto.PostsSaveRequestDto;
import com.board.springbootboard.domain.posts.service.PostsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// rest docs
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class PostsRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostsService postsService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("REST DOC TEST")
    public void restdoc() throws Exception {

        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("sds1zzang")
                .build();

        mockMvc.perform(post("/api/v1/posts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk()) // 200
                .andDo(print())
                .andDo(document("posts-save",
                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("TITLE"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("CONTENT"),
                                fieldWithPath("author").type(JsonFieldType.STRING).description("AUTHOR")
                        ),
                        responseFields(
                                fieldWithPath("Status").type(JsonFieldType.NUMBER).description("상태코드")
//                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
//                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

    }


}
