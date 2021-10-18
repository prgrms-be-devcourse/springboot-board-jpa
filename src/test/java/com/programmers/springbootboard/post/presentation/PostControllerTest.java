package com.programmers.springbootboard.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.request.MemberSignRequest;
import com.programmers.springbootboard.post.application.PostService;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.response.PostInsertResponse;
import com.programmers.springbootboard.post.dto.request.PostUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// TODO :: 목객체로 테스트 진행, 현재 테스트 코드는 잘못되어있습니다.
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
    private PostService postService;

    @Autowired
    private PostConverter postConverter;


    @BeforeEach
    @DisplayName("초기화")
    void init() {
        memberService.deleteAll();

        // given
        Email email = new Email("wrjs@naver.com");
        Name name = new Name("김동건");
        Age age = new Age("18");
        Hobby hobby = new Hobby("취미는 없습니다!");

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);

        // when
        memberService.insert(request);

        // then
        long count = memberService.count();
        assertThat(1L).isEqualTo(count);
    }

    @Test
    @DisplayName("게시물_추가")
    void insertPost() throws Exception {
        // given
        Email email = new Email("wrjs@naver.com");
        Title title = new Title("행복합니당 행복합니다.");
        Content content = new Content("행복합니당행복합니당행복합니당!");

        PostInsertResponse postInsertRequest = postConverter.toPostInsertRequest(email, title, content);

        // when // then
        mockMvc.perform(post("/api/post")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
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
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
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

        PostInsertResponse postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        // when // then
        mockMvc.perform(delete("/api/post/{id}", id)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
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
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
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

        PostInsertResponse postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        PostUpdateRequest postUpdateRequest = postConverter.toPostUpdateRequest(email, title, content);


        // when // then
        mockMvc.perform(patch("/api/post/{id}", id)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
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
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
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

        PostInsertResponse postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        // when // then
        mockMvc.perform(get("/api/post/{id}", id)
                        .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-inquiry",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
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

        PostInsertResponse postInsertRequest = postConverter.toPostInsertRequest(email, title, content);
        postService.insert(email, postInsertRequest);

        // when // then
        mockMvc.perform(get("/api/post")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("posts-inquiry",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("본문"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("본문"),
                                fieldWithPath("data.content[].email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("link").type(JsonFieldType.OBJECT).description("hateoas"),
                                fieldWithPath("link.rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link.href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link.type").type(JsonFieldType.STRING).description("HTTP Method Type")
                        )
                ));
    }
}
