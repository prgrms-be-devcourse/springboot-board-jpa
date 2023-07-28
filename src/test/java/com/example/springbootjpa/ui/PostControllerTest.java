package com.example.springbootjpa.ui;

import com.example.springbootjpa.application.PostService;
import com.example.springbootjpa.ui.dto.post.PostDto;
import com.example.springbootjpa.ui.dto.post.PostSaveRequestDto;
import com.example.springbootjpa.ui.dto.post.PostUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .operationPreprocessors()
                        .withRequestDefaults(modifyUris().host("localhost").port(8080), prettyPrint())
                        .withResponseDefaults(modifyUris().host("localhost").port(8080), prettyPrint()))
                .build();
    }

    @Test
    @DisplayName("모든 게시글 조회")
    void findAllPosts() throws Exception {
        //given
        PostDto postDto1 = new PostDto(1, "titleA", "contentA", 1);
        PostDto postDto2 = new PostDto(2, "titleB", "contentB", 2);
        PostDto postDto3 = new PostDto(3, "titleC", "contentC", 3);

        FieldDescriptor[] post =
                new FieldDescriptor[]{
                        fieldWithPath("[].postId").description("postId"),
                        fieldWithPath("[].title").description("title"),
                        fieldWithPath("[].content").description("content"),
                        fieldWithPath("[].userId").description("userId")
                };

        Pageable pageable = PageRequest.of(0, 3, Sort.by(
                Sort.Order.desc("id")));

        given(postService.findAllPosts(pageable)).willReturn(List.of(postDto1, postDto2, postDto3));

        // when && then
        mockMvc.perform(get("/api/v1/posts"))
                .andDo(print())
                .andDo(document("find-all-posts",
                        responseHeaders(
                                headerWithName(CONTENT_TYPE).description("content type")
                        ),
                        responseFields(
                                post
                        )
                ));
    }
    
    @Test
    @DisplayName("특정 게시글 조회")
    void findById() throws Exception {
        //given
        PostDto postDto1 = new PostDto(1, "titleA", "contentA", 1);
        given(postService.findPost(1L)).willReturn(postDto1);

        // when && then
        mockMvc.perform(get("/api/v1/posts/{postId}", 1))
                .andDo(print())
                .andDo(document("find-post",
                        responseHeaders(
                                headerWithName(CONTENT_TYPE).description("content type")
                        ),
                        responseFields(
                                fieldWithPath("postId").description("postId"),
                                fieldWithPath("title").description("title"),
                                fieldWithPath("content").description("content"),
                                fieldWithPath("userId").description("userId")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 생성")
    void createPost() throws Exception {
        //given
        PostSaveRequestDto postSaveRequestDto = new PostSaveRequestDto(1, "titleA", "contentA");

        given(postService.createPost(1, "titleA", "contentA")).willReturn(1L);

        // when && then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postSaveRequestDto)))
                .andDo(print())
                .andDo(document("create-post",
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description("content type"),
                                headerWithName(CONTENT_LENGTH).description("length of body")
                        ),
                        requestFields(
                                fieldWithPath("userId").description("id of user"),
                                fieldWithPath("title").description("title of post"),
                                fieldWithPath("content").description("content of post")
                        ),

                        responseHeaders(
                                headerWithName(LOCATION).description("created url"),
                                headerWithName(CONTENT_TYPE).description("content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id of post")
                        )
                ));
    }


    @Test
    @DisplayName("특정 게시글 수정")
    void updatePost() throws Exception {
        // given
        given(postService.updatePost(1L, "updateTitle", "updatedContent")).willReturn(1L);

        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto("updateTitle", "updateContent");

        // when && then
        mockMvc.perform(patch("/api/v1/posts/{postId}", 1)
                .content(objectMapper.writeValueAsString(postUpdateRequestDto))
                .contentType("application/json"))
                .andDo(print())
                .andDo(document("update-post",
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description("content type"),
                                headerWithName(CONTENT_LENGTH).description("length of body")
                        ),
                        requestFields(
                                fieldWithPath("title").description("title of post"),
                                fieldWithPath("content").description("content of post")
                        ),
                        responseHeaders(
                                headerWithName(CONTENT_TYPE).description("content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id of created post")
                        )
                ));
    }
}
