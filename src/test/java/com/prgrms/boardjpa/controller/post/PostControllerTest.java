package com.prgrms.boardjpa.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.domain.member.Hobby;
import com.prgrms.boardjpa.domain.member.dto.MemberCreateRequestDto;
import com.prgrms.boardjpa.domain.member.service.MemberService;
import com.prgrms.boardjpa.domain.post.dto.PostCreateRequestDto;
import com.prgrms.boardjpa.domain.post.dto.PostUpdateRequestDto;
import com.prgrms.boardjpa.domain.post.service.PostService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;
    @Autowired
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        memberService.createMember(MemberCreateRequestDto.builder()
                .name("kim")
                .age(27)
                .hobby(Hobby.INACTIVE)
                .build());
    }

    @Test
    @Order(1)
    void 글쓰기() throws Exception {
        PostCreateRequestDto post = PostCreateRequestDto.builder()
                .memberId(1L)
                .title("글 제목 테스트")
                .content("글 내용 테스트")
                .build();
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                // memberId, title, content
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                // id, title, content, member
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("member.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("member.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("member.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("member.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("member.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("member.createdBy").type(JsonFieldType.STRING).description("createdBy")
                        )
                ));

    }

    @Test
    @Order(2)
    void 글_전체_읽기() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("page", "0");
        map.add("size", "10");

        mockMvc.perform(get("/posts")
                        .params(map))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getAll",
                        responseFields(
                                fieldWithPath("posts[]").type(JsonFieldType.ARRAY).description("posts"),
                                fieldWithPath("posts[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("posts[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("posts[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("posts[].member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("posts[].member.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("posts[].member.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("posts[].member.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("posts[].member.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("posts[].member.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("posts[].member.createdBy").type(JsonFieldType.STRING).description("createdBy")
                        )));

    }

    @Test
    @Order(3)
    void 글_한개_읽기() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getOne",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("member.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("member.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("member.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("member.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("member.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("member.createdBy").type(JsonFieldType.STRING).description("createdBy")
                        )
                ));
    }

    @Test
    @Order(4)
    void 글_수정하기() throws Exception {
        PostUpdateRequestDto post = PostUpdateRequestDto.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();
        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("member.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("member.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("member.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("member.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("member.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("member.createdBy").type(JsonFieldType.STRING).description("createdBy")
                        )
                ));
    }
}