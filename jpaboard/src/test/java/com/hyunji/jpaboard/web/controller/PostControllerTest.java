package com.hyunji.jpaboard.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunji.jpaboard.web.dto.PostDto;
import com.hyunji.jpaboard.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private void registerUser(String name, int age, String hobby) {
        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setAge(age);
        userDto.setHobby(hobby);

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/user")
                            .content(mapper.writeValueAsString(userDto))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("user registered"))
            ;
        } catch (Exception e) {
            throw new IllegalStateException("failed to register user");
        }
    }

    private void registerPost(String title, String content, String username) {
        PostDto.Request requestDto = new PostDto.Request();
        requestDto.setTitle(title);
        requestDto.setContent(content);
        requestDto.setUsername(username);

        try {
            mockMvc.perform(post("/posts")
                            .content(mapper.writeValueAsString(requestDto))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("post registered"));
        } catch (Exception e) {
            throw new IllegalStateException("failed to register post");
        }
    }

    @Test
    void register() {
        registerUser("post_user01", 100, "game");
        registerPost("test title", "test content", "post_user01");
    }

    @Test
    void getPages() throws Exception {

        registerUser("post_user03", 100, "game");
        IntStream.range(1, 100)
                .forEach(i -> registerPost("test title" + i, "test content" + i, "post_user03"));

        mockMvc.perform(get("/posts")
                .param("pageNum", "80")
        ).andExpect(status().isOk());
    }

}