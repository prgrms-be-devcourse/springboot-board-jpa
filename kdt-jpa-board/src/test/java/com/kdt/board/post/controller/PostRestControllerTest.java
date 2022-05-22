package com.kdt.board.post.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.post.domain.Post;
import com.kdt.board.post.dto.request.PostCreateRequestDto;
import com.kdt.board.post.dto.request.PostEditRequestDto;
import com.kdt.board.post.repository.PostRepository;
import com.kdt.board.user.domain.User;
import com.kdt.board.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("페이징 적용된 모든 게시물 조회")
    void getAllPostsTest() throws Exception {
        User user = new User("CHOI", 27);
        userRepository.save(user);
        for (int i = 0; i < 20; i++) {
            postRepository.save(new Post("title" + i, "content" + i, user));
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andDo(MockMvcRestDocumentation.document("get-posts",
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                    fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("content.desc"),
                    fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("postId"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pageable"),
                    fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                    fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                    fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                    fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                    fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                    fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                    fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                    fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),
                    fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                    fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                    fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                    fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                    fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),
                    fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                    fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                    fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),
                    fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                    fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                    fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                    fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                )
            ));
    }

    @Test
    @DisplayName("게시물 단건 조회")
    void getOneTest() throws Exception {
        User user = new User("CHOI", 27);
        userRepository.save(user);
        Post post = postRepository.save(new Post("title", "content", user));

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andDo(MockMvcRestDocumentation.document("get-post",
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("postId"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                )
            ));
    }

    @Test
    @DisplayName("게시물 등록 성공")
    void savePostTest() throws Exception {
        User user = new User("CHOI", 27);
        userRepository.save(user);
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto(user.getId(), "title",
            "content");

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequestDto)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andDo(MockMvcRestDocumentation.document("save-post",
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                )
            ));
    }

    @Test
    @DisplayName("게시물 수정 성공")
    void editTest() throws Exception {
        User user = new User("CHOI", 27);
        userRepository.save(user);
        Post post = postRepository.save(new Post("title", "content", user));
        PostEditRequestDto postEditRequestDto = new PostEditRequestDto(post.getId(), "updateTitle",
            "updateContent");

        mockMvc.perform(MockMvcRequestBuilders.put("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEditRequestDto)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andDo(MockMvcRestDocumentation.document("edit-post",
                requestFields(
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                )
            ));
    }

    @Test
    @DisplayName("존재하지 않는 post 조회시 NotFoundException 발생")
    void nonFoundExceptionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andDo(MockMvcResultHandlers.print())
            .andDo(MockMvcRestDocumentation.document("get-post-exception",
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data").type(JsonFieldType.STRING).description("data"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                )
            ));
    }
}