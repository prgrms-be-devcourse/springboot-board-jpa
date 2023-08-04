package com.juwoong.springbootboardjpa.user.api;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juwoong.springbootboardjpa.post.application.PostService;
import com.juwoong.springbootboardjpa.user.api.model.UserRequest;
import com.juwoong.springbootboardjpa.user.application.UserService;
import com.juwoong.springbootboardjpa.user.application.model.UserDto;
import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;

@WebMvcTest
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @DisplayName("User ID 조회에 대한 성공 테스트")
    @Test
    public void SearchByIdTest() throws Exception {
        // given
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Juwoong", 30, Hobby.SPORTS, null, null, null);

        when(userService.searchById(userId)).thenReturn(userDto);

        //when then
        mockMvc.perform(get("/api/user/search/{id}", userId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userId))
            .andExpect(jsonPath("$.name").value("Juwoong"))
            .andExpect(jsonPath("$.age").value(30))
            .andExpect(jsonPath("$.hobby").value(Hobby.SPORTS.name()))
            .andDo(document("search-by-user",
                responseFields(
                    fieldWithPath("id").description("The ID of the user"),
                    fieldWithPath("name").description("The name of the user"),
                    fieldWithPath("age").description("The age of the user"),
                    fieldWithPath("hobby").description("The hobby of the user"),
                    fieldWithPath("posts").description("List of user posts"),
                    fieldWithPath("createdAt").description("User creation timestamp"),
                    fieldWithPath("updatedAt").description("User update timestamp")
                )
            ));
    }

    @DisplayName("User 생성에 대한 성공 테스트")
    @Test
    public void testCreateUser() throws Exception {
        // given
        UserRequest request = new UserRequest("Juwoong", 30, Hobby.SPORTS);

        UserDto userDto = new UserDto(1L, "Juwoong", 30, Hobby.SPORTS, null, null, null);

        when(userService.createUser("Juwoong", 30, Hobby.SPORTS)).thenReturn(userDto);

        // when then
        mockMvc.perform(post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Juwoong"))
            .andExpect(jsonPath("$.age").value(30))
            .andExpect(jsonPath("$.hobby").value(Hobby.SPORTS.name()))
            .andDo(document("create-user",
                requestFields(
                    fieldWithPath("name").description("The name of the user"),
                    fieldWithPath("age").description("The age of the user"),
                    fieldWithPath("hobby").description("The hobby of the user")
                ),
                responseFields(
                    fieldWithPath("id").description("The ID of the user"),
                    fieldWithPath("name").description("The name of the user"),
                    fieldWithPath("age").description("The age of the user"),
                    fieldWithPath("hobby").description("The hobby of the user"),
                    fieldWithPath("posts").description("List of user posts"),
                    fieldWithPath("createdAt").description("User creation timestamp"),
                    fieldWithPath("updatedAt").description("User update timestamp")
                )
            ));
    }

}
