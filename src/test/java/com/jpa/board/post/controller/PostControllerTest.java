package com.jpa.board.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.board.post.dto.PostCreateDto;
import com.jpa.board.post.dto.PostUpdateDto;
import com.jpa.board.post.repository.PostRepository;
import com.jpa.board.post.service.PostService;
import com.jpa.board.user.User;
import com.jpa.board.user.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private  UserRepository userRepository;

    @BeforeEach
    void setUp() throws NotFoundException {
        userRepository.save(User.builder()
                .age(30)
                .name("미니미")
                .hobby("독서")
                .build());
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .title("controller")
                .content("controllerTest")
                .userId(1L)
                .build();

        postService.save(postCreateDto);
    }

    @Test
    void save() throws Exception {
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .title("controller")
                .content("controllerTest")
                .userId(1L)
                .build();

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userID")
                        )
                ));
    }

    @Test
    void getAll() throws Exception{
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getAll-response",
                    preprocessResponse(prettyPrint()),
                    requestParameters(
                            parameterWithName("page").description("The page offset (시작점)"),
                            parameterWithName("size").description("The page size (한번에 보여줄 게시글 갯수)")
                    ),
                    responseFields(
                            beneathPath("content"),
                            subsectionWithPath("content").type(JsonFieldType.STRING).description("제목"),
                            fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                            fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                            fieldWithPath("createdBy").type(JsonFieldType.STRING).description("작성자")
                    )
                )
        );
    }

    @Test
    void getOne() throws Exception{
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "getone",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("작성자")
                        ),
                        pathParameters(parameterWithName("id").description("게시글 번호"))
                ));
    }

    @Test
    void update() throws Exception {
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .title("수정")
                .content("수정이 무사히 되었군요")
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        pathParameters(parameterWithName("id").description("게시글 번호")),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        )
                ));
    }
}