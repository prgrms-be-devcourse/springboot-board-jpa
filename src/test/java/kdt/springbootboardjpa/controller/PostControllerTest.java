package kdt.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdt.springbootboardjpa.controller.request.SavePostRequest;
import kdt.springbootboardjpa.respository.UserRepository;
import kdt.springbootboardjpa.respository.entity.User;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        User user = User.builder()
                .age("32")
                .name("name")
                .hobby("hobby").build();
        User savedUser = userRepository.save(user);

        String title = "title";
        String content = "content";
        Long createdBy = savedUser.getId();
        SavePostRequest request = SavePostRequest.builder()
                .title(title)
                .content(content)
                .createdBy(createdBy)
                .build();

        String url = "/posts";
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("[성공] Post 생성하기")
    void createPost() throws Exception {
        User user = User.builder()
                .age("32")
                .name("name")
                .hobby("hobby").build();
        User savedUser = userRepository.save(user);

        String title = "title";
        String content = "content";
        Long createdBy = savedUser.getId();
        SavePostRequest request = SavePostRequest.builder()
                .title(title)
                .content(content)
                .createdBy(createdBy)
                .build();

        String url = "/posts";
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(title))
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("createdBy").type(JsonFieldType.NUMBER).description("글쓴이")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("createdBy").type(JsonFieldType.NUMBER).description("글쓴이")
                        )));
    }

    @Test
    @DisplayName("[실패] Post 생성하기, User가 존재하지 않는 경우")
    void createPost_fail() throws Exception {
        String title = "title";
        String content = "content";
        Long createdBy = 100L;
        SavePostRequest request = SavePostRequest.builder()
                .title(title)
                .content(content)
                .createdBy(createdBy)
                .build();

        String url = "/posts";
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMsg").value("Can't find User"))
                .andExpect(jsonPath("$.statusCode").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.uriRequested").value(url))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("[성공] Post 수정하기")
    void updatePost() throws Exception {
        String title = "title2";
        String content = "content2";
        Long createdBy = 1L;
        SavePostRequest request = SavePostRequest.builder()
                .title(title)
                .content(content)
                .createdBy(createdBy)
                .build();
        Long postId = 1L;

        String url = "/posts/" + postId;
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.createdBy").value(createdBy))
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("createdBy").type(JsonFieldType.NUMBER).description("글쓴이")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("createdBy").type(JsonFieldType.NUMBER).description("글쓴이")
                        )));
    }

    @Test
    @DisplayName("[실패] Post 수정하기, Post가 존재하지 않는 경우")
    void updatePost_fail() throws Exception {
        String title = "title2";
        String content = "content2";
        Long createdBy = 1L;
        SavePostRequest request = SavePostRequest.builder()
                .title(title)
                .content(content)
                .createdBy(createdBy)
                .build();
        Long postId = 100L;

        String url = "/posts/" + postId;
        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .content(body)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMsg").value("Can't find Post"))
                .andExpect(jsonPath("$.statusCode").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.uriRequested").value(url))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("[성공] Post 조회하기")
    void getPost() throws Exception {
        Long postId = 1L;
        String url = "/posts/" + postId;

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andDo(print())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("createdBy").type(JsonFieldType.NUMBER).description("글쓴이")
                        )));
    }

    @Test
    @DisplayName("[실패] Post 조회하기, Post가 존재하지 않는 경우")
    void getPost_fail() throws Exception {
        Long postId = 100L;
        String url = "/posts/" + postId;

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMsg").value("Can't find Post"))
                .andExpect(jsonPath("$.statusCode").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.uriRequested").value(url))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("[성공] Post 전부 조회하기")
    void getAllPost() throws Exception {
        String url = "/posts";

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-all",
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("[].createdBy").type(JsonFieldType.NUMBER).description("글쓴이")
                        )));
    }
}
