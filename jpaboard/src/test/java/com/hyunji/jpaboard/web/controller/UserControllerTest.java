package com.hyunji.jpaboard.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunji.jpaboard.web.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void register() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("user01");
        userDto.setAge(100);
        userDto.setHobby("game");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("user registered"))
        ;
    }
}