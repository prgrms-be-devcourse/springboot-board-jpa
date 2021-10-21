package com.example.board.application.post.controller;

import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.post.service.PostService;
import com.example.board.domain.user.dto.UserDto;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    UserDto userDto;

    @BeforeEach
    void setup() {
        userDto = UserDto.builder()
                .name("이름")
                .age(20)
                .hobby("취미")
                .build();
    }

    @Test
    void save() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("제목")
                .content("내용입니다.")
                .userDto(userDto)
                .build();

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )
                ));
    }

    @Test
    void update() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("제목")
                .content("내용입니다.")
                .userDto(userDto)
                .build();

        Long saveId = postService.save(postDto);

        PostDto findPostDto = postService.findOne(saveId);
        findPostDto.setTitle("제목 변경");

        mockMvc.perform(post("/posts/{id}", findPostDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(findPostDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )
                ));
    }

    @Test
    void getOne() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("제목")
                .content("내용입니다.")
                .userDto(userDto)
                .build();

        Long saveId = postService.save(postDto);

        mockMvc.perform(get("/posts/{id}", saveId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-one",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("data.id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("data.title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("data.content"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("data.userDto"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("data.userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("data.userDto.name"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("data.userDto.age"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("data.userDto.hobby"),
                                fieldWithPath("data.userDto.createdAt").type(JsonFieldType.STRING).description("data.userDto.createdAt"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("data.createdAt"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )
                ));
    }

    @Test
    void getAll() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("제목")
                .content("내용입니다.")
                .userDto(userDto)
                .build();

        Long saveId = postService.save(postDto);

        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-all",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("data.content[]"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("data.content[].id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("data.content[].title"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("data.content[].content"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("data.content[].createdAt"),
                                fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("data.content[].userDto"),
                                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("data.content[].userDto.id"),
                                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("data.content[].userDto.name"),
                                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("data.content[].userDto.age"),
                                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("data.content[].userDto.hobby"),
                                fieldWithPath("data.content[].userDto.createdAt").type(JsonFieldType.STRING).description("data.content[].userDto.createdAt"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )
                ));;
    }
}