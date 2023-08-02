package devcource.hihi.boardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcource.hihi.boardjpa.dto.user.CreateUserDto;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import devcource.hihi.boardjpa.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void save_test() throws Exception {
        //given
        CreateUserDto userDto = CreateUserDto.builder()
                        .name("신예진")
                                .age(25)
                                        .hobby("수영")
                .build();
        //when
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-service",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("NAME"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("AGE"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("HOBBY")

                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("NAME"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("AGE"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("HOBBY")
                        ))
                );

    }

    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/users/{id}", 11)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/users")
                        .param("pageNumber", String.valueOf(0))
                        .param("pageSize", String.valueOf(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}