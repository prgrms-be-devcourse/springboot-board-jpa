package com.example.board.domain.user.api;

import com.example.board.config.RestDocsConfiguration;
import com.example.board.domain.user.dto.UserDto;
import com.example.board.domain.user.entity.User;
import com.example.board.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.example.board.domain.hobby.entity.HobbyType.GAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestDocumentationResultHandler restDocs;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(restDocs)
                .build();
    }

    private final User user = User.builder()
            .name("박현서")
            .age(3)
            .build();

    private final UserDto.CreateUserRequest createUserRequest
            = new UserDto.CreateUserRequest(user.getName(), user.getAge(), GAME);


    private final UserDto.SingleUserDetailResponse singleUserDetailResponse
            = UserDto.SingleUserDetailResponse.toResponse(user, List.of(GAME));

    @DisplayName("회원 저장을 성공한다.")
    @Test
    void save_user_success() throws Exception {
        when(userService.enroll(any()))
                .thenReturn(singleUserDetailResponse);

        String requestBody = objectMapper.writeValueAsString(createUserRequest);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("age").description("나이"),
                                fieldWithPath("hobby").description("취미")
                        ),
                        responseFields(
                                fieldWithPath("status").description("응답 상태"),
                                fieldWithPath("title").description("응답 제목"),
                                fieldWithPath("content").description("응답 내용"),
                                fieldWithPath("stock.name").description("회원 이름"),
                                fieldWithPath("stock.age").description("회원 나이"),
                                fieldWithPath("stock.hobbies").description("회원 취미")
                        )
                ));
    }
}