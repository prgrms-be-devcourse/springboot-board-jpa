package com.programmers.board.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.board.core.post.application.PostService;
import com.programmers.board.core.post.application.dto.CreateRequestPost;
import com.programmers.board.core.user.application.dto.UserDto;
import com.programmers.board.core.user.domain.Hobby;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("포스팅 생성 테스트 Happy Path")
    void savePost() throws Exception {

        //Given
        CreateRequestPost createRequestPost = CreateRequestPost.builder()
                .title("happyTitle")
                .content("happyContent")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("jung")
                                .age(145)
                                .hobby(Hobby.COOKING)
                                .build()
                )
                .build();

        mockMvc.perform(post("/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequestPost))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("body").type(JsonFieldType.OBJECT).description("body"),
                                fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("포스트 ID"),
                                fieldWithPath("body.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("body.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("body.createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버 응답시간")
                        )
                ));

    }

    @Test
    @DisplayName("포스팅 생성 테스트 Edge Case")
    void savePostFail() {

    }

}