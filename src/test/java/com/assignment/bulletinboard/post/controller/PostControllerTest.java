package com.assignment.bulletinboard.post.controller;

import com.assignment.bulletinboard.post.dto.PostDto;
import com.assignment.bulletinboard.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;


@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    Long postId = 1L;

    @BeforeEach
    void setUp() {
        //Given
        PostDto postDto = PostDto.builder()
                .id(postId)
                .title("찬호팍의 스피치 강연")
                .content("스피치 강연을 하게 되어서 영광입니다. 스피치라는 것은 처음엔 굉장히 어렵지만 몇가지 요령을 알게 되면 쉬운거거든요. 제가 이 스피치의 중요성을 깨닫게 된게 제가 1994년 LA에 있었을 때...")
                .build();

        //When
        Long id = postService.save(postDto);

        //Then
        assertThat(postId).isEqualTo(id);
    }

    @Test
    void saveCallTest() throws Exception {
        //Given
        PostDto postDto = PostDto.builder()
                .id(2L)
                .title("찬호팍의 스피치 강연")
                .content("스피치 강연을 하게 되어서 영광입니다. 스피치라는 것은 처음엔 굉장히 어렵지만 몇가지 요령을 알게 되면 쉬운거거든요. 제가 이 스피치의 중요성을 깨닫게 된게 제가 1994년 LA에 있었을 때...")
                .build();

        //When //Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("PostID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("PostTitle"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("PostContent"),
                                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("PostCreatedBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.NULL).description("PostCreatedAt"),
                                fieldWithPath("userDto").type(JsonFieldType.NULL).description("PostUserDto")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")

                        )
                ));
    }

    @Test
    void getOne() throws Exception {
        //Given
        mockMvc.perform(get("/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        //Given
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}