package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.domain.UserRepository;
import com.example.springbootboardjpa.user.dto.CreateUserRequest;
import com.example.springbootboardjpa.user.dto.UserResponse;
import com.example.springbootboardjpa.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DisplayName("User API 테스트")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    UserResponse userResponse;

    @BeforeEach
    void setup() {
        CreateUserRequest createUserRequest =
                CreateUserRequest.builder()
                        .age(1)
                        .name("lee")
                        .hobby("live")
                        .build();
        userResponse = userService.insert(createUserRequest);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 등록 테스트")
    void USER_INSERT_TEST() throws Exception {
        CreateUserRequest createUserRequest =
                CreateUserRequest.builder()
                        .age(29)
                        .name("nnagman")
                        .hobby("why")
                        .build();
        this.mockMvc.perform(put("/api/v1/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.name").value(createUserRequest.getName()))
                .andExpect(jsonPath("$.age").value(createUserRequest.getAge()))
                .andExpect(jsonPath("$.hobby").value(createUserRequest.getHobby()))
                .andDo(document("register-user",
                        requestFields(
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("전체 유저 조회 테스트")
    void GET_USER_LIST_TEST() throws Exception {
        this.mockMvc.perform(get("/api/v1/users")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.content[0].id").value(userResponse.getId().toString()))
                .andDo(document("getAll-user",
                        responseFields(
                                fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("content[]"),
                                fieldWithPath("content[].id").type(JsonFieldType.STRING).description("content[].id"),
                                fieldWithPath("content[].name").type(JsonFieldType.STRING).description("content[].name"),
                                fieldWithPath("content[].age").type(JsonFieldType.NUMBER).description("content[].age"),
                                fieldWithPath("content[].hobby").type(JsonFieldType.STRING).description("content[].hobby"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("content[].createdAt"),
                                fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING).description("content[].updatedAt"),
                                fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageable"),
                                fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                        )
                ));
    }

    @Test
    @DisplayName("특정 유저 조회 테스트")
    void GET_USER_BY_ID_TEST() throws Exception {
        this.mockMvc.perform(get("/api/v1/users/{id}", userResponse.getId())
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id").value(userResponse.getId().toString()))
                .andExpect(jsonPath("$.name").value(userResponse.getName()))
                .andExpect(jsonPath("$.age").value(userResponse.getAge()))
                .andExpect(jsonPath("$.hobby").value(userResponse.getHobby()))
                .andDo(document("getById-user",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("유저 정보를 수정 할 수 있다.")
    void MODIFY_USER_TEST() throws Exception {
        this.mockMvc.perform(patch("/api/v1/users")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userResponse)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json;charset=UTF-8")))
                .andExpect(jsonPath("$.id").value(userResponse.getId().toString()))
                .andDo(document("modify-user",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("id"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("유저를 삭제 할 수 있다.")
    void REMOVE_USER_TEST() throws Exception {
        this.mockMvc.perform(delete("/api/v1/users/{id}", userResponse.getId().toString()))
                .andExpect(status().isNoContent())
                .andDo(document("remove-user"));
    }
}