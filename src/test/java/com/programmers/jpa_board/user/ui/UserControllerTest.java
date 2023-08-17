package com.programmers.jpa_board.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpa_board.user.application.UserServiceImpl;
import com.programmers.jpa_board.user.domain.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void API_POST_유저_저장_성공() throws Exception {
        //given
        UserDto.CreateUserRequest request = new UserDto.CreateUserRequest("신범철", 26, "운동");
        UserDto.UserResponse response = new UserDto.UserResponse(1L, "신범철", 26, "운동", null, LocalDateTime.now());

        given(userService.save(request))
                .willReturn(response);

        //when & then
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("CREATED"))
                .andExpect(jsonPath("data.name").value(response.name()))
                .andExpect(jsonPath("data.age").value(response.age()))
                .andExpect(jsonPath("data.hobby").value(response.hobby()))
                .andExpect(jsonPath("data.id").value(response.id()))
                .andExpect(jsonPath("data.createAt").isNotEmpty())
                .andExpect(jsonPath("data.posts").isEmpty())
                .andDo(print());
    }
}
