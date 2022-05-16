package com.kdt.boardMission.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.UserRepository;
import com.kdt.boardMission.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 생성 저장")
    public void createUserTest() throws Exception {

        //given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("name")
                .age(10)
                .hobby("hobby")
                .build();

        //when
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("유저 목록 조회")
    public void searchUserTest() throws Exception {

        //given
        for (int i = 0; i < 20; i++) {
            UserDto userDto = UserDto.builder()
                    .name("name" + i)
                    .age(10)
                    .hobby("hobby")
                    .build();
            userService.saveUser(userDto);
        }

        //when
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-find-all",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("data.content[]"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("data.content[].id"),
                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("data.content[].name"),
                                fieldWithPath("data.content[].age").type(JsonFieldType.NUMBER).description("data.content[].age"),
                                fieldWithPath("data.content[].hobby").type(JsonFieldType.STRING).description("data.content[].hobby"),

                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),

                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),

                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),

                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),

                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )
                ));
    }

    @Test
    @DisplayName("유저 목록 이름으로 조회")
    public void searchUserByNameTest() throws Exception {

        //given
        for (int i = 0; i < 20; i++) {
            UserDto userDto = UserDto.builder()
                    .name("name" + i)
                    .age(10)
                    .hobby("hobby")
                    .build();
            userService.saveUser(userDto);
        }

        //when
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name","am"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-find-name",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("data.content[]"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("data.content[].id"),
                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("data.content[].name"),
                                fieldWithPath("data.content[].age").type(JsonFieldType.NUMBER).description("data.content[].age"),
                                fieldWithPath("data.content[].hobby").type(JsonFieldType.STRING).description("data.content[].hobby"),

                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),

                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),

                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),

                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),

                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )
                ));
    }

    @Test
    @DisplayName("유저 아이디로 조회")
    public void findByIdTest() throws Exception {

        //given
        UserDto userDto = UserDto.builder()
                .name("name")
                .age(10)
                .hobby("hobby")
                .build();
        long userId = userService.saveUser(userDto);

        //when
        mockMvc.perform(get("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-find-userId",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("data.id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("data.name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("data.age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("data.hobby"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("수정하기")
    public void editUserTest() throws Exception {

        //given
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("name")
                .age(10)
                .hobby("hobby")
                .build();
        userService.saveUser(userDto);

        //when
        mockMvc.perform(put("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-edit",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("data.id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("data.name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("data.age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("data.hobby"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }


}