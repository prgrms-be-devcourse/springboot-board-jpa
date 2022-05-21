package org.programmers.springbootboardjpa.web.controller.api.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.springbootboardjpa.domain.user.Age;
import org.programmers.springbootboardjpa.domain.user.Name;
import org.programmers.springbootboardjpa.domain.user.Password;
import org.programmers.springbootboardjpa.domain.user.User;
import org.programmers.springbootboardjpa.repository.user.UserRepository;
import org.programmers.springbootboardjpa.service.post.PostService;
import org.programmers.springbootboardjpa.web.CustomizedAutoConfigureMockMvc;
import org.programmers.springbootboardjpa.web.dto.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO: 포맷팅 적용
@AutoConfigureRestDocs
@CustomizedAutoConfigureMockMvc
@SpringBootTest
class PostApiControllerV1Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${spring.data.web.pageable.default-page-size}")
    static Integer defaultPageSize;

    @Value("${spring.data.web.pageable.max-page-size}")
    static Integer maxPageSize;

    static User testUser;

    static RequestFieldsSnippet createFormRequestFieldSnippet = requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"));

    static ResponseFieldsSnippet idResponseFieldSnippet = responseFields(fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"));

    static FieldDescriptor[] postDtoFieldDescription = new FieldDescriptor[]{
            fieldWithPath("postId").type(JsonFieldType.NUMBER).description("글 ID"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("작성자 닉네임"),
            fieldWithPath("createdDate").type(JsonFieldType.STRING).description("글 등록 시간"),
            fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("글 수정 시간")};

    static ResponseFieldsSnippet postResponseFieldSnippet = responseFields(postDtoFieldDescription);

    static RequestFieldsSnippet updateFormRequestFieldSnippet = requestFields(
            fieldWithPath("postId").type(JsonFieldType.NUMBER).description("글 ID"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"));

    static RequestParametersSnippet pageRequestParametersSnippet = requestParameters(
            parameterWithName("page").optional().description("요청 페이지 번호"),
            parameterWithName("size").optional().description("한 페이지 당 글 수\n최대 크기: " + maxPageSize + "\n미지정시 기본 크기" + defaultPageSize));

    static ResponseFieldsSnippet postPageResponseFieldSnippet = responseFields(
            fieldWithPath("content.[]").type(JsonFieldType.ARRAY).description("글 정보의 배열"),
            subsectionWithPath("pageable").ignored(),
            fieldWithPath("number").type(JsonFieldType.NUMBER).description("해당 페이지 번호"),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description("한 페이지 당 글 수"),
            subsectionWithPath("sort").type(JsonFieldType.OBJECT).description("정렬 방식"),
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
            fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 글 수"),
            fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("해당 페이지에 조회된 글 수"),
            fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
            fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
            fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("텅 빈 페이지인지"))
            .andWithPrefix("content.[].", postDtoFieldDescription);

    @BeforeEach
    void setup() {
        testUser = new User("Leesang", new Password("wings0417"), new Name("해경", "김"), new Age(LocalDate.of(1910, 9, 23)));
        testUser = userRepository.save(testUser);
    }

    @Test
    void createPost() throws Exception {
        PostCreateForm postCreateForm = new PostCreateFormV1("오감도", "13인의아해가도로로질주하오", testUser.getUserId());

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateForm)))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/api/v1/posts/*"))
                .andDo(print())
                .andDo(document("createPost", createFormRequestFieldSnippet, idResponseFieldSnippet));
    }

    @Test
    void showPost() throws Exception {
        PostCreateForm postCreateForm = new PostCreateFormV1("오감도", "13인의아해가도로로질주하오", testUser.getUserId());
        Long postId = postService.writePost(postCreateForm);

        mockMvc.perform(get("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(PostDtoV1.from(postService.findPostWithPostId(postId)))))
                .andDo(print())
                .andDo(document("showPost", postResponseFieldSnippet));
    }

    @Test
    void editPost() throws Exception {
        PostCreateForm postCreateForm = new PostCreateFormV1("오감도", "13인의아해가도로로질주하오", testUser.getUserId());
        Long postId = postService.writePost(postCreateForm);

        PostUpdateForm postUpdateForm = new PostUpdateFormV1(postId, "거울", "거울속에는소리가없소\n저렇게까지조용한세상은참없을것이오\n", testUser.getUserId());

        mockMvc.perform(patch("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateForm)))
                .andExpect(status().isOk())
                //TODO: PR 포인트6
//                .andExpect(content().string(objectMapper.writeValueAsString(PostDtoV1.from(postService.findPostWithPostId(postId)))))
                .andDo(print())
                .andDo(document("editPost", updateFormRequestFieldSnippet, postResponseFieldSnippet));
    }

    @Test
    void deletePost() throws Exception {
        PostCreateForm postCreateForm = new PostCreateFormV1("오감도", "13인의아해가도로로질주하오", testUser.getUserId());
        Long postId = postService.writePost(postCreateForm);

        mockMvc.perform(delete("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("deletePost"));
    }

    @Test
    void showPostPaged() throws Exception {
        PostCreateForm postCreateForm = new PostCreateFormV1("오감도", "13인의아해가도로로질주하오", testUser.getUserId());
        postService.writePost(postCreateForm);

        mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("showPostPaged", pageRequestParametersSnippet, postPageResponseFieldSnippet));
    }
}