package com.assignment.board.controller;

import com.assignment.board.dto.DtoConverter;
import com.assignment.board.dto.PostDto;
import com.assignment.board.dto.UserDto;
import com.assignment.board.repository.PostRepository;
import com.assignment.board.service.PostService;
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

import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DtoConverter dtoConverter;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void save_test() {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("테스트하기")
                .content("서비스 테스트 입니다.")
                .userDto(
                        UserDto.builder()
                                .name("NaSangwon")
                                .age(28)
                                .hobby("빈둥거리기")
                                .build()
                )
                .build();

        Long savedId = postService.save(postDto);

    }


    @Test
    void saveCallTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("테스트하기")
                .content("서비스 테스트 입니다.")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("NaSangwon")
                                .age(28)
                                .hobby("빈둥거리기")
                                .build()
                )
                .build();

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))) //Json 형태의 String으로 orderDto를 변환
                .andExpect(status().isOk()) // 200
                .andDo(print())
                .andDo(document("post-save",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")


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
        mockMvc.perform(get("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //200응답을 기대
                .andDo(print())
                .andDo(document("post-getOne",
                        preprocessRequest(prettyPrint()),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),

                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")

                        )
                ));
    }

    @Test
    void update() throws Exception {

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("수정하기")
                .content("수정하기 테스트 입니다.")
                .userDto(
                        UserDto.builder()
                                .name("NaSangwon")
                                .age(28)
                                .hobby("빈둥거리기")
                                .build()
                )
                .build();

        mockMvc.perform(put("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0)) //페이지랑 사이즈
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
