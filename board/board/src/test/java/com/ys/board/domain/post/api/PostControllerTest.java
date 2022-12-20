package com.ys.board.domain.post.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.board.domain.post.model.Post;
import com.ys.board.domain.post.repository.PostRepository;
import com.ys.board.domain.user.model.User;
import com.ys.board.domain.user.repository.UserRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @DisplayName("Post 생성 성공 - post /api/v1/posts - Post 생성에 성공한다.")
    @Test
    void createPostSuccess() throws Exception {
        String name = "ys";
        int age = 28;

        User user = User.create(name, age, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";
        long userId = user.getId();
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, userId);

        this.mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.postId").exists())
            .andDo(document("posts-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성 유저 Id")
                ),
                responseFields(
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 Id")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 생성 실패 - post /api/v1/posts - 유저가 없으므로 Post 생성에 실패한다.")
    @Test
    void createPostFailNotFoundUser() throws Exception {

        String title = "title";
        String content = "content";
        long userId = 0L;
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, userId);

        this.mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCreateRequest)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.requestUrl").exists())
            .andExpect(jsonPath("$.method").exists())
            .andDo(document("posts-create-user-notfound",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성 유저 Id")
                ),
                responseFields(
                    fieldWithPath("timeStamp").description("서버 응답 시간"),
                    fieldWithPath("message").description("예외 메시지"),
                    fieldWithPath("requestUrl").description("요청한 url"),
                    fieldWithPath("method").description("요청 method")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 조회 성공 - get /api/v1/posts/{postId} - Post 조회에 성공한다.")
    @Test
    void findPostByIdSuccess() throws Exception {
        String name = "ys";
        int age = 28;

        User user = User.create(name, age, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";

        Post post = Post.create(title, content);
        post.changeUser(user);
        postRepository.save(post);

        this.mockMvc.perform(get("/api/v1/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.postId").value(post.getId()))
            .andExpect(jsonPath("$.title").value(title))
            .andExpect(jsonPath("$.content").value(content))
            .andExpect(jsonPath("$.userId").value(user.getId()))
            .andExpect(jsonPath("$.createdAt").value(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))))
            .andExpect(jsonPath("$.createdBy").value(post.getCreatedBy()))
            .andDo(document("posts-findById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postId").description("Post Id")
                ),
                responseFields(
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 Id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 Id"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
                    fieldWithPath("createdBy").description("게시글 작성자")
                    )
            ))
            .andDo(print());
    }

    @DisplayName("Post 조회 실패 - get /api/v1/posts/{postId} - Post가 없으므로 Post 조회에 실패한다.")
    @Test
    void findPostByIdFailNotFound() throws Exception {
        Long postId = 1L;

        this.mockMvc.perform(get("/api/v1/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.requestUrl").exists())
            .andExpect(jsonPath("$.method").exists())
            .andDo(document("posts-findById-post-NotFound",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postId").description("Post Id")
                ),
                responseFields(
                    fieldWithPath("timeStamp").description("서버 응답 시간"),
                    fieldWithPath("message").description("예외 메시지"),
                    fieldWithPath("requestUrl").description("요청한 url"),
                    fieldWithPath("method").description("요청 method")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 수정 성공 - put /api/v1/posts/{postId} - Post 수정에 성공한다.")
    @Test
    void updateAllSuccess() throws Exception {
        //given
        User user = User.create("name", 28, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";

        Post post = Post.create(title, content);
        post.changeUser(user);
        postRepository.save(post);
        Long postId = post.getId();

        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);


        this.mockMvc.perform(put("/api/v1/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequest)))
            .andExpect(status().isOk())
            .andDo(document("posts-update-all",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postId").description("Post Id")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 수정 실패 - put /api/v1/posts/{postId} - Post가 없으므로 Post 수정에 실패한다.")
    @Test
    void updateAllFailNotFoundPost() throws Exception {
        //given

        Long postId = 0L;

        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);


        this.mockMvc.perform(put("/api/v1/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequest)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.requestUrl").exists())
            .andExpect(jsonPath("$.method").exists())
            .andDo(document("posts-update-all-FailNotFound",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postId").description("Post Id")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                ),
                responseFields(
                    fieldWithPath("timeStamp").description("서버 응답 시간"),
                    fieldWithPath("message").description("예외 메시지"),
                    fieldWithPath("requestUrl").description("요청한 url"),
                    fieldWithPath("method").description("요청 method")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 수정 실패 - put /api/v1/posts/{postId} - Post의 title이나 content가 빈 값 Post 수정에 실패한다.")
    @Test
    void updateAllFailEmptyRequestFail() throws Exception {
        //given

        User user = User.create("name", 28, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";

        Post post = Post.create(title, content);
        post.changeUser(user);
        postRepository.save(post);
        Long postId = post.getId();

        String updateTitle = "";
        String updateContent = "";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);


        this.mockMvc.perform(put("/api/v1/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.requestUrl").exists())
            .andExpect(jsonPath("$.method").exists())
            .andDo(document("posts-update-all-FailEmptyValue",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postId").description("Post Id")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                ),
                responseFields(
                    fieldWithPath("timeStamp").description("서버 응답 시간"),
                    fieldWithPath("message").description("예외 메시지"),
                    fieldWithPath("requestUrl").description("요청한 url"),
                    fieldWithPath("method").description("요청 method")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 조회 성공 - get /api/v1/posts - Post 조회에 성공하고 createdAt 역순으로 출력한다. hasNext = true이고 요청한 pageSize 와 size가 같다")
    @Test
    void findAllPostByCursorIdSuccessHasNext() throws Exception {
        //given
        int size = 50;
        List<Post> posts = saveAll(size);
        Long cursorId = posts.get(size / 2).getId();

        int pageSize = 20;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cursorId", cursorId.toString());
        params.add("pageSize", String.valueOf(pageSize));

        this.mockMvc.perform(get("/api/v1/posts")
                .params(params)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.postResponses.length()").value(pageSize))
            .andExpect(jsonPath("$.hasNext").value(true))
            .andExpect(jsonPath("$.cursorId").value(cursorId))
            .andDo(document("posts-findAll-HasNext",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("cursorId").description("커서 기준 Id (PostId)"),
                    parameterWithName("pageSize").description("요청할 페이지 사이즈")
                ),
                responseFields(
                    fieldWithPath("cursorId").type(JsonFieldType.NUMBER).description("커서 기준 Id PostId"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                    fieldWithPath("postResponses[]").type(JsonFieldType.ARRAY).description("게시글들"),
                    fieldWithPath("postResponses[].postId").type(JsonFieldType.NUMBER).description("게시글 Id"),
                    fieldWithPath("postResponses[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("postResponses[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("postResponses[].userId").type(JsonFieldType.NUMBER).description("유저 Id"),
                    fieldWithPath("postResponses[].createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
                    fieldWithPath("postResponses[].createdBy").description("게시글 작성자")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 조회 성공 - get /api/v1/posts - Post 조회에 성공하고 createdAt 역순으로 출력한다. hasNext = false 이고 요청한 pageSize 보다 size가 작다")
    @Test
    void findAllPostByCursorIdSuccessNotHasNext() throws Exception {
        //given
        int size = 19;
        List<Post> posts = saveAll(size);
        Long cursorId = posts.get(size / 2).getId();

        int pageSize = 20;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cursorId", cursorId.toString());
        params.add("pageSize", String.valueOf(pageSize));

        this.mockMvc.perform(get("/api/v1/posts")
                .params(params)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.postResponses.length()", lessThan(pageSize)))
            .andExpect(jsonPath("$.hasNext").value(false))
            .andExpect(jsonPath("$.cursorId").value(cursorId))
            .andDo(document("posts-findAll-NotHasNext",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("cursorId").description("커서 기준 Id (PostId)"),
                    parameterWithName("pageSize").description("요청할 페이지 사이즈")
                ),
                responseFields(
                    fieldWithPath("cursorId").type(JsonFieldType.NUMBER).description("커서 기준 Id PostId"),
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                    fieldWithPath("postResponses[]").type(JsonFieldType.ARRAY).description("게시글들"),
                    fieldWithPath("postResponses[].postId").type(JsonFieldType.NUMBER).description("게시글 Id"),
                    fieldWithPath("postResponses[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("postResponses[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("postResponses[].userId").type(JsonFieldType.NUMBER).description("유저 Id"),
                    fieldWithPath("postResponses[].createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
                    fieldWithPath("postResponses[].createdBy").description("게시글 작성자")
                )
            ))
            .andDo(print());
    }

    @DisplayName("Post 조회 실패 - get /api/v1/posts - Post가 없으므로 Post 조회에 실패한다.")
    @Test
    void findAllPostByCursorIdFailNotFound() throws Exception {
        //given
        Long cursorId = 20L;

        int pageSize = 20;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cursorId", cursorId.toString());
        params.add("pageSize", String.valueOf(pageSize));

        this.mockMvc.perform(get("/api/v1/posts")
                .params(params)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.timeStamp").exists())
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.requestUrl").exists())
            .andDo(document("posts-findAll-NotFound",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("cursorId").description("커서 기준 Id (PostId)"),
                    parameterWithName("pageSize").description("요청할 페이지 사이즈")
                ),
                responseFields(
                    fieldWithPath("timeStamp").description("서버 응답 시간"),
                    fieldWithPath("message").description("예외 메시지"),
                    fieldWithPath("requestUrl").description("요청한 url"),
                    fieldWithPath("method").description("요청 method")
                )
            ))
            .andDo(print());
    }

    private List<Post> saveAll(int size) {
        User user = User.create("name", 28, "");
        userRepository.save(user);

        List<Post> posts = IntStream.range(0, size)
            .mapToObj(v -> {
                    Post post = new Post("title" + v, "content" + v);
                    post.changeUser(user);
                    return post;
                }
            )
            .collect(Collectors.toList());

        return postRepository.saveAll(posts);
    }

}