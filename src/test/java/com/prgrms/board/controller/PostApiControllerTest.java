package com.prgrms.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostUpdateDto;
import com.prgrms.board.repository.MemberRepository;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.service.MemberService;
import com.prgrms.board.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class PostApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    private Long savedMemberId;
    private Long savedPostId;
    private PostCreateDto postCreateDto;

    @AfterEach
    void clear() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }
    @BeforeEach
    void setup() {
        MemberCreateDto createDto = MemberCreateDto.builder()
                .name("member1")
                .age(26)
                .hobby("youTube")
                .build();

        savedMemberId = memberService.join(createDto);

        postCreateDto = PostCreateDto.builder()
                .title("this is title")
                .content("this is content")
                .writerId(savedMemberId)
                .build();

        savedPostId = postService.register(postCreateDto);
    }

    @Test
    @Transactional
    void 게시글_저장() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("content").description("게시글 내용"),
                                fieldWithPath("writerId").description("작성자 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
        
    }


    @Transactional
    @Test
    void 게시글_단건_조회() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedPostId)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findOne",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.writer").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    void 게시글_수정() throws Exception {
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .postId(savedPostId)
                .title("change title")
                .content("change content")
                .build();

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/posts", postUpdateDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Transactional
    @Test
    void 게시글_페이징_조회() throws Exception {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        int cursorId = 10;
        int size = 10;

        mockMvc.perform(get("/api/v1/posts")
                        .param("cursorId", String.valueOf(cursorId))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(multiValueMap)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("post-paging",
                                requestParameters(
                                        parameterWithName("cursorId").optional().description("cursorId"), // 필수여부 false
                                        parameterWithName("size").optional().description("size") // 필수여부 false
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                                        fieldWithPath("data.postDtoList[].id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                        fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("hasNext"),
                                        fieldWithPath("data.postDtoList[].title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("data.postDtoList[].content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("data.postDtoList[].writer").type(JsonFieldType.STRING).description("작성자")
                                )
                        )
                );
    }
}