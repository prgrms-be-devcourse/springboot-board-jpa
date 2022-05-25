package com.prgrms.springbootboardjpa.post.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.springbootboardjpa.post.dto.PostConverter;
import com.prgrms.springbootboardjpa.post.dto.PostDto;
import com.prgrms.springbootboardjpa.post.dto.PostResponse;
import com.prgrms.springbootboardjpa.post.entity.Post;
import com.prgrms.springbootboardjpa.post.repository.PostRepository;
import com.prgrms.springbootboardjpa.post.service.PostService;
import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.prgrms.springbootboardjpa.user.repository.UserRepository;
import com.prgrms.springbootboardjpa.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostConverter postConverter;

    User user;
    UserDto userDto;
    UserResponse userResponse;
    Post post;
    PostDto postDto;
    PostResponse postResponse;
    TypeReference<List<PostResponse>> typeReference;

    private final String PATH = "/api/v1/posts";

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();

        userDto = UserDto.builder()
                .nickName("Nickname")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test@gmail.com")
                .build();

        userResponse = userService.save(userDto);

        user = userRepository.findById(userResponse.getId()).get();

        postDto = PostDto.builder()
                .title("First Title")
                .content("First content")
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        postResponse = postService.save(postDto);

        post = postRepository.findById(postResponse.getId()).get();

        setTypeReference();
    }

    @AfterEach
    void clearUp(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void save() throws Exception{
        //Given
        PostDto givenPostDto = PostDto.builder()
                .title("New Title")
                .content("New Content")
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        //When
        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenPostDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(givenPostDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(givenPostDto.getContent()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userNickName").value(user.getNickName()))
                .andDo(document("post-save",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("id").ignored()
                                ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userNickName").type(JsonFieldType.STRING).description("userNickName")
                        )
                )
                );
    }

    @Test
    void allPosts() throws Exception{
        //Given
        List<PostResponse> savedResponseList = new ArrayList<>();
        PostDto givenPostDto = PostDto.builder()
                .title("New Title")
                .content("New Content")
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        savedResponseList.add(postResponse);
        savedResponseList.add(postService.save(givenPostDto));

        //When
        MvcResult result = mockMvc.perform(get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-getAll",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("[].userNickName").type(JsonFieldType.STRING).description("userNickName")
                        )
                        )
                ).andReturn();

        List<PostResponse> postResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);

        //Then
        assertThat(postResponseList).usingRecursiveFieldByFieldElementComparator().isEqualTo(savedResponseList);
    }

    @Test
    void allPostsWithPage() throws Exception{
        //Given
        List<PostResponse> savedResponseList = new ArrayList<>();
        PostDto givenPostDto = PostDto.builder()
                .title("New Title")
                .content("New Content")
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        savedResponseList.add(postService.save(givenPostDto));

        //When
        MvcResult result = mockMvc.perform(get(PATH)
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-getAllWithPage",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("[].userNickName").type(JsonFieldType.STRING).description("userNickName")
                        )
                        )
                ).andReturn();

        List<PostResponse> postResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);

        //Then
        assertThat(postResponseList).usingRecursiveFieldByFieldElementComparator().isEqualTo(savedResponseList);
    }

    @Test
    void update() throws Exception{
        //Given
        postDto.setTitle("update title");
        postDto.setContent("update context");

        //When
        mockMvc.perform(put(PATH + "/" + String.valueOf(post.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(postDto.getContent()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userNickName").value(user.getNickName()))
                .andDo(document("post-update",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("id").ignored()
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userNickName").type(JsonFieldType.STRING).description("userNickName")
                        )
                        )
                );
    }

    private void setTypeReference() {
        typeReference = new TypeReference<List<PostResponse>>() {
            @Override
            public Type getType() {
                return super.getType();
            }

            @Override
            public int compareTo(TypeReference<List<PostResponse>> o) {
                return super.compareTo(o);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }

        };
    }
}