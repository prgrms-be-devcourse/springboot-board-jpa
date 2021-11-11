package com.misson.jpa_board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misson.jpa_board.domain.Hobby;
import com.misson.jpa_board.dto.PostCreateRequest;
import com.misson.jpa_board.dto.PostDto;
import com.misson.jpa_board.dto.UserDto;
import com.misson.jpa_board.service.PostService;
import com.misson.jpa_board.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@AutoConfigureRestDocs
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long postId;

    private PostCreateRequest postCreateRequest;

    @BeforeEach
    void saveEntity() {
        UserDto createUser = UserDto.builder()
                .age(28)
                .name("choi")
                .hobby(new Hobby("coding"))
                .build();
        UserDto insertedUser = userService.save(createUser);

        postCreateRequest = PostCreateRequest.builder()
                .title("제목학원")
                .content("내용은 뭐로하지")
                .userId(insertedUser.getId())
                .build();

        postId = postService.save(postCreateRequest);
    }

    @Test
    @DisplayName("게시글 조회")
    void postFindByIdTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("제목학원")))
                .andExpect(jsonPath("$.content", is("내용은 뭐로하지")))
                .andDo(print())
                .andDo(document("postFindById",
                        pathParameters(parameterWithName("id").description(JsonFieldType.NUMBER).description("postId"))
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 수정")
    void postChanged() throws Exception {
        PostDto postDto = postService.postFindById(postId);
        postDto.changePost("수정된 제목2", "수정된 내용2");

        mockMvc.perform(RestDocumentationRequestBuilders.put("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("수정된 제목2")))
                .andExpect(jsonPath("$.content", is("수정된 내용2")))
                .andDo(print())
                .andDo(document("changePost",
                        pathParameters(parameterWithName("id").description(JsonFieldType.NUMBER).description("postId"))
                        , requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 작성")
    void insertPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .content(objectMapper.writeValueAsString(postCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document(
                        "insertPost",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        ),
                        responseBody()
                ));
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void postFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PageRequest.of(0, 10)))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("findAll"
                        , requestFields(
                                fieldWithPath("offset").type(JsonFieldType.NUMBER).description("해당 페이지에 첫 번째 원소의 수"),
                                fieldWithPath("paged").type(JsonFieldType.BOOLEAN).description("paged"),
                                fieldWithPath("unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("비었는지 여부"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬상태"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬상태"),
                                fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 사이즈")
                        )
                        , responseFields(

                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("게시글 번호"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("글 제목"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("글 내용"),
                                fieldWithPath("content[].userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("empty 여부"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted 여부"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted 여부"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("해당 페이지에 첫 번째 원소의 수"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("페이지화 정보 포함 여부"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("비었는지 여부"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬상태"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬상태"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫번째인지"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막인지"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                        )
                ));
    }
}
