package com.prgrms.boardjpa.User.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.User.domain.User;
import com.prgrms.boardjpa.User.domain.UserRepository;
import com.prgrms.boardjpa.User.dto.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 등록 성공")
    void createUserSuccessTest() throws Exception {

        //given
        UserRequest userRequest = UserRequest.builder()
                .name("lee")
                .age(10)
                .hobby("soccer")
                .build();

        //when -> then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-join",
                        preprocessRequest(prettyPrint()),  // 요청 포멧팅
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ), responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        )));
    }

    @Test
    @DisplayName("유저 등록 실패 - 중복된 이름")
    void createUserFailTest_duplicatedName() throws Exception {

        //given
        userRepository.save(User.builder()
                .name("hong")
                .hobby("golf")
                .age(20)
                .build());

        UserRequest userRequest = UserRequest.builder()
                .name("hong")
                .age(10)
                .hobby("soccer")
                .build();

        //when -> then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("user-join-fail-duplicated-name",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ), responseFields(
                                fieldWithPath("httpStatus").type(STRING).description("상태 코드"),
                                fieldWithPath("message").type(STRING).description("응답 메세지"),
                                fieldWithPath("valid").type(OBJECT).description("상세 정보")
                        )));
    }

    @Test
    @DisplayName("유저 등록 실패 - 잘못된 데이터 입력")
    void createUserFailTest_invalidData() throws Exception {

        //given
        UserRequest userRequest = UserRequest.builder()
                .name("@#@#@#(*&")
                .age(10)
                .hobby("soccer")
                .build();

        //when -> then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("user-join-fail-invalid-data",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ), responseFields(
                                fieldWithPath("httpStatus").type(STRING).description("상태 코드"),
                                fieldWithPath("message").type(STRING).description("응답 메세지"),
                                subsectionWithPath("valid").description("상세 정보")
                                        .attributes(
                                                Attributes.key("name").value("상세 에러 메세지 - 이름은 영어만 가능합니다.")
                                        )
                        )));
    }
}