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
import org.assertj.core.api.Assertions;
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
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
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
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 포스트 URI")
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
        Pageable pageable =  PageRequest.of(1,2);
        for (int i = 0; i < 10; i++) {
            Long postId = postService.createPost(postDto);
            postService.updatePost(new PostDTO.UpdateRequest("제목"+i,"컨텐츠"+i),postId);
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
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지면 true 입니다."),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지의 수 입니다."),
                                fieldWithPath("pageNumber").type(JsonFieldType.VARIES).description("현재 페이지의 위치입니다."),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫페이지면 true 입니다.")
                                )));

        //then
    }

    //toDo : 수정 테스트
    @Test
    void updateCallTest() throws Exception {
        //given
        Long postId = postService.createPost(postDto);
        PostDTO.UpdateRequest updateRequest = new PostDTO.UpdateRequest("수정된 타이틀","수정된 컨텐츠 내용");
        //when
        mockMvc.perform(post("/posts/{id}",postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("update-post",requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("업데이트 될 타이틀"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("업데이트 될 내용")
                        ))
                );
        //then
        var updatedPost = postService.findById(postId);
        assertThat(postDto).isNotEqualTo(updatedPost);
        assertThat(updatedPost.title()).isEqualTo("수정된 타이틀");
        assertThat(updatedPost.content()).isEqualTo("수정된 컨텐츠 내용");
    }

}