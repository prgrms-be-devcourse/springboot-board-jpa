package com.blessing333.boardapi.controller;

import com.blessing333.boardapi.TestDataProvider;
import com.blessing333.boardapi.controller.dto.PostCreateCommand;
import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.controller.dto.PostUpdateCommand;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.blessing333.boardapi.entity.exception.PostCreateFailException;
import com.blessing333.boardapi.entity.exception.PostUpdateFailException;
import com.blessing333.boardapi.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestDataProvider dataProvider;
    @Autowired
    private PostRepository postRepository;

    private User testUser;
    private Post testPost;

    @BeforeAll
    void init() {
        testUser = dataProvider.insertUserToDb("tester", 26);
        testPost = dataProvider.insertPostToDb("title", "content", testUser);
    }

    @DisplayName("페이징 조회 테스트")
    @Test
    void getPosts() throws Exception {
        dataProvider.insert20PostToDB(testUser);

        mockMvc.perform(get("/api/v1/posts")
                        .param("page", "2")
                        .param("size", "5")
                        .param("sort", "createdAt,desc"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andDo(document("post-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("page").description("The page to retrieve"),
                                parameterWithName("size").description("The size per page"),
                                parameterWithName("sort").description("sort")
                        ),
                        responseFields(
                                fieldWithPath("content.[]").type(JsonFieldType.ARRAY).description("content"),
                                fieldWithPath("content.[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("content.[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content.[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("content.[].createdAt").type(JsonFieldType.STRING).description("date"),
                                fieldWithPath("content.[].writer").type(JsonFieldType.OBJECT).description("writer"),
                                fieldWithPath("content.[].writer.createdAt").type(JsonFieldType.STRING).description("writer create"),
                                fieldWithPath("content.[].writer.id").type(JsonFieldType.NUMBER).description("writer id"),
                                fieldWithPath("content.[].writer.name").type(JsonFieldType.STRING).description("writer name"),
                                fieldWithPath("content.[].writer.age").type(JsonFieldType.NUMBER).description("writer age"),
                                fieldWithPath("content.[].writer.hobby").type(JsonFieldType.NULL).description("writer hobby"),
                                fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("paging"),
                                fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("paging"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("total element"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("number of element"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("total page"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("sort").type(JsonFieldType.OBJECT).description("number"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                        )
                ));
    }

    @DisplayName("id로 단일 게시글 조회 요청시 게시글 정보 반환")
    @Test
    void getPostInformation() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/posts/" + testPost.getId()))
                .andExpect(status().is2xxSuccessful())
                .andDo(document("post-inquiry",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdDate"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("writer.createdAt").type(JsonFieldType.STRING).description("writer create"),
                                fieldWithPath("writer.id").type(JsonFieldType.NUMBER).description("writer id"),
                                fieldWithPath("writer.name").type(JsonFieldType.STRING).description("writer name"),
                                fieldWithPath("writer.age").type(JsonFieldType.NUMBER).description("writer age"),
                                fieldWithPath("writer.hobby").type(JsonFieldType.NULL).description("writer hobby")
                        )
                ))
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        PostInformation result = mapper.readValue(jsonString, PostInformation.class);

        assertThat(result.getId()).isEqualTo(testPost.getId());
        assertThat(result.getTitle()).isEqualTo(testPost.getTitle());
        assertThat(result.getContent()).isEqualTo(testPost.getContent());
        assertThat(result.getWriter()).isEqualTo(testPost.getWriter());
    }

    @DisplayName("존재하지 않는 게시글 조회 요청 시 PostNotFoundException 발생시키고 응답으로 404 Not found code 반환")
    @Test
    void getPostInformationWithInvalidId() throws Exception {
        String invalidId = "-1";
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/posts/" + invalidId))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("게시글 생성 요청시 게시글을 생성하고 생성된 게시글 정보를 반환한다")
    @Test
    void registerPostTest() throws Exception {
        String title = "title title";
        String content = "content content";
        PostCreateCommand commands = new PostCreateCommand(title, content, testUser.getId());
        String json = mapper.writeValueAsString(commands);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(document("post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdDate"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("writer.createdAt").type(JsonFieldType.STRING).description("writer create"),
                                fieldWithPath("writer.id").type(JsonFieldType.NUMBER).description("writer id"),
                                fieldWithPath("writer.name").type(JsonFieldType.STRING).description("writer name"),
                                fieldWithPath("writer.age").type(JsonFieldType.NUMBER).description("writer age"),
                                fieldWithPath("writer.hobby").type(JsonFieldType.NULL).description("writer hobby")
                        )
                ))
                .andReturn();

        String jsonString = mvcResult.getResponse().getContentAsString();
        PostInformation result = mapper.readValue(jsonString, PostInformation.class);
        Optional<Post> found = postRepository.findById(result.getId());
        Assertions.assertDoesNotThrow(() -> found.orElseThrow());
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getWriter()).isEqualTo(testPost.getWriter());
    }

    @DisplayName("잘못된 필드로 게시글 생성을 요청하면 MethodArgumentNotValidException 발생, 400코드 반환")
    @Test
    void registerPostWithInvalidValue() throws Exception {
        String title = "t"; // 제목 길이 2 미만
        String content = ""; // 내용 없음
        PostCreateCommand commands = new PostCreateCommand(title, content, testUser.getId());
        String json = mapper.writeValueAsString(commands);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getClass().isAssignableFrom(MethodArgumentNotValidException.class));
    }

    @DisplayName("잘못된 UserId로 게시글 생성 요청시 PostCreateFailException 생성, 400에러 코드 반환")
    @Test
    void registerPostWithInvalidUserId() throws Exception {
        String title = "title title";
        String content = "content content";
        Long invalidId = -1L;
        PostCreateCommand commands = new PostCreateCommand(title, content, invalidId);
        String json = mapper.writeValueAsString(commands);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getClass().isAssignableFrom(PostCreateFailException.class));
    }

    @DisplayName("게시글 수정 요청시 게시글을 수정하고 수정된 게시글 정보를 반환한다")
    @Test
    void update() throws Exception {
        String changedTitle = "change";
        String changedContent = "changed content";
        PostUpdateCommand postUpdateCommand = new PostUpdateCommand(testPost.getId(), changedTitle, changedContent);
        String jsonString = mapper.writeValueAsString(postUpdateCommand);
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().is2xxSuccessful())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdDate"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("writer.createdAt").type(JsonFieldType.STRING).description("writer create"),
                                fieldWithPath("writer.id").type(JsonFieldType.NUMBER).description("writer id"),
                                fieldWithPath("writer.name").type(JsonFieldType.STRING).description("writer name"),
                                fieldWithPath("writer.age").type(JsonFieldType.NUMBER).description("writer age"),
                                fieldWithPath("writer.hobby").type(JsonFieldType.NULL).description("writer hobby")
                        )
                ))
                .andReturn();

        String resultString = mvcResult.getResponse().getContentAsString();
        PostInformation postInformation = mapper.readValue(resultString, PostInformation.class);
        assertThat(postInformation.getTitle()).isEqualTo(changedTitle);
        assertThat(postInformation.getContent()).isEqualTo(changedContent);
    }

    @DisplayName("잘못된 게시글id로 수정 요청시 PostUpdateFailException 발생, 400코드 반환")
    @Test
    void updateWithInvalidId() throws Exception {
        Long invalidId = -1L;
        PostUpdateCommand postUpdateCommand = new PostUpdateCommand(invalidId, "change", "content change");
        String jsonString = mapper.writeValueAsString(postUpdateCommand);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertTrue(Objects.requireNonNull(mvcResult.getResolvedException()).getClass().isAssignableFrom(PostUpdateFailException.class));
        assertNotNull(mvcResult.getResolvedException().getMessage());
    }

}