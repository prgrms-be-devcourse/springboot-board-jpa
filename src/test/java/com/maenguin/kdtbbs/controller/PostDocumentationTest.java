package com.maenguin.kdtbbs.controller;

import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import com.maenguin.kdtbbs.repository.UserRepository;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class PostDocumentationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User("string", "string");
        Post post = new Post("string", "string");
        user.addPost(post);
        userRepository.save(user);
    }

    @Test
    void common() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/bbs/api/v1/posts")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("common",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                subsectionWithPath("data").description("요청 결과 데이터"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("요청 성공 여부"),
                                fieldWithPath("error").type(JsonFieldType.NULL).description("에러 정보"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    void searchAllPostsDocs() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/bbs/api/v1/posts")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("post-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("postList").type(JsonFieldType.ARRAY).description("게시글 목록"),
                                fieldWithPath("postList[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("postList[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("postList[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("pagination").type(JsonFieldType.OBJECT).description("pagination 정보"),
                                fieldWithPath("pagination.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("pagination.totalElements").type(JsonFieldType.NUMBER).description("총 게시글 수"),
                                fieldWithPath("pagination.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("pagination.offset").type(JsonFieldType.NUMBER).description("페이징 offset"),
                                fieldWithPath("pagination.size").type(JsonFieldType.NUMBER).description("페이징 size")
                        )
                ));

    }
}
