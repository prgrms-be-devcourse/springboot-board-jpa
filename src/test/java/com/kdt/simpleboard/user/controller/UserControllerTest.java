package com.kdt.simpleboard.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.simpleboard.BaseIntegrationTest;
import com.kdt.simpleboard.user.UserData;
import com.kdt.simpleboard.user.domain.User;
import com.kdt.simpleboard.user.dto.UserRequest;
import com.kdt.simpleboard.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원 생성 api 호출에 성공한다.")
    void createUser() throws Exception{
        UserRequest.CreateUserReq userReq = UserData.createUserReq();

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userReq))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdId").value(1L));
    }

    @Test
    @DisplayName("중복 이름의 회원이 존재하면 회원 api 생성 호출에 실패한다.")
    void createUserWithFail() throws Exception{
        User user = UserData.user();
        userRepository.save(user);
        UserRequest.CreateUserReq userReq = UserData.createUserReq();

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userReq))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("해당 이름의 회원이 이미 존재합니다."))
                .andDo(MockMvcResultHandlers.print());
    }
}