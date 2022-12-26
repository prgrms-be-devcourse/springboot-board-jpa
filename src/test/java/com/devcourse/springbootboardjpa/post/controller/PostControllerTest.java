package com.devcourse.springbootboardjpa.post.controller;

import com.devcourse.springbootboardjpa.common.dto.page.PageDTO;
import com.devcourse.springbootboardjpa.post.domain.Post;
import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.post.service.PostService;
import com.devcourse.springbootboardjpa.user.domain.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    @DisplayName("게시물 저장 성공")
    void shouldSavePost() throws Exception {
        // given
        PostDTO.SaveRequest postSaveRequest = PostDTO.SaveRequest.builder()
                .userId(1L)
                .title("title")
                .content("content")
                .build();

        when(postService.savePost(any(PostDTO.SaveRequest.class)))
                .thenReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(postSaveRequest))
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("status").value(201))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("data").value(1))
                .andDo(print())
                .andDo(document("save-post",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성자 Id")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("응답 결과")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 조회 성공")
    void shouldFindPost() throws Exception {
        // given
        Long request = 1L;
        PostDTO.FindResponse findPostResponse = createPostFindResponseDto(request, "title", "content", 1L, "userName");
        when(postService.findPost(request))
                .thenReturn(findPostResponse);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/posts/{id}", request)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("status").value(200))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.id").value(findPostResponse.getId()))
                .andExpect(jsonPath("data.title").value(findPostResponse.getTitle()))
                .andExpect(jsonPath("data.content").value(findPostResponse.getContent()))
                .andExpect(jsonPath("data.userId").value(findPostResponse.getUserId()))
                .andExpect(jsonPath("data.userName").value(findPostResponse.getUserName()))
                .andDo(print())
                .andDo(document("find-post",
                        pathParameters(
                                parameterWithName("id").description("게시글 Id")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 결과"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 Id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 Id"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("게시글 작성자 Id"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void shouldUpdatePost() throws Exception {
        // given
        Long request = 1L;
        Long response = 1L;
        PostDTO.UpdateRequest postUpdateRequest = PostDTO.UpdateRequest.builder()
                .title("title")
                .content("content")
                .build();

        when(postService.updatePost(any(Long.class), any(PostDTO.UpdateRequest.class)))
                .thenReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(
                put("/posts/{id}", request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(postUpdateRequest)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("status").value(200))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("data").value(response))
                .andDo(print())
                .andDo(document("update-post",
                        pathParameters(
                                parameterWithName("id").description("게시글 Id")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정 게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정 게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("응답 결과")
                        )
                ));
    }

    @Test
    @DisplayName("페이지로 Post 조회 성공")
    void findPostsPage() throws Exception {
        // given
        PageDTO.Request pageRequest = new PageDTO.Request(0, 5, 10);
        User user = User.builder().id(1L).name("name").age(10).hobby("hobby").build();
        List<PostDTO.FindResponse> findPosts = new ArrayList<>();
        for (long i = 1; i <= 2; i++) {
            findPosts.add(createPostFindResponseDto(i, "title " + i, "content " + i, user.getId(), "name " + i));
        }

        PageDTO.Response<Post, PostDTO.FindResponse> response
                = new PageDTO.Response<>(findPosts, false, true, List.of(1, 2), 1);

        when(postService.findAllPostsPage(any(PageDTO.Request.class)))
                .thenReturn(response);


        // when
        ResultActions resultActions = mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(pageRequest)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value(200))
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.data[0].id").value(findPosts.get(0).getId()))
                .andExpect(jsonPath("data.data[0].title").value(findPosts.get(0).getTitle()))
                .andExpect(jsonPath("data.data[0].content").value(findPosts.get(0).getContent()))
                .andExpect(jsonPath("data.data[0].userId").value(findPosts.get(0).getUserId()))
                .andExpect(jsonPath("data.data[0].userName").value(findPosts.get(0).getUserName()))
                .andExpect(jsonPath("data.data[1].id").value(findPosts.get(1).getId()))
                .andExpect(jsonPath("data.data[1].title").value(findPosts.get(1).getTitle()))
                .andExpect(jsonPath("data.data[1].content").value(findPosts.get(1).getContent()))
                .andExpect(jsonPath("data.data[1].userId").value(findPosts.get(1).getUserId()))
                .andExpect(jsonPath("data.data[1].userName").value(findPosts.get(1).getUserName()))
                .andDo(print())
                .andDo(document("find-posts-page",
                        requestFields(
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("게시글 페이지"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("한 페이지당 게시글 수"),
                                fieldWithPath("totalPage").type(JsonFieldType.NUMBER).description("표시할 총 페이지 수")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.data").type(JsonFieldType.ARRAY).description("조회된 게시글"),
                                fieldWithPath("data.data[0].id").type(JsonFieldType.NUMBER).description("게시글 Id"),
                                fieldWithPath("data.data[0].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.data[0].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.data[0].userId").type(JsonFieldType.NUMBER).description("게시글 작성자 Id"),
                                fieldWithPath("data.data[0].userName").type(JsonFieldType.STRING).description("게시글 작성자 이름"),
                                fieldWithPath("data.pages").type(JsonFieldType.ARRAY).description("화면에 표시될 페이지 목록"),
                                fieldWithPath("data.nowPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("data.prev").type(JsonFieldType.BOOLEAN).description("이전 페이지 목록 여부"),
                                fieldWithPath("data.next").type(JsonFieldType.BOOLEAN).description("다음 페이지 목록 여부")
                        )
                ));
    }

    private PostDTO.FindResponse createPostFindResponseDto(Long id, String title, String content, Long userId, String userName) {
        return PostDTO.FindResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .userId(userId)
                .userName(userName)
                .build();
    }

}