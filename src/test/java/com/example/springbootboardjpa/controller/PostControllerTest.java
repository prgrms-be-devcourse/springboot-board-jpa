package com.example.springbootboardjpa.controller;


import com.example.springbootboardjpa.converter.PostConverter;
import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.dto.UserDto;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import com.example.springbootboardjpa.repoistory.PostJpaRepository;
import com.example.springbootboardjpa.repoistory.UserJpaRepository;
import com.example.springbootboardjpa.service.DefaultPostService;
import com.example.springbootboardjpa.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PostJpaRepository postJpaRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("이름", 99);
        user = userJpaRepository.save(user);
    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void createPost() throws Exception {
        // Given
        UserDto.Info userDto = UserDto.Info.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .build();
        PostDTO.Save postDto = new PostDTO.Save("제목", "내용", userDto);

        // When // Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void nullTitleCreateFailTest() throws Exception {
        // Given
        UserDto.Info userDto = UserDto.Info.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .build();
        PostDTO.Save postDto = new PostDTO.Save(null, "내용", userDto);

        // When // Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void nullContextCreateFailTest() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void nullUserCreateFailTest() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void nullUserIdCreateFailTest() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void updatePost() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void nullTitleUpdateFailTest() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void nullContextUpdateFailTest() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void getPostById() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void getFailById() {

    }

    @Test
    @DisplayName("post를 정상 생성한다.")
    public void getAllByPage() {

    }
}