package com.kdt.devboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.devboard.post.domain.Post;
import com.kdt.devboard.post.domain.PostDto;
import com.kdt.devboard.post.repository.PostRepository;
import com.kdt.devboard.post.service.PostService;
import com.kdt.devboard.user.domain.User;
import com.kdt.devboard.user.domain.UserDto;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    Long postId;

    @BeforeEach
    void saveTest() throws NotFoundException {
        UserDto userDto = UserDto.builder()
                .age(10)
                .createBy("me")
                .name("sunho")
                .hobby("sleep")
                .build();

        PostDto postDto = PostDto.builder()
                .content("화이팅!!")
                .title("오늘도")
                .createBy("me")
                .userDto(userDto)
                .build();

        postId = postService.save(postDto);
    }

    @AfterEach
    void tearUp() {
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("게시글 단건 조회 요청 테스트")
    void getOne() throws Exception {
        mockMvc.perform(get("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 다건 조회 요청 테스트")
    void getAll() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정 요청 테스트")
    void updatePost() throws Exception {
        PostDto updateDto = PostDto.builder()
                .id(postId)
                .content("바뀐 내용")
                .title("바뀐 제목")
                .build();

        mockMvc.perform(put("/posts")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deletePost() throws Exception {
        mockMvc.perform(delete("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
