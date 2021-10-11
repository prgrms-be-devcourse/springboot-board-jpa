package com.example.board.Controller;

import com.example.board.Dto.PostRequestDto;
import com.example.board.Dto.UserDto;
import com.example.board.Service.PostService;
import com.example.board.domain.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    PostService postService;
    @Autowired
    ObjectMapper objectMapper;
//    @BeforeEach
//    void setup(){
//        PostRequestDto postRequestDto = PostRequestDto.builder()
//                .title("test확인")
//                .content("post test중입니다")
//                .user(
//                        UserDto.builder()
//                                .age(24)
//                                .name("박연수")
//                                .hobby("놀기")
//                                .build()
//                )
//                .build();
//        postService.save(postRequestDto);
//    }
    @Test
    void saveApiTest() throws Exception {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();
       mockMvc.perform(post("/api/v1/posts")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(postRequestDto)))
               .andExpect(status().isOk())
               .andDo(print());



    }
    @Test
    void update() throws Exception {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();
        Post save = postService.save(postRequestDto);

        PostRequestDto postRequestDto2 = PostRequestDto.builder()
                .title("updatetest")
                .content("updatetest중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();
        mockMvc.perform(post("/api/v1/posts/{id}",save.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto2)))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    void findOne() throws Exception {
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();
        Post save = postService.save(postRequestDto);



        mockMvc.perform(get("/api/v1/posts/{id}",save.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print());


    }
    @Test
    void findAll() throws Exception {
        PageRequest page=PageRequest.of(0,10);

        mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(page)))

                .andExpect(status().isOk())
                .andDo(print());


    }





}