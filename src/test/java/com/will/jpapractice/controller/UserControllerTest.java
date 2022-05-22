package com.will.jpapractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.jpapractice.domain.post.dto.PostResponse;
import com.will.jpapractice.domain.post.repository.PostRepository;
import com.will.jpapractice.domain.user.application.UserService;
import com.will.jpapractice.domain.user.dto.UserRequest;
import com.will.jpapractice.domain.user.dto.UserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.MethodNotAllowedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("유저가 하나도 없을 경우 data 값은 비어있다.")
    void test_find_user_if_empty() throws Exception {
        mockMvc.perform(get("/users")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("유저 나이 값을 0으로 지정했을 때 400 에러를 반환한다.")
    void test_get_user_if_age_is_zero() throws Exception {

        UserRequest userRequest = new UserRequest("will", 0);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))
                )
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andDo(document("create-user",
                                responseFields(
                                        fieldWithPath("message").type(STRING).description("에러 메시지"),
                                        fieldWithPath("statusCode").type(NUMBER).description("응답 상태 코드"),
                                        fieldWithPath("errors[].field").type(STRING).description("에러 발생 필드 명"),
                                        fieldWithPath("errors[].value").type(STRING).description("에러 발생 필드 값"),
                                        fieldWithPath("errors[].reason").type(STRING).description("에러 발생 이유"),
                                        fieldWithPath("serverDateTime").type(STRING).description("서버 시간")
                                )
                        )
                );
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void test_create_user() throws Exception {
        UserRequest userRequest = new UserRequest("will", 24);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create-user",
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("응답 상태 코드"),
                                        fieldWithPath("data").type(NUMBER).description("사용자 아이디")
                                )
                        )
                );
    }

    @Test
    @DisplayName("모든 유저를 페이징 조회한다.")
    void test_paging_all_user() throws Exception {
        List<UserResponse> userResponses = new ArrayList<>();
        for (long i=0; i<5; i++) {
            userResponses.add(
                    UserResponse.builder()
                            .id(i)
                            .name("will-" + i)
                            .age(23)
                            .build()
            );
        }

        Page<UserResponse> userResponsePage = new PageImpl<>(userResponses);
        when(userService.findUsers(PageRequest.of(1, 5)))
                .thenReturn(userResponsePage);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-all-users",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 크기")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("응답 상태 코드"),
                                        fieldWithPath("data.content[].id").type(NUMBER).description("유저 ID"),
                                        fieldWithPath("data.content[].name").type(STRING).description("유저 이름"),
                                        fieldWithPath("data.content[].age").type(NUMBER).description("유저 나이"),
                                        fieldWithPath("data.pageable").type(STRING).description("페이지"),
                                        fieldWithPath("data.last").type(BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("data.totalPages").type(NUMBER).description("전체 페이지 수"),
                                        fieldWithPath("data.totalElements").type(NUMBER).description("전체 게시글 수"),
                                        fieldWithPath("data.size").type(NUMBER).description("페이지 크기"),
                                        fieldWithPath("data.number").type(NUMBER).description("페이지 번호"),
                                        fieldWithPath("data.sort.empty").type(BOOLEAN).description("정렬 설정 여부"),
                                        fieldWithPath("data.sort.sorted").type(BOOLEAN).description("정렬 여부"),
                                        fieldWithPath("data.sort.unsorted").type(BOOLEAN).description("비정렬 여부"),
                                        fieldWithPath("data.first").type(BOOLEAN).description("첫 페이지 여부"),
                                        fieldWithPath("data.numberOfElements").type(NUMBER).description("게시글 개수"),
                                        fieldWithPath("data.empty").type(BOOLEAN).description("빈 값 여부")
                                )
                        )
                );
    }

}