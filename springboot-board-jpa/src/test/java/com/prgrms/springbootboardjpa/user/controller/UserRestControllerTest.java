package com.prgrms.springbootboardjpa.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.prgrms.springbootboardjpa.user.repository.UserRepository;
import com.prgrms.springbootboardjpa.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    User user;
    UserDto userDto;
    UserResponse userResponse;
    TypeReference<List<UserResponse>> typeReference;

    final String PATH = "/api/v1/users";

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();

        userDto = UserDto.builder()
                .nickName("Nickname")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test@gmail.com")
                .build();

        userResponse = userService.save(userDto);

        user = userRepository.findById(userResponse.getId()).get();

        setTypeReference();
    }

    @AfterEach
    void clearUp(){
        userRepository.deleteAll();
    }

    @Test
    void save() throws Exception {
        //Given
        UserDto givenUserDto = UserDto.builder()
                .nickName("Nickname2")
                .age(20)
                .hobby("Sleep")
                .firstName("EllaTwo")
                .lastName("MaTwo")
                .password("NewPassword123")
                .email("test2@gmail.com")
                .build();

        //when
        mockMvc.perform(post(PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(givenUserDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickName").value(givenUserDto.getNickName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(givenUserDto.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hobby").value(givenUserDto.getHobby()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(givenUserDto.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(givenUserDto.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(givenUserDto.getEmail()))
                .andDo(document("user-save",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id").optional(),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("nickName"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age").optional(),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby").optional(),
                                fieldWithPath("firstName").type(JsonFieldType.STRING).description("firstName"),
                                fieldWithPath("lastName").type(JsonFieldType.STRING).description("lastName"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("password"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("nickName"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("firstName").type(JsonFieldType.STRING).description("firstName"),
                                fieldWithPath("lastName").type(JsonFieldType.STRING).description("lastName"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email")
                        )
                        ));
    }

    @Test
    void allUsers() throws Exception {
        //Given
        UserDto givenUserDto = UserDto.builder()
                .nickName("Nickname2")
                .age(20)
                .hobby("Sleep")
                .firstName("EllaTwo")
                .lastName("MaTwo")
                .password("NewPassword123")
                .email("test2@gmail.com")
                .build();

        UserResponse givenUserResponse = userService.save(givenUserDto);

        List<UserResponse> givenUserResponseList = new ArrayList<>();
        givenUserResponseList.add(userResponse);
        givenUserResponseList.add(givenUserResponse);

        //when
        MvcResult result = mockMvc.perform(get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-getAll", preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("nickName"),
                                fieldWithPath("[].age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("[].hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("[].firstName").type(JsonFieldType.STRING).description("firstName"),
                                fieldWithPath("[].lastName").type(JsonFieldType.STRING).description("lastName"),
                                fieldWithPath("[].email").type(JsonFieldType.STRING).description("email")
                        )
                )).andReturn();

        List<UserResponse> userResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);


        //Then
        assertThat(userResponseList).usingRecursiveFieldByFieldElementComparator().isEqualTo(givenUserResponseList);
    }

    @Test
    void allUsersWithPage() throws Exception {
        //Given
        UserDto givenUserDto = UserDto.builder()
                .nickName("Nickname2")
                .age(20)
                .hobby("Sleep")
                .firstName("EllaTwo")
                .lastName("MaTwo")
                .password("NewPassword123")
                .email("test2@gmail.com")
                .build();

        UserResponse givenUserResponse = userService.save(givenUserDto);

        List<UserResponse> givenUserResponseList = new ArrayList<>();
        givenUserResponseList.add(givenUserResponse);

        //when
        MvcResult result = mockMvc.perform(get(PATH)
                .param("page", "1")
                .param("size", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-getAllWithPage",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("[].nickName").type(JsonFieldType.STRING).description("nickName"),
                                fieldWithPath("[].age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("[].hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("[].firstName").type(JsonFieldType.STRING).description("firstName"),
                                fieldWithPath("[].lastName").type(JsonFieldType.STRING).description("lastName"),
                                fieldWithPath("[].email").type(JsonFieldType.STRING).description("email")
                        )
                )).andReturn();

        List<UserResponse> userResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);


        assertThat(userResponseList).usingRecursiveFieldByFieldElementComparator().isEqualTo(givenUserResponseList);
    }

    void setTypeReference(){
        typeReference = new TypeReference<List<UserResponse>>() {
            @Override
            public Type getType() {
                return super.getType();
            }

            @Override
            public int compareTo(TypeReference<List<UserResponse>> o) {
                return super.compareTo(o);
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        };
    }
}