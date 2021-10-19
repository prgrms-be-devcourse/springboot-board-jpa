package com.kdt.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.kdt.post.dto.PostControlRequestDto;
import com.kdt.post.dto.PostDto;
import com.kdt.post.repository.PostRepository;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class PostControllerTest {

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

    @Autowired
    private PostRepository postRepository;

    private Long userId;

    private Long postId;

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

        postId = postService.save(saveRequestDto);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물 추가 요청을 정상적으로 처리하는지 확인한다.")
    void saveTest() throws Exception {
        //Given
        PostDto postDto2 = PostDto.builder()
                .title("second title")
                .content("this is a second sample post")
                .build();

        PostControlRequestDto saveRequest = PostControlRequestDto.builder()
                .userId(userId)
                .title(postDto2.getTitle())
                .content(postDto2.getContent())
                .build();

        //When
        //Then
        MvcResult mvcResult = mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseData = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("data").toString();
        Long savedPostId = Long.valueOf(responseData);
        PostDto savedPostDto = postService.find(savedPostId);

        assertThat(savedPostDto, samePropertyValuesAs(postDto2, "id", "createdAt", "createdBy", "lastUpdatedAt", "userDto"));
        assertThat(savedPostDto.getUserDto(), samePropertyValuesAs(userDto, "id", "createdAt", "createdBy", "lastUpdatedAt", "postDtos"));
        assertThat(postService.findAll(PageRequest.of(0, 10)).getTotalElements(), is(2L));
        assertThat(userService.find(userId).getPostDtos().size(), is(2));
    }

    @Test
    @DisplayName("제약조건에 맞지 않는 게시물 추가 요청을 받으면 Bad Request 응답을 반환한다.")
    void saveTestUsingInvalidData() throws Exception {
        //Given
        StringBuilder titleExceedMaxLength = new StringBuilder();
        for(int i=0;i<110;i++) {
            titleExceedMaxLength.append("a");
        }

        PostDto postDto2 = PostDto.builder()
                .title(null)
                .content("this is a second sample post")
                .build();

        PostControlRequestDto saveRequest = PostControlRequestDto.builder()
                .userId(userId)
                .title(postDto2.getTitle())
                .content(postDto2.getContent())
                .build();

        PostDto postDto3 = PostDto.builder()
                .title(titleExceedMaxLength.toString())
                .content("title length is 110")
                .build();

        PostControlRequestDto saveRequest2 = PostControlRequestDto.builder()
                .userId(userId)
                .title(postDto3.getTitle())
                .content(postDto3.getContent())
                .build();


        //When
        //Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveRequest2)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        assertThat(postService.findAll(PageRequest.of(0, 10)).getTotalElements(), is(1L));
        assertThat(userService.find(userId).getPostDtos().size(), is(1));
    }

    @Test
    @DisplayName("사용자 정보가 없는 상태에서 게시물 저장 요청을 받으면 Not Found 응답을 반환한다.")
    void saveTestWhenSignOut() throws Exception {
        //Given
        PostDto postDto2 = PostDto.builder()
                .title("second title")
                .content("this is a second sample post")
                .build();

        PostControlRequestDto saveRequest = PostControlRequestDto.builder()
                .userId(null)
                .title(postDto2.getTitle())
                .content(postDto2.getContent())
                .build();

        //When
        //Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveRequest)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
        assertThat(postService.findAll(PageRequest.of(0, 10)).getTotalElements(), is(1L));
        assertThat(userService.find(userId).getPostDtos().size(), is(1));
    }

    @Test
    @DisplayName("게시물 수정 요청을 정상적으로 처리하는지 확인한다.")
    void updateTest() throws Exception {
        //Given
        postDto.setTitle("updated title");
        postDto.setContent("this is a updated post");

        PostControlRequestDto updateRequest = PostControlRequestDto.builder()
                .userId(userId)
                .postId(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        //When
        //Then
        MvcResult mvcResult = mockMvc.perform(put("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseData = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("data").toString();
        Long updatedPostId = Long.valueOf(responseData);
        PostDto updatedPostDto = postService.find(updatedPostId);

        assertThat(updatedPostDto, samePropertyValuesAs(updatedPostDto, "userDto", "lastUpdatedAt"));
        log.info(updatedPostDto.toString());

    }

    @Test
    @DisplayName("사용자 정보가 없는 상태에서 게시물 수정 요청을 받으면 Not Found 응답을 반환한다.")
    void updateTestWhenSignOut() throws Exception {
        //Given
        PostDto updatePostDto = PostDto.builder()
                .id(postId)
                .title("updated title")
                .content("this is a updated post")
                .build();

        PostControlRequestDto updateRequest = PostControlRequestDto.builder()
                .userId(null)
                .postId(postId)
                .title(updatePostDto.getTitle())
                .content(updatePostDto.getContent())
                .build();

        //When
        //Then
        mockMvc.perform(put("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        PostDto savedPostDto = postService.find(postId);
        assertThat(savedPostDto, samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
    }

    @Test
    @DisplayName("제약조건에 맞지 않는 게시물 수정 요청을 받으면 Bad Request 응답을 반환한다.")
    void updateTestUsingInvalidData() throws Exception {
        //Given
        PostDto updatePostDto = PostDto.builder()
                .id(postId)
                .title(null)
                .content(null)
                .build();

        PostControlRequestDto updateRequest = PostControlRequestDto.builder()
                .userId(userId)
                .postId(postId)
                .title(updatePostDto.getTitle())
                .content(updatePostDto.getContent())
                .build();

        //When
        //Then
        mockMvc.perform(put("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        PostDto savedPostDto = postService.find(postId);
        assertThat(savedPostDto, samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));

    }

    @Test
    @DisplayName("게시물을 작성하지 않은 사용자가 게시물 수정을 요청하면 Not Found 응답을 반환한다.")
    void updatePostByInvalidUserTest() throws Exception {
        //Given
        UserDto userDto2 = UserDto.builder()
                .name("jun")
                .age(10)
                .hobby("planting")
                .build();

        Long userId2 = userService.save(userDto);

        PostDto postDto2 = PostDto.builder()
                .title("second-title")
                .content("this is a second sample post")
                .build();

        PostControlRequestDto saveRequestDto = PostControlRequestDto.builder()
                .userId(userId2)
                .title(postDto2.getTitle())
                .content(postDto2.getContent())
                .build();

        Long postId2 = postService.save(saveRequestDto);

        PostDto updatePostDto2 = PostDto.builder()
                .id(postDto2.getId())
                .title("updated title")
                .content("this is a updated sample post")
                .build();

        PostControlRequestDto updateRequest = PostControlRequestDto.builder()
                .userId(userId)
                .postId(postId2)
                .title(updatePostDto2.getTitle())
                .content(updatePostDto2.getContent())
                .build();

        //When
        //Then
        mockMvc.perform(put("/posts/{id}", postId2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        PostDto savedPostDto = postService.find(postId);
        assertThat(savedPostDto, samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(userService.find(userId).getPostDtos().get(0),samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt") );

        PostDto savedPostDto2 = postService.find(postId2);
        assertThat(savedPostDto2, samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(userService.find(userId2).getPostDtos().get(0),samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt") );
    }

    @Test
    @DisplayName("게시물 조회 요청을 정상적으로 처리하는지 확인한다.")
    void getTest() throws Exception {
        //Given
        //When
        //Then
        MvcResult mvcResult = mockMvc.perform(get("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseData = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("data").toString();
        log.info("requestId : {} => responseData : {}", postId, responseData);
    }

    @Test
    @DisplayName("유효하지 않은 게시물 조회를 요청하면 Not Found 응답을 반환한다.")
    void getInvalidPostTest() throws Exception {
        //Given
        //When
        //Then
        mockMvc.perform(get("/posts/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("게시물 전체 조회 요청을 정상적으로 처리하는지 확인한다.")
    void getAllTest() throws Exception {
        //Given
        PostDto postDto2 = PostDto.builder()
                .title("second-title")
                .content("this is a second sample post")
                .build();

        PostControlRequestDto saveRequestDto = PostControlRequestDto.builder()
                .userId(userId)
                .title(postDto2.getTitle())
                .content(postDto2.getContent())
                .build();

        postService.save(saveRequestDto);

        //When
        //Then
        MvcResult mvcResult = mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseData = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("data").toString();
        log.info("responseData : {}", responseData);
    }

    @Test
    @DisplayName("게시물 삭제 요청을 정상적으로 처리하는지 확인한다.")
    void deleteTest() throws Exception {
        //Given
        PostControlRequestDto deleteRequestDto = PostControlRequestDto.builder()
                .userId(userId)
                .postId(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        //When
        //Then
        MvcResult mvcResult = mockMvc.perform(delete("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseData = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("data").toString();
        Long deletedPostId = Long.valueOf(responseData);

        assertThrows(NotFoundException.class, () -> postService.find(deletedPostId));
        assertThat(userService.find(userId).getPostDtos().size(), is(0));
    }

    @Test
    @DisplayName("게시물을 작성하지 않은 사용자가 게시물 삭제를 요청하면 Not Found 응답을 반환한다.")
    void deletePostByInvalidUserTest() throws Exception {
        //Given
        UserDto userDto2 = UserDto.builder()
                .name("jun")
                .age(10)
                .hobby("planting")
                .build();

        Long userId2 = userService.save(userDto);

        PostDto postDto2 = PostDto.builder()
                .title("second-title")
                .content("this is a second sample post")
                .build();

        PostControlRequestDto saveRequestDto = PostControlRequestDto.builder()
                .userId(userId2)
                .title(postDto2.getTitle())
                .content(postDto2.getContent())
                .build();

        Long postId2 = postService.save(saveRequestDto);

        //When
        //Then
        PostControlRequestDto deleteRequestDto = PostControlRequestDto.builder()
                .userId(userId)
                .postId(postId2)
                .title(postDto2.getTitle())
                .content(postDto2.getContent())
                .build();

        mockMvc.perform(delete("/posts/{id}", postId2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteRequestDto)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        assertThat(postService.findAll(PageRequest.of(0, 10)).getTotalElements(), is(2L));

        PostDto savedPostDto = postService.find(postId);
        assertThat(savedPostDto, samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(userService.find(userId).getPostDtos().get(0),samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt") );

        PostDto savedPostDto2 = postService.find(postId2);
        assertThat(savedPostDto2, samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(userService.find(userId2).getPostDtos().get(0),samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt") );
    }
}