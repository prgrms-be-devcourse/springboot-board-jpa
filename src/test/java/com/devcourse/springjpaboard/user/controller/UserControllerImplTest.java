package com.devcourse.springjpaboard.user.controller;

import com.devcourse.springjpaboard.exception.NotFoundException;
import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.user.controller.stub.UserStubs;
import com.devcourse.springjpaboard.user.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.devcourse.springjpaboard.exception.ExceptionMessage.NOT_FOUND_USER;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureRestDocs
class UserControllerImplTest {

    @InjectMocks
    private UserControllerImpl userController;

    @Mock
    private UserServiceImpl userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void createUserTest() throws Exception {
        // given
        CreateUserRequest request = UserStubs.createUserRequest();
        UserResponse response = UserStubs.userResponse();

        doReturn(response)
                .when(userService)
                .save(any(CreateUserRequest.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("data.id", response.id()).exists())
                .andExpect(jsonPath("data.name", response.name()).exists())
                .andExpect(jsonPath("data.age", response.age()).exists())
                .andExpect(jsonPath("data.hobby", response.hobby()).exists());
    }

    @Test
    @DisplayName("존재하는 회원 ID로 조회 테스트")
    void getUserByIdTest() throws Exception {
        // given
        Long request = 1L;
        UserResponse response = UserStubs.userResponse();

        doReturn(response).when(userService).findById(request);
        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/user/1")
        );
        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        String data = jsonObject.getString("data");
        ObjectMapper mapper = new ObjectMapper();
        UserResponse mvcResponse = mapper.readValue(data, UserResponse.class);
        assertThat(mvcResponse).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 조회시 예외 발생 테스트")
    void getUserByIdFailTest() throws Exception {
        // given
        Long request = 3L;

        // when
        when(userService.findById(request)).thenThrow(new NotFoundException(NOT_FOUND_USER));

        // then
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.findById(request));
    }
}