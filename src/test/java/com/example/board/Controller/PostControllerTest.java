package com.example.board.Controller;

import com.example.board.Dto.PostDto;
import com.example.board.Dto.PostRequestDto;
import com.example.board.Dto.UserDto;
import com.example.board.Service.PostService;
import com.example.board.domain.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @Autowired
    private WebApplicationContext wac;

    private PostDto postDto;

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        postDto = PostDto.builder()
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
        postService.save(postDto);
    }
    @Test
    void saveApiTest() throws Exception {

       mockMvc.perform(post("/api/v1/posts")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(postDto)))
               .andExpect(status().isOk())
               .andDo(print());

    }
    @Test
    void update() throws Exception {

        Long save = postService.save(postDto);

        PostRequestDto postDto2 = PostRequestDto.builder()
                .title("updatetest")
                .content("updatetest중입니다")
                .build();
        mockMvc.perform(patch("/api/v1/posts/{id}",save)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto2)))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    void findOne() throws Exception {

        Long save = postService.save(postDto);


        mockMvc.perform(get("/api/v1/posts/{id}",save)
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