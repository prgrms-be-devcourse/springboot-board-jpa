package com.programmers.kwonjoosung.board.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.kwonjoosung.board.model.dto.CreateUserRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
class UserControllerTest {

    private final CreateUserRequest request
            = new CreateUserRequest("유저명", 28, "테스트 코드 짜기");

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        given(userService.saveUser(any(CreateUserRequest.class))).willReturn(new IdResponse(1L));

        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디")
                        )));
    }
}