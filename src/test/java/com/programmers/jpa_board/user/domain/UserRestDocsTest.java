package com.programmers.jpa_board.user.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpa_board.user.application.UserServiceImpl;
import com.programmers.jpa_board.user.domain.dto.UserDto;
import com.programmers.jpa_board.user.ui.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
public class UserRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void 저장_성공() throws Exception {
        //given
        UserDto.CreateUserRequest request = new UserDto.CreateUserRequest("신범철", 26, "헬스");
        UserDto.UserResponse response = new UserDto.UserResponse(1L, "신범철", 26, "운동", null, LocalDateTime.now());

        given(userService.save(request))
                .willReturn(response);

        //when & then
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태 코드"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미"),
                                fieldWithPath("data.posts").type(JsonFieldType.ARRAY).description("게시물 목록").optional(),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 일시")
                        ))
                );
    }
}
