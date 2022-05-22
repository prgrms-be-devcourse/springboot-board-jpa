package com.kdt.board.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.dto.UserDto;
import com.kdt.board.domain.repository.PostRepository;
import com.kdt.board.domain.repository.UserRepository;
import com.kdt.board.domain.service.PostService;
import com.kdt.board.domain.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Long savedUserId;
    Long savedPostId;

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        // given
        UserDto.SaveRequest userDto = new UserDto.SaveRequest("YongHoon", 26, "tennis");
        savedUserId = userService.save(userDto);

        PostDto.SaveRequest postDto = new PostDto.SaveRequest("제목테스트", "내용내용내용내용", savedUserId);

        // when
        savedPostId = postService.save(postDto);

        // then
        assertThat(postRepository.count()).isEqualTo(1L);
    }

    @Test
    @DisplayName("/posts - 모든(All) posts")
    public void getAllPostsTest() throws Exception {
        // given
        PostDto.Response byId = postService.findById(savedPostId);

        // when // then
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAll",
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                                        fieldWithPath("serverDatetime").type(STRING).description("응답시간"),
                                        fieldWithPath("data").type(OBJECT).description("데이터"),
                                        fieldWithPath("data.content").type(ARRAY).description("데이터목록"),
                                        fieldWithPath("data.content[].id").type(NUMBER).description("게시판아이디"),
                                        fieldWithPath("data.content[].title").type(STRING).description("게시판제목"),
                                        fieldWithPath("data.content[].content").type(STRING).description("게시판내용"),
                                        fieldWithPath("data.content[].createdAt").type(STRING).description("content createdAt"),
                                        fieldWithPath("data.content[].updatedAt").type(STRING).description("content updatedAt"),
                                        fieldWithPath("data.content[].createdBy").type(NULL).description("content createdBy"),
                                        fieldWithPath("data.content[].user").type(OBJECT).description("회원"),
                                        fieldWithPath("data.content[].user.id").type(NUMBER).description("회원아이디"),
                                        fieldWithPath("data.content[].user.name").type(STRING).description("회원이름"),
                                        fieldWithPath("data.content[].user.age").type(NUMBER).description("회원나이"),
                                        fieldWithPath("data.content[].user.hobby").type(STRING).description("회원취미"),
                                        fieldWithPath("data.content[].user.createdAt").type(STRING).description("유저 createdAt"),
                                        fieldWithPath("data.content[].user.updatedAt").type(STRING).description("유저 updatedAt"),
                                        fieldWithPath("data.content[].user.createdBy").type(NULL).description("유저 createdBy"),
                                        fieldWithPath("data.pageable").type(OBJECT).description("데이터 페이지"),
                                        fieldWithPath("data.pageable.sort").type(OBJECT).description("sort"),
                                        fieldWithPath("data.pageable.sort.empty").type(BOOLEAN).description("empty 유무"),
                                        fieldWithPath("data.pageable.sort.sorted").type(BOOLEAN).description("sorted"),
                                        fieldWithPath("data.pageable.sort.unsorted").type(BOOLEAN).description("unsorted"),
                                        fieldWithPath("data.pageable.offset").type(NUMBER).description("offset"),
                                        fieldWithPath("data.pageable.pageNumber").type(NUMBER).description("pageNumber"),
                                        fieldWithPath("data.pageable.pageSize").type(NUMBER).description("pageSize"),
                                        fieldWithPath("data.pageable.paged").type(BOOLEAN).description("paged"),
                                        fieldWithPath("data.pageable.unpaged").type(BOOLEAN).description("unpaged"),
                                        fieldWithPath("data.last").type(BOOLEAN).description("last"),
                                        fieldWithPath("data.totalPages").type(NUMBER).description("totalPages"),
                                        fieldWithPath("data.totalElements").type(NUMBER).description("totalElements"),
                                        fieldWithPath("data.size").type(NUMBER).description("size"),
                                        fieldWithPath("data.number").type(NUMBER).description("number"),
                                        fieldWithPath("data.sort").type(OBJECT).description("sort"),
                                        fieldWithPath("data.sort.empty").type(BOOLEAN).description("empty"),
                                        fieldWithPath("data.sort.sorted").type(BOOLEAN).description("sorted"),
                                        fieldWithPath("data.sort.unsorted").type(BOOLEAN).description("unsorted"),
                                        fieldWithPath("data.first").type(BOOLEAN).description("first"),
                                        fieldWithPath("data.numberOfElements").type(NUMBER).description("numberOfElements"),
                                        fieldWithPath("data.empty").type(BOOLEAN).description("empty")
                                )
                        )
                );
    }

    @Test
    @DisplayName("/posts/{id} - id 값에 해당하는 post")
    public void getOneTest() throws Exception {
        // given
        PostDto.Response byId = postService.findById(savedPostId);

        // when // then
        mockMvc.perform(get("/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findOne",
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                                        fieldWithPath("serverDatetime").type(STRING).description("응답시간"),
                                        fieldWithPath("data").type(OBJECT).description("데이터"),
                                        fieldWithPath("data.id").type(NUMBER).description("데이터 아이디"),
                                        fieldWithPath("data.title").type(STRING).description("데이터 제목"),
                                        fieldWithPath("data.content").type(STRING).description("post 내용"),
                                        fieldWithPath("data.createdAt").type(STRING).description("post createdAt"),
                                        fieldWithPath("data.updatedAt").type(STRING).description("post updatedAt"),
                                        fieldWithPath("data.createdBy").type(NULL).description("post createdBy"),
                                        fieldWithPath("data.user").type(OBJECT).description("데이터목록"),
                                        fieldWithPath("data.user.id").type(NUMBER).description("게시판아이디"),
                                        fieldWithPath("data.user.name").type(STRING).description("게시판아이디"),
                                        fieldWithPath("data.user.age").type(NUMBER).description("게시판아이디"),
                                        fieldWithPath("data.user.hobby").type(STRING).description("게시판아이디"),
                                        fieldWithPath("data.user.createdAt").type(STRING).description("게시판아이디"),
                                        fieldWithPath("data.user.updatedAt").type(STRING).description("게시판아이디"),
                                        fieldWithPath("data.user.createdBy").type(NULL).description("게시판아이디"))
                        )
                );
    }

    @Test
    @DisplayName("/ - Post 를 저장")
    public void saveTest() throws Exception {
        // given
        PostDto.SaveRequest saveRequestDto = new PostDto.SaveRequest("새로운제목", "새로운내용", savedUserId);

        // when // then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("post 를 update")
    public void updateTest() throws Exception {
        // given
        PostDto.UpdateRequest updateRequestDto = new PostDto.UpdateRequest(savedPostId, "새로운 제목", "새로운 내용", savedUserId);

        // when // then
        mockMvc.perform(put("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/{id} - id 값에 해당하는 post 제거")
    public void deleteByIdTest() throws Exception {
        // given
        Long deleteId = savedPostId;

        // when // then
        mockMvc.perform(delete("/posts/{id}", deleteId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}