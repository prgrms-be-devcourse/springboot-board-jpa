package com.prgrms.dev.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.dev.springbootboardjpa.domain.post.PostRepository;
import com.prgrms.dev.springbootboardjpa.domain.user.User;
import com.prgrms.dev.springbootboardjpa.domain.user.UserRepository;
import com.prgrms.dev.springbootboardjpa.dto.PostCreateRequestDto;
import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private PostCreateRequestDto postRequestDto;
    private PostDto postDto;
    private User user;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();

        user = User.builder()
                .name("박씨")
                .age(22)
                .hobby("SPORTS").build();

        userRepository.save(user);

        postRequestDto = PostCreateRequestDto.builder()
                .title("테스트")
                .content("테스트 콘")
                .build();

        postDto = postService.create(postRequestDto);
    } // 분리해보기

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto))
                        .param("userId", String.valueOf(user.getId())))
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        queryParameters(
                                parameterWithName("userId").description("userId")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("responseTime").type(JsonFieldType.STRING).description("response time")
                        )
                ));
    }

    @Test
    void update() throws Exception {
        PostCreateRequestDto requestDto = PostCreateRequestDto.builder()
                .title("수정된 이름")
                .content("수정된 콘텐츠")
                .build();

        mockMvc.perform(post("/api/v1/posts/{id}", postDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("responseTime").type(JsonFieldType.STRING).description("response time")
                        )
                ));
    }

    @Test
    void findOne() throws Exception {
        PostDto findDto = postService.findById(postDto.id());

        mockMvc.perform(get("/api/v1/posts/{id}", postDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(findDto)))
                .andExpect(status().isOk()) //-> 테스트
                .andDo(document("post-findOne",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("responseTime").type(JsonFieldType.STRING).description("response time")
                        )
                ));
    }
    // 인코딩, 해싱, 암호화 ............. end ..
    // 헉...

    // paging -> 수많은 데이터 =>

    @Test
    void getAll() throws Exception {
        PageRequest page = PageRequest.of(0, 10);
        Page<PostDto> findDtos = postService.getAll(page);

        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andDo(document("post-getAll",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("데이터 목록"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("데이터 ID"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("데이터 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("데이터 내용"),
                                fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("데이터의 유저 정보"),
                                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("데이터의 유저 ID"),
                                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("데이터의 유저 이름"),
                                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("데이터의 유저 취미"),
                                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("데이터의 유저 나이"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("responseTime").type(JsonFieldType.STRING).description("응답 시간"),

                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("페이지의 정렬 정보").ignored(),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("새 데이터").ignored(),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("새 데이터").ignored(),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부").ignored(),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수").ignored(),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("총 요소 수").ignored(),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 번째 페이지 여부").ignored(),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("페이지 크기").ignored(),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 번호").ignored(),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 요소 수").ignored(),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("데이터가 비어 있는지 여부").ignored()
                        ) // 필요한 정보만 표현하는 방식을 고민
                ));
    }
}
