package com.kdt.boardMission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.boardMission.dto.CreatePostDto;
import com.kdt.boardMission.dto.PostDto;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.PostRepository;
import com.kdt.boardMission.repository.UserRepository;
import com.kdt.boardMission.service.PostService;
import com.kdt.boardMission.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        userDto = UserDto.builder()
                .name("name")
                .age(10)
                .hobby("hobby")
                .build();
        long userId = userService.saveUser(userDto);
        userDto.setId(userId);
    }

    UserDto userDto;

    @Test
    @DisplayName("게시글 작성")
    public void doPostTest() throws Exception {

        //given
        PostDto postDto = PostDto.builder()
                .title("title")
                .content("content")
                .build();

        CreatePostDto createPostDto = new CreatePostDto(userDto, postDto);

        //when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createPostDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),

                                fieldWithPath("postDto").type(JsonFieldType.OBJECT).description("postDto"),
                                fieldWithPath("postDto.id").type(JsonFieldType.NUMBER).description("postDto.id"),
                                fieldWithPath("postDto.title").type(JsonFieldType.STRING).description("postDto.title"),
                                fieldWithPath("postDto.content").type(JsonFieldType.STRING).description("postDto.content"),
                                fieldWithPath("postDto.user").type(JsonFieldType.NULL).description("postDto.user")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

}