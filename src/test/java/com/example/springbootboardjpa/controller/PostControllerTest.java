package com.example.springbootboardjpa.controller;


import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.dto.UserDto;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import com.example.springbootboardjpa.repoistory.PostJpaRepository;
import com.example.springbootboardjpa.repoistory.UserJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;


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
                .andExpect(redirectedUrl("/posts"))
                .andDo(print());
    }

    @Test
    @DisplayName("title이 null이면 post를 생성할 수 없다.")
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
    @DisplayName("context가 null이면 post를 생성할 수 없다.")
    public void nullContextCreateFailTest() throws Exception {
        // Given
        UserDto.Info userDto = UserDto.Info.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .build();
        PostDTO.Save postDto = new PostDTO.Save("제목", null, userDto);

        // When // Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("user가 null이면 post를 생성할 수 없다.")
    public void nullUserCreateFailTest() throws Exception  {
        // Given
        PostDTO.Save postDto = new PostDTO.Save("제목", "내용", null);

        // When // Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("user id가 null이면 post를 생성할 수 없다.")
    public void nullUserIdCreateFailTest() throws Exception  {
        // Given
        UserDto.Info userDto = UserDto.Info.builder()
                .id(null)
                .age(user.getAge())
                .name(user.getName())
                .build();
        PostDTO.Save postDto = new PostDTO.Save("제목", "내용", userDto);

        // When // Then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("post를 정상 업데이트한다.")
    public void updatePost() throws Exception{
        // Given
        var post = postJpaRepository.save(new Post("제목", "내용",user));
        PostDTO.Request postDto= new PostDTO.Request("update_title", "update_context");

        // When // Then
        var id = post.getId();
        mockMvc.perform(post("/posts/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/posts/"+id))
                .andDo(print());
    }

    @Test
    @DisplayName("title이 null이면 post를 update할 수 없다.")
    public void nullTitleUpdateFailTest() throws Exception{
        // Given
        var post = postJpaRepository.save(new Post("제목", "내용",user));
        PostDTO.Request postDto= new PostDTO.Request(null, "update_context");

        // When // Then
        var id = post.getId();
        mockMvc.perform(post("/posts/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("context이 null이면 post를 update할 수 없다.")
    public void nullContextUpdateFailTest() throws Exception{
        // Given
        var post = postJpaRepository.save(new Post("제목", "내용",user));
        PostDTO.Request postDto= new PostDTO.Request("update_title", null);

        // When // Then
        var id = post.getId();
        mockMvc.perform(post("/posts/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    @DisplayName("post를 정상 조회한다.")
    public void getPostById() throws Exception {
        // Given
        var ResponseTitle = "$.[?(@.title == '%s')]";
        var ResponseContext = "$.[?(@.content == '%s')]";
        var post = postJpaRepository.save(new Post("제목", "내용",user));

        // When // Then
        var id = post.getId();
        mockMvc.perform(get("/posts/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ResponseTitle, "제목").exists())
                .andExpect(jsonPath(ResponseContext, "내용").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("post id가 존재하지않을 경우 조회 실패한다.")
    public void getFailById() throws Exception {
        // Given
        var nonPresentedId = 0L;
        assertThat(postJpaRepository.findById(nonPresentedId).isEmpty()).isTrue();

        // When // Then
        mockMvc.perform(get("/posts/"+nonPresentedId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("posts page 정상 생성한다.")
    public void getAllByPage() throws Exception {
        // Given
        List<Post> posts = Arrays.asList(new Post("test_title0", "content0", user),
                new Post("test_title1", "content1", user),
                new Post("test_title2", "content2", user),
                new Post("test_title3", "content3", user),
                new Post("test_title4", "content4", user),
                new Post("test_title5", "content5", user),
                new Post("test_title6", "content6", user));
        postJpaRepository.saveAll(posts);

        // When // Then
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(5)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}