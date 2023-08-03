package prgms.boardmission.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import prgms.boardmission.member.dto.MemberDto;
import prgms.boardmission.post.dto.PostDto;
import prgms.boardmission.post.dto.PostUpdateDto;
import prgms.boardmission.post.service.PostService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostService postService;

    private MemberDto memberDto;
    private PostDto postDto;

    @BeforeEach
    void setUp() {
        long id = 1L;
        String name = "sehan";
        int age = 20;
        String hobby = "hobby";

        memberDto = new MemberDto(id, name, age, hobby);

        Long postId = 1L;
        String title = "title";
        String content = "content";

        postDto = new PostDto(postId, title, content, memberDto);

        postService.save(postDto);
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk()) // 200
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                                fieldWithPath("memberDto.userId").type(JsonFieldType.NUMBER).description("memberDto.userId"),
                                fieldWithPath("memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                                fieldWithPath("memberDto.age").type(JsonFieldType.NUMBER).description("memberDto.age"),
                                fieldWithPath("memberDto.hobby").type(JsonFieldType.STRING).description("memberDto.hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findBYId() throws Exception {
        mockMvc.perform(get("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findById",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.memberDto").type(JsonFieldType.OBJECT).description("memberDto"),
                                fieldWithPath("data.memberDto.userId").type(JsonFieldType.NUMBER).description("memberDto.userId"),
                                fieldWithPath("data.memberDto.name").type(JsonFieldType.STRING).description("memberDto.name"),
                                fieldWithPath("data.memberDto.age").type(JsonFieldType.NUMBER).description("memberDto.age"),
                                fieldWithPath("data.memberDto.hobby").type(JsonFieldType.STRING).description("memberDto.hobby"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void update() throws Exception {
        String editContent = "edit content";

        PostUpdateDto postUpdateDto = new PostUpdateDto(editContent);

        mockMvc.perform(MockMvcRequestBuilders.patch(("/posts/{postId}"), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }
}
