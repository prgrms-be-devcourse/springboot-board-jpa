package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.repository.PostRepository;
import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글이 정상정으로 생성 및 업로드된다")
    void postUpload() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("Saving-A-Post",
                            requestFields(
                                    // Fields of PostDto
                                    fieldWithPath("id").type(JsonFieldType.NULL).description("게시글에 부여되는 ID값. 서버에서 부여되는 값이다."),
                                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                            ),
                            responseFields(
                                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                    fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된 게시글의 ID"),
                                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버가 응답한 시간")
                            )
                        )
                );
    }

    @Test
    @DisplayName("하나의 게시글 정보를 받아올 수 있다")
    void getOnePost() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();
        Long id = postService.save(postDto);

        mockMvc.perform(get("/api/v1/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("Read-A-Post",
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),

                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("조회하려는 게시글의 정보"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("조회하려는 게시글의 ID"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("조회하려는 게시글의 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("조회하려는 게시글의 내용"),

                                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버가 응답한 시간")
                                )
                        )
                );
    }

    @Test
    @DisplayName("모든 게시글 정보를 받아올 수 있다")
    void getAllPost() throws Exception {
        PostDto postDto1 = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();
        PostDto postDto2 = PostDto.builder()
                .title("Test Post 2")
                .content("Content of Test Post 2")
                .build();
        PostDto postDto3 = PostDto.builder()
                .title("Test Post 3")
                .content("Content of Test Post 3")
                .build();
        postService.save(postDto1);
        postService.save(postDto2);
        postService.save(postDto3);

        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("Read-All-Post",
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),

                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("조회하려는 게시글들을 감사고 있는 객체"),
                                        fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("조회하려는 게시글들의 정보"),
                                        fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("조회하려는 게시글의 ID"),
                                        fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("조회하려는 게시글의 제목"),
                                        fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("조회하려는 게시글의 내용"),

                                        fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("Pageable 관련 정보"),
                                        fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("Page 정렬 관련 정보"),
                                        fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("빈 페이지 여부"),
                                        fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("페이지 정렬 여부"),
                                        fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("페이지 미정렬 여부"),
                                        fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("지정된 페이지 및 페이지 사이즈에 따른 page offset"),
                                        fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("Page Number"),
                                        fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("Page Size"),
                                        fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("Paged 여부"),
                                        fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("paged의 역"),

                                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 게시글 수"),
                                        fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("각 페이지 별 사이즈"),
                                        fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                        fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("현재 페이지가 첫 번째 페이지인지 여부"),
                                        fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 게시글 수"),
                                        fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("현재 페이지가 빈 페이지인지 여부"),

                                        // TODO: 문서 보충하기. 왜 sort 객체가 두 군데에 있는지? where? -> data.sort, data.pageable.sort
                                        fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("???"),
                                        fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("???"),
                                        fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("???"),
                                        fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("???"),

                                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버가 응답한 시간")
                                )
                        )
                );
    }

    @Test
    @DisplayName("게시글을 정상적으로 수정할 수 있다")
    void editPost() throws Exception {
        PostDto postDto1 = PostDto.builder()
                .title("Test Post 1")
                .content("Content of Test Post 1")
                .build();
        Long postId = postService.save(postDto1);

        PostDto dtoForUpdate = PostDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();

        mockMvc.perform(post("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoForUpdate))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("Edit-A-Post",
                                requestFields(
                                        // Fields of PostDto
                                        fieldWithPath("id").type(JsonFieldType.NULL).description("게시글의 ID값. 여기서는 이용되지 않는다."),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정하려는 게시글 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정하려는 게시글 내용")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("수정된 게시글의 ID. 기존 ID와 동일하다."),
                                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("서버가 응답한 시간")
                                )
                        )
                );
    }
}