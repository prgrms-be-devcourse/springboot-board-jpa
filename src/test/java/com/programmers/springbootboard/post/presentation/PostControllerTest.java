package com.programmers.springbootboard.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import com.programmers.springbootboard.post.application.PostService;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.PostInsertRequest;
import com.programmers.springbootboard.post.dto.PostUpdateRequest;
import com.programmers.springbootboard.post.infrastructure.PostRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberConverter memberConverter;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostConverter postConverter;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    @DisplayName("초기화")
    void init() {
        postRepository.deleteAll();
        memberRepository.deleteAll();

        // given
        Email email = new Email("wrjs@naver.com");
        Name name = new Name("김동건");
        Age age = new Age("18");
        Hobby hobby = new Hobby("취미는 없습니다!");

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);

        // when
        memberService.insert(request);

        // then
        long count = memberRepository.count();
        assertThat(1L).isEqualTo(count);
    }

    @Test
    @DisplayName("게시물_추가")
    void insertPost() throws Exception {
        // given
        Email email = new Email("wrjs@naver.com");
        Title title = new Title("행복합니당 행복합니다.");
        Content content = new Content("행복합니당행복합니당행복합니당!");

        PostInsertRequest postInsertRequest = postConverter.toPostInsertRequest(email, title, content);

        // when // then
        mockMvc.perform(post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postInsertRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-insert",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("본문")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )
                ));
    }

    @Test
    @DisplayName("게시물_수정")
    void updatePost() throws Exception {
        // given
        Long id = 1L;
        Email email = new Email("wrjs@naver.com");
        Title title = new Title("행복합니당 행복합니다.");
        Content content = new Content("행복합니당행복합니당행복합니당!");

        PostInsertRequest postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        PostUpdateRequest postUpdateRequest = postConverter.toPostUpdateRequest(email, title, content);


        // when // then
        mockMvc.perform(put("/api/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("본문")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )
                ));
    }

    @Test
    @DisplayName("게시물_단건_조회")
    void inquiryPost() throws Exception {
        // given
        Long id = 1L;
        Email email = new Email("wrjs@naver.com");
        Title title = new Title("행복합니당 행복합니다.");
        Content content = new Content("행복합니당행복합니당행복합니당!");

        PostInsertRequest postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        // when // then
        mockMvc.perform(get("/api/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-inquiry",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일")
                        )
                ));
    }

    @Test
    @DisplayName("게시물_전체_조회")
    void inquiryPosts() throws Exception {
        // given
        Long id = 1L;
        Email email = new Email("wrjs@naver.com");
        Title title = new Title("행복합니당 행복합니다.");
        Content content = new Content("행복합니당행복합니당행복합니당!");

        PostInsertRequest postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        // when // then
        mockMvc.perform(get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("posts-inquiry",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("본문"),
                                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일")
                        )
                ));
    }

    @Test
    @DisplayName("게시물_삭제")
    void deletePost() throws Exception {
        // given
        Long id = 1L;
        Email email = new Email("wrjs@naver.com");
        Title title = new Title("행복합니당 행복합니다.");
        Content content = new Content("행복합니당행복합니당행복합니당!");

        PostInsertRequest postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        // when // then
        mockMvc.perform(delete("/api/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-delete",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )
                ));
    }
}
