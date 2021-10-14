package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.UserRequestDto;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private Long globalUserId;
    private String globalUserName = "jihun-each";

    @BeforeEach
    void setUp() {
        var userDto = UserRequestDto.builder()
                .age(12)
                .name(globalUserName)
                .hobby("hobby-each")
                .build();

        globalUserId = userService.insert(userDto);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자를 추가할 수 있다.")
    void insertTest() throws Exception {
        //given
        var userDto = UserRequestDto.builder()
                .age(12)
                .name("jihun-each")
                .hobby("hobby-each")
                .build();

        //when, then
        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))  // dto를 String형태로 변환해서 requestbody에 삽입
                .andExpect(status().isOk())  // 기대되는 결과 200 일 것
                .andDo(print())  // 결과를 프린트함
                // 문서화를 위한 설정
                .andDo(document("user-insert",
                        requestFields(
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된id"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("사용자 한명을 삭제할 수 있다.")
    void deleteOneTest() throws Exception {
        //given
        var id = globalUserId;

        // when, then
        mockMvc.perform(delete("/api/v1/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // 기대되는 결과 200 일 것
                .andDo(print())  // 결과를 프린트함
                // 문서화를 위한 설정
                .andDo(document("user-delete"));
    }

    @Test
    @DisplayName("사용자 id로 찾을 수 있다.")
    void findByIdTest() throws Exception {
        //given
        var id = globalUserId;

        // when, then
        mockMvc.perform(get("/api/v1/user/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // 기대되는 결과 200 일 것
                .andDo(print())  // 결과를 프린트함
                // 문서화를 위한 설정
                .andDo(document("user-find-by-id",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }
}