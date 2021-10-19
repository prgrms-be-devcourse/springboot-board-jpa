package com.kdt.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.kdt.post.dto.PostControlRequestDto;
import com.kdt.post.dto.PostDto;
import com.kdt.post.service.PostService;
import com.kdt.user.dto.UserDto;
import com.kdt.user.repository.UserRepository;
import com.kdt.user.service.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private Long userId;

    private UserDto userDto;

    private PostDto postDto;

    @BeforeEach
    void setUp() throws NotFoundException {
        userDto = UserDto.builder()
                .name("son")
                .age(30)
                .hobby("soccer")
                .build();

        userId = userService.save(userDto);

        postDto = PostDto.builder()
                .title("test-title")
                .content("this is a sample post")
                .build();

        PostControlRequestDto saveRequestDto = PostControlRequestDto.builder()
                .userId(userId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        postService.save(saveRequestDto);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("고객 추가 요청을 정상적으로 처리하는지 확인한다.")
    void saveTest() throws Exception {
        //Given
        UserDto userDto2 = UserDto.builder()
                .name("jenny")
                .age(19)
                .hobby("shopping")
                .build();

        //When
        //Then
        mockMvc.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDto2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("제약 조건에 맞지 않은 고객 추가 요청을 받으면 Bad Request 응답을 반환한다.")
    void saveTestUsingInvalidData() throws Exception {
        //Given
        UserDto userDto2 = UserDto.builder()
                .name(null)
                .age(0)
                .hobby("shopping")
                .build();

        UserDto userDto3 = UserDto.builder()
                .name("a123456789101112131415161819")
                .age(0)
                .hobby("shopping")
                .build();

        //When
        //Then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto2)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto3)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("고객 수정 요청을 정상적으로 처리하는지 확인한다.")
    void updateTest() throws Exception {
        //Given
        log.info("before updating : {}", userDto.toString());
        userDto.setName("jun");
        userDto.setHobby("cooking");

        //When
        //Then
        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("유효하지 않은 고객의 수정을 요청하면 Not Found 응답을 반환한다.")
    void updateInvalidUserTest() throws Exception {
        //Given
        UserDto updateUserDto = UserDto.builder()
                .id(userId)
                .name(userDto.getName())
                .age(30)
                .hobby("updated - cooking")
                .build();

        //When
        //Then
        mockMvc.perform(put("/users/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("제약조건에 맞지 않는 고객 정보의 수정을 요청하면 Bad Request 응답을 반환한다.")
    void updateTestUsingInvalidData() throws Exception {
        //Given
        UserDto updateUserDto = UserDto.builder()
                .id(userId)
                .name("1111111")
                .age(0)
                .hobby(userDto.getHobby())
                .build();

        //When
        //Then
        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("고객 조회 요청을 정상적으로 처리하는지 확인한다.")
    void getTest() throws Exception {
        //Given
        //When
        //Then
        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseData = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("data").toString();
        log.info("requestId : {} => responseData : {}", userId, responseData);
    }

    @Test
    @DisplayName("유효하지 않은 고객의 조회를 요청하면 Not Found 응답을 반환한다.")
    void getInvalidUserTest() throws Exception {
        //Given
        //When
        //Then
        MvcResult mvcResult = mockMvc.perform(get("/users/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        String responseData = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("data").toString();
        log.info("requestId : {} => responseData : {}", userId, responseData);
    }

    @Test
    @DisplayName("고객 삭제 요청을 정상적으로 처리하는지 확인한다.")
    void deleteTest() throws Exception {
        //Given
        //When
        //Then
        mockMvc.perform(delete("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("유효하지 않은 고객의 삭제를 요청하면 Not Found 응답을 반환한다.")
    void deleteInvalidUserTest() throws Exception {
        //Given
        //When
        //Then
        mockMvc.perform(delete("/users/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}