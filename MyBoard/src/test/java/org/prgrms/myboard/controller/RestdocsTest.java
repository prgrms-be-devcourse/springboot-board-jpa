package org.prgrms.myboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.myboard.dto.PostCreateRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class RestdocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void save_test() throws Exception {
        // Given
        PostCreateRequestDto postDto = new PostCreateRequestDto("testTitle", "testContent", 46L);

        // When Then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isOk()) // 200
            .andDo(print())
            .andDo(document("post-save",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("UserId")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("PostId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("Author"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Created Time"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Updated Time")
                )
            ));
    }

}
