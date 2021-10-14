package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.PostDeleteRequestDto;
import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostUpdateRequestDto;
import com.devco.jpaproject.controller.dto.UserRequestDto;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.PostService;
import com.devco.jpaproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs // mock을 사용하면 api문서화 까지도 가능함.
@AutoConfigureMockMvc  // MockMvc를 주입받기 위함: 스프링 애플리케이션을 띄우지 않고 mock으로 주입받음
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    UserRepository userRepository;

    Long globalPostId;
    Long globalWriterId;

    @BeforeEach
    void setUp() throws UserNotFoundException {
        var writerDto = UserRequestDto.builder()
                .age(12)
                .name("jihun-each")
                .hobby("hobby-each")
                .build();

        globalWriterId = userService.insert(writerDto);

        var postRequestDto = PostRequestDto.builder()
                .writerId(globalWriterId)
                .content("new-content")
                .title("new-title")
                .build();

        globalPostId = postService.insert(postRequestDto);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글을 삽입 api를 수행할 수 있다.")
    void insertTest() throws Exception {
        //given : request body 만들기
        var requestDto = PostRequestDto.builder()
                .writerId(globalWriterId)
                .content("new-content")
                .title("new-title")
                .build();

        //when //then
        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))  // dto를 String형태로 변환해서 requestbody에 삽입
                .andExpect(status().isOk())  // 기대되는 결과 200 일 것
                .andDo(print())  // 결과를 프린트함
                // 문서화를 위한 설정
                .andDo(document("post-insert",
                        requestFields(
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("writerId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("등록된 게시물 ID"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 id로 단건 게시물을 조회할 수 있다.")
    void findByIdTest() throws Exception {
        //given
        Long toBeFoundPostId = globalPostId;

        //when, then
        mockMvc.perform(get("/api/v1/post/{id}", toBeFoundPostId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                // 문서화 설정
                .andDo(document("post-find-by-id",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data.writerDto.name").type(JsonFieldType.STRING).description("작성자이름"),
                                fieldWithPath("data.writerDto.age").type(JsonFieldType.NUMBER).description("작성자나이"),
                                fieldWithPath("data.writerDto.hobby").type(JsonFieldType.STRING).description("작성자취미"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시물id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시물제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시물본문"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("페이징처리 기반으로 전체 게시물을 조회할 수 있다.")
    void findAllByPageTest() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                // 문서화 설정
                .andDo(document("posts-page",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),

                                fieldWithPath("data.content[].writerDto.name").type(JsonFieldType.STRING).description("작성자id"),
                                fieldWithPath("data.content[].writerDto.age").type(JsonFieldType.NUMBER).description("작성자age"),
                                fieldWithPath("data.content[].writerDto.hobby").type(JsonFieldType.STRING).description("작성자hobby"),

                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("게시물id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("본문"),

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

                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),

                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),

                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글을 삭제할 수 있다.")
    void deleteOneTest() throws Exception {
        //given
        var dto = PostDeleteRequestDto.builder()
                .postId(globalPostId)
                .writerId(globalWriterId)
                .build();

        //when then
        mockMvc.perform(delete("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))  // dto를 String형태로 변환해서 requestbody에 삽입
                .andExpect(status().isOk())  // 기대되는 결과 200 일 것
                .andDo(print())  // 결과를 프린트함
                // 문서화를 위한 설정
                .andDo(document("post-delete",
                        requestFields(
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("writerId"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId")
                        )
                ));
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다.")
    void updateTest() throws Exception {
        //given
        var dto = PostUpdateRequestDto.builder()
                .postId(globalPostId)
                .content("content-update")
                .title("title-update")
                .writerId(globalWriterId)
                .build();

        //when then
        mockMvc.perform(patch("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())  // 기대되는 결과 200 일 것
                .andDo(print())  // 결과를 프린트함
                // 문서화를 위한 설정
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("writerId"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 본문"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목")
                        )
                ));
    }

}