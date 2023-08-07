package prgrms.board.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import prgrms.board.post.application.dto.request.PostSaveRequest;
import prgrms.board.post.application.dto.request.PostUpdateRequest;
import prgrms.board.post.domain.Post;
import prgrms.board.post.domain.PostRepository;
import prgrms.board.user.domain.User;
import prgrms.board.user.domain.UserRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.JsonFieldType.VARIES;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("고예성", 28);
        userRepository.save(user);
    }

    @Test
    @DisplayName("게시물 저장 성공 테스트")
    void savePostTest() throws Exception {
        // given
        var request = new PostSaveRequest(
                user.getId(),
                "테스트테스트",
                "에이요요요 마잌쳌"
        );

        // when, then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("Post-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId")
                                        .type(NUMBER)
                                        .description("user_id"),
                                fieldWithPath("title")
                                        .type(STRING)
                                        .description("title"),
                                fieldWithPath("content")
                                        .type(STRING)
                                        .description("content")
                        ),
                        responseFields(
                                fieldWithPath("postId")
                                        .type(NUMBER)
                                        .description("post_id"),
                                fieldWithPath("userId")
                                        .type(NUMBER)
                                        .description("user_id"),
                                fieldWithPath("createdAt")
                                        .type(VARIES)
                                        .description("created_at")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 전체조회 성공 테스트")
    void findAllTest() throws Exception {
        // given
        Post post1 = new Post(
                "테스트 제목1",
                "테스트 내용1"
        );
        post1.updateUser(user);
        postRepository.save(post1);

        Post post2 = new Post(
                "테스트 제목2",
                "테스트 내용2"
        );
        post2.updateUser(user);
        postRepository.save(post2);

        // when, then
        mockMvc.perform(get("/api/v1/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("Post-find-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("content[].createdBy")
                                        .type(STRING)
                                        .description("created_by"),
                                fieldWithPath("content[].title")
                                        .type(STRING)
                                        .description("title"),
                                fieldWithPath("content[].content")
                                        .type(STRING)
                                        .description("content"),
                                fieldWithPath("content[].createdAt")
                                        .type(VARIES)
                                        .description("created_at"),
                                fieldWithPath("pageable.sort.empty")
                                        .type(BOOLEAN)
                                        .description("pageable.sort.empty"),
                                fieldWithPath("pageable.sort.unsorted")
                                        .type(BOOLEAN)
                                        .description("pageable.sort.unsorted"),
                                fieldWithPath("pageable.sort.sorted")
                                        .type(BOOLEAN)
                                        .description("pageable.sort.sorted"),
                                fieldWithPath("pageable.offset")
                                        .type(NUMBER)
                                        .description("pageable.offset"),
                                fieldWithPath("pageable.pageNumber")
                                        .type(NUMBER)
                                        .description("pageable.pageNumber"),
                                fieldWithPath("pageable.pageSize")
                                        .type(NUMBER)
                                        .description("pageable.pageSize"),
                                fieldWithPath("pageable.paged")
                                        .type(BOOLEAN)
                                        .description("pageable.paged"),
                                fieldWithPath("pageable.unpaged")
                                        .type(BOOLEAN)
                                        .description("pageable.unpaged"),
                                fieldWithPath("last")
                                        .type(BOOLEAN)
                                        .description("last"),
                                fieldWithPath("totalPages")
                                        .type(NUMBER)
                                        .description("total_pages"),
                                fieldWithPath("totalElements")
                                        .type(NUMBER)
                                        .description("total_elements"),
                                fieldWithPath("first")
                                        .type(BOOLEAN)
                                        .description("first"),
                                fieldWithPath("size")
                                        .type(NUMBER)
                                        .description("size"),
                                fieldWithPath("number")
                                        .type(NUMBER)
                                        .description("number"),
                                fieldWithPath("sort.empty")
                                        .type(BOOLEAN)
                                        .description("sort.empty"),
                                fieldWithPath("sort.unsorted")
                                        .type(BOOLEAN)
                                        .description("sort.unsorted"),
                                fieldWithPath("sort.sorted")
                                        .type(BOOLEAN)
                                        .description("sort.sorted"),
                                fieldWithPath("numberOfElements")
                                        .type(NUMBER)
                                        .description("number_of_elements"),
                                fieldWithPath("empty")
                                        .type(BOOLEAN)
                                        .description("empty")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 단건 조회 성공 테스트")
    void findOneSuccessTest() throws Exception {

        // given
        Post post = new Post(
                "테스트 제목1",
                "테스트 내용1"
        );
        post.updateUser(user);
        postRepository.save(post);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-find-by-id",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post Id")),
                        responseFields(
                                fieldWithPath("createdBy")
                                        .type(STRING)
                                        .description("created_by"),
                                fieldWithPath("title")
                                        .type(STRING)
                                        .description("title"),
                                fieldWithPath("content")
                                        .type(STRING)
                                        .description("content"),
                                fieldWithPath("createdAt")
                                        .type(VARIES)
                                        .description("created_at")
                        )));
    }

    @Test
    @DisplayName("게시물 수정 성공 테스트")
    void updatePostSuccessTest() throws Exception {
        // given
        Post post = new Post(
                "수정 테스트1",
                "수정됨욯"
        );
        post.updateUser(user);
        postRepository.save(post);

        PostUpdateRequest request = new PostUpdateRequest(
                "수정 테스트1",
                "수정됨욯"
        );

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/posts/{id}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post Id")),
                        requestFields(
                                fieldWithPath("title")
                                        .type(STRING)
                                        .description("updated_title"),
                                fieldWithPath("content")
                                        .type(STRING)
                                        .description("updated_content")
                        ),
                        responseFields(
                                fieldWithPath("postId")
                                        .type(NUMBER)
                                        .description("post_id"),
                                fieldWithPath("title")
                                        .type(STRING)
                                        .description("title"),
                                fieldWithPath("content")
                                        .type(STRING)
                                        .description("content"),
                                fieldWithPath("createdBy")
                                        .type(STRING)
                                        .description("created_by"),
                                fieldWithPath("updatedAt")
                                        .type(VARIES)
                                        .description("updated_at")
                        )));
    }
}
