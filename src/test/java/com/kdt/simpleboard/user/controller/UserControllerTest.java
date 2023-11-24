package com.kdt.simpleboard.user.controller;

import com.kdt.simpleboard.BaseIntegrationTest;
import com.kdt.simpleboard.user.UserData;
import com.kdt.simpleboard.user.domain.User;
import com.kdt.simpleboard.user.dto.UserRequest;
import com.kdt.simpleboard.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.kdt.simpleboard.user.dto.UserRequest.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 생성 api 호출에 성공한다.")
    void createUser() throws Exception {
        CreateUserRequest createUserRequest = UserData.createUserReq();
        mvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(createUserRequest))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdId").isNotEmpty());
    }

    @Test
    @DisplayName("중복 이름의 회원이 존재하면 회원 api 생성 호출에 실패한다.")
    void createUserWithFail() throws Exception {
        User user = UserData.user();
        userRepository.save(user);
        CreateUserRequest userReq = UserData.createUserReq();

        mvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(userReq))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("해당 이름의 회원이 이미 존재합니다."))
                .andDo(MockMvcResultHandlers.print());
    }
}