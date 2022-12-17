package com.prgrms.be.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.User;
import com.prgrms.be.app.domain.dto.PostDTO;
import com.prgrms.be.app.repository.PostRepository;
import com.prgrms.be.app.repository.UserRepository;
import com.prgrms.be.app.service.PostService;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;


@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private PostDTO.CreateRequest postDto;
    private User user;

    @BeforeEach
    void setup(){
        user = userRepository.save(new User("su yong", 25, "패션"));
        System.out.println("break");
        postDto = new PostDTO.CreateRequest(
                "title",
                "content",
                user.getId()
        );

    }

    //toDo : 생성 테스트
    @Test
    void saveCallTest() throws Exception {
        // given

        // when, then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("포스트 작성자 Id")
                        )));
    }

    //toDo : 단건 조회 테스트
    @Test
    void getOneCallTest() throws Exception {
        // given
        Post post = postRepository.save(new Post("title", "content", user));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-post",
                        pathParameters(
                                parameterWithName("id").description("포스트 id")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("포스트 내용"),
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("포스트 Id"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("포스트 생성 시간"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("포스트 작성자 Id"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("포스트 작성자 이름")
                        )));
    }
//    this.mockMvc.perform(get("/locations/{latitude}/{longitude}", 51.5072, 0.1275)) // (1)
//            .andExpect(status().isOk())
//            .andDo(document("locations", pathParameters( // (2)
//            parameterWithName("latitude").description("The location's latitude"), // (3)
//    parameterWithName("longitude").description("The location's longitude") // (4)
//	)));

    //toDo : 페이징 조회 테스트
    @Test
    void getAllCallTest() throws Exception{
        //given
        Pageable pageable =  PageRequest.of(0,2);
        for (int i = 0; i < 10; i++) {
            Long postId = postService.createPost(postDto);
            postService.updatePost(new PostDTO.UpdateRequest("제목"+i,"컨텐츠"+i,postId));
        }
        //when

        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(pageable.getPageNumber())).param("size",String.valueOf(pageable.getPageSize())))
                .andDo(print()).andExpect(status().isOk()).andDo(document("posts-pagegation",
                        responseFields(
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("포스트 제목"),
                                fieldWithPath("content[].postId").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("포스트가 만들어진 때"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("number").type(JsonFieldType.VARIES).description(""),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("")
                                )));

        //then
    }

    //toDo : 수정 테스트
    @Test
    void updateCallTest() {

    }

}