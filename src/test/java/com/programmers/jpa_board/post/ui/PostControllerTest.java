package com.programmers.jpa_board.post.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpa_board.post.application.PostService;
import com.programmers.jpa_board.post.domain.dto.PostDto;
import com.programmers.jpa_board.post.domain.dto.UpdatePostRequest;
import com.programmers.jpa_board.post.infra.PostRepository;
import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private User user;

    @BeforeEach
    void init() {
        user = new User("신범철", 26, "헬스");
        userRepository.save(user);
    }

    @Test
    void 저장_성공() throws Exception {
        //given
        PostDto.CreatePostRequest request = new PostDto.CreatePostRequest("제목-범철", "내용이야", user.getId());

        //when & then
        this.mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간")

                        )
                ));
    }

    @Test
    void 단건_조회_성공() throws Exception {
        //given
        PostDto.CreatePostRequest request = new PostDto.CreatePostRequest("제목-범철", "내용이야", user.getId());
        PostDto.CommonResponse response = postService.save(request);

        //when & then
        this.mockMvc.perform(get("/posts/{id}", response.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간")
                        )
                ));
    }

    @Test
    void 페이징_조회_성공() throws Exception {
        //given
        PostDto.CreatePostRequest request = new PostDto.CreatePostRequest("제목-범철", "내용이야", user.getId());
        postService.save(request);
        PostDto.CreatePostRequest request2 = new PostDto.CreatePostRequest("제목-범철2", "내용이야2", user.getId());
        postService.save(request2);

        //when & then
        this.mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-getPages",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                subsectionWithPath("data").description("데이터"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("배열"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("글 아아디"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.content[].createAt").type(JsonFieldType.STRING).description("생성 시간"),
                                subsectionWithPath("data.pageable").description("페이징 정보"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("빈 값 여부"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬되지 않은 여부"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 가능 여부"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징되지 않은 여부"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 요소 수"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("데이터 크기"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("빈 값 여부"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬되지 않은 여부"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("요소 수"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("빈 값 여부")
                        )
                ));
    }

    @Test
    void 수정_성공() throws Exception {
        //given
        PostDto.CreatePostRequest request = new PostDto.CreatePostRequest("제목-범철", "내용이야", user.getId());
        PostDto.CommonResponse response = postService.save(request);

        UpdatePostRequest updateRequest = new UpdatePostRequest("변경-범철", "변경내용");


        //when & then
        this.mockMvc.perform(put("/posts/{id}", response.id())
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간")
                        )
                ));
    }
}
