package org.programmers.springboardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.programmers.springboardjpa.domain.post.api.PostApi;
import org.programmers.springboardjpa.domain.post.dto.PostRequest;
import org.programmers.springboardjpa.domain.post.dto.PostResponse.PostResponseDto;
import org.programmers.springboardjpa.domain.post.service.PostDefaultService;
import org.programmers.springboardjpa.domain.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(PostApi.class)
class PostApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostDefaultService postService;

    @Test
    void save() throws Exception {
        PostResponseDto postResponseDto = new PostResponseDto(
                1L,
                "(속보)",
                "빠르게 걷는다는 뜻",
                new UserDto.UserResponse(
                        2L,
                        "익명1",
                        20,
                        "인터넷 서핑"
                )
        );

        //given
        given(this.postService.savePost(any(PostRequest.PostCreateRequest.class)))
                .willReturn(postResponseDto);

        //when
        PostRequest.PostCreateRequest postCreateRequestDto = new PostRequest.PostCreateRequest(
                "(속보)",
                "빠르게 걷는다는 뜻",
                new UserDto.UserRequest(
                        "익명1",
                        20,
                        "인터넷 서핑"
                )
        );
        ResultActions result = this.mockMvc.perform(
                post("/api/v1/posts")
                        .content(this.objectMapper.writeValueAsString(postCreateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("posts-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("유저"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("유저 취미")
                        )
                        ,
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("유저"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("유저 취미")
                        )
                ));
    }

    @Test
    void getAllPost() throws Exception {
        //given
        PostResponseDto postResponseDto = new PostResponseDto(
                3L,
                "여기 왔으면 이 글부터 봐라",
                "(독수리)",
                new UserDto.UserResponse(
                        4L,
                        "익명3",
                        29,
                        "인터넷 서핑"
                )
        );
        List<PostResponseDto> postResponseList = new ArrayList<>();
        postResponseList.add(postResponseDto);

        given(this.postService.getPostList(any(Pageable.class)))
                .willReturn(postResponseList);

        //when
        ResultActions result = this.mockMvc.perform(get("/api/v1/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("posts-getAll",
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("크기")
                        ),
                        responseFields(
                                fieldWithPath("[0].id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("[0].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("[0].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[0].userDto").type(JsonFieldType.OBJECT).description("유저"),
                                fieldWithPath("[0].userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("[0].userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("[0].userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("[0].userDto.hobby").type(JsonFieldType.STRING).description("유저 취미")
                        )
                ));
    }

    @Test
    void getPost() throws Exception {
        //given
        PostResponseDto postResponseDto = new PostResponseDto(
                1L,
                "(속보)",
                "빠르게 걷는다는 뜻",
                new UserDto.UserResponse(
                        2L,
                        "익명1",
                        20,
                        "인터넷 서핑"
                )
        );

        given(this.postService.getPost(1L))
                .willReturn(postResponseDto);

        //when
        ResultActions result = this.mockMvc.perform(
                get("/api/v1/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("posts-get",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("유저"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("유저 취미")
                        )
                ));
    }

    @Test
    void update() throws Exception {
        //given
        PostResponseDto postResponseDto = new PostResponseDto(
                3L,
                "안녕하세요",
                "안녕히 계세요",
                new UserDto.UserResponse(
                        4L,
                        "익명3",
                        29,
                        "인터넷 서핑"
                )
        );

        given(postService.updatePost(eq(3L), any(PostRequest.PostUpdateRequest.class)))
                .willReturn(postResponseDto);

        //when
        PostRequest.PostUpdateRequest updateRequestDto = new PostRequest.PostUpdateRequest(
                "안녕하세요",
                "안녕히 계세요"
        );
        ResultActions result = this.mockMvc.perform(
                put("/api/v1/posts/{id}", 3L)
                        .content(this.objectMapper.writeValueAsString(updateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("posts-update",
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("유저"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("유저 취미")
                        )
                ));
    }
}