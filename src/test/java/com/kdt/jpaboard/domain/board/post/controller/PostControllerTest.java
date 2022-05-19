package com.kdt.jpaboard.domain.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.jpaboard.domain.board.post.dto.CreatePostDto;
import com.kdt.jpaboard.domain.board.post.dto.PostDto;
import com.kdt.jpaboard.domain.board.post.dto.UpdatePostDto;
import com.kdt.jpaboard.domain.board.post.service.PostService;
import com.kdt.jpaboard.domain.board.user.dto.CreateUserDto;
import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import com.kdt.jpaboard.domain.board.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@DisplayName("게시물 controller 테스트")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    private CreateUserDto userDto;
    private CreatePostDto createPostDto;
    private Long userId;

    @BeforeEach
    void setup() {
        userDto = CreateUserDto.builder()
                .name("beomsic")
                .age(26)
                .hobby("soccer")
                .build();

        userId = userService.save(userDto);

        createPostDto = CreatePostDto.builder()
                .title("테스트")
                .content("테스트")
                .build();
    }

    @AfterEach
    void reset() {
        postService.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void save() throws Exception {
        // Given
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);
        PageRequest page = PageRequest.of(0, 10);
        // When
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userName"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userAge"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userHobby")
                                ),
                        responseFields (
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
        // Then
        Page<PostDto> all = postService.findAll(page);
        assertThat(all.getTotalElements() == 1, is(true));
    }

    @Test
    @DisplayName("모든 게시물 조회 테스트")
    void findAll() throws Exception {
        /// Given
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);
        // When
        postService.save(createPostDto);
        mockMvc.perform(get("/posts")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "createdAt,DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("serverDateTime").exists())
                .andDo(print())
                .andDo(document("post-find-all",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("게시물 리스트"),
                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("작성자 나이"),
                                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("작성자 취미"),

                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),

                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),

                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),

                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),

                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
        // Then

    }

    @Test
    @DisplayName("특정 게시물 조회 테스트")
    void findOne() throws Exception {
        // Given
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);
        // When
        Long postId = postService.save(createPostDto);
        mockMvc.perform(get("/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-find-by-id",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("작성자 나이"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("작성자 취미"),

                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
        // Then
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void testUpdate() throws Exception {
        // Given
        UserDto saveUser = userService.findById(userId);
        createPostDto.setUserDto(saveUser);
        UpdatePostDto updatePostDto = new UpdatePostDto(createPostDto.getTitle(), "안녕하세요");
        // When
        Long postId = postService.save(createPostDto);
        mockMvc.perform(put("/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields (
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
        // Then
        PostDto one = postService.findById(postId);
        assertThat(one.getContent().equals(updatePostDto.getContent()), is(true));
        assertThat(one.getUserDto().getId().equals(userId), is(true));
    }
}