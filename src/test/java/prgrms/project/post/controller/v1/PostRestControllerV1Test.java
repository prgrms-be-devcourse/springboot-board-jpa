package prgrms.project.post.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import prgrms.project.post.domain.post.Post;
import prgrms.project.post.domain.user.Hobby;
import prgrms.project.post.domain.user.User;
import prgrms.project.post.repository.PostRepository;
import prgrms.project.post.repository.UserRepository;
import prgrms.project.post.service.post.PostDto;
import prgrms.project.post.service.post.PostService;
import prgrms.project.post.util.mapper.UserMapper;

import java.util.Map;
import java.util.Set;

import static java.sql.JDBCType.ARRAY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class PostRestControllerV1Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserMapper userMapper;

    User savedUser;
    Long postId;

    @BeforeEach
    void setup() {
        User user = User.builder().name("name").age(10).hobbies(Set.of(new Hobby("swim"))).build();
        savedUser = userRepository.save(user);

        var post = Post.builder().title("title").content("content").user(savedUser).build();
        var savedPost = postRepository.save(post);
        postId = savedPost.getId();
    }

    @AfterEach
    void cleanup() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("Post 를 등록한다.")
    void testSavePost() throws Exception {
        var request = PostDto.builder()
                .title("new title")
                .content("new content")
                .user(userMapper.toDto(savedUser))
                .build();

        var requestString = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/posts")
                .content(requestString)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(NULL).description("아이디"),
                                fieldWithPath("title").type(STRING).description("게시판제목"),
                                fieldWithPath("content").type(STRING).description("게시판내용"),
                                fieldWithPath("user").type(OBJECT).description("회원"),
                                fieldWithPath("user.id").type(NUMBER).description("회원아이디"),
                                fieldWithPath("user.name").type(STRING).description("회원이름"),
                                fieldWithPath("user.age").type(NUMBER).description("회원나이"),
                                fieldWithPath("user.hobbies").type(ARRAY).description("회원취미목록"),
                                fieldWithPath("user.hobbies[0].hobby").type(STRING).description("회원취미")
                                ),
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(STRING).description("응답시간"),
                                fieldWithPath("data").type(NUMBER).description("데이터")
                        )
                ));
    }

    @Test
    @DisplayName("Post 를 id 로 찾는다.")
    void testFindById() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}", postId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-find",
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(STRING).description("응답시간"),
                                fieldWithPath("data").type(OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(NUMBER).description("게시판아이디"),
                                fieldWithPath("data.title").type(STRING).description("게시판제목"),
                                fieldWithPath("data.content").type(STRING).description("게시판내용"),
                                fieldWithPath("data.user").type(OBJECT).description("회원"),
                                fieldWithPath("data.user.id").type(NUMBER).description("회원아이디"),
                                fieldWithPath("data.user.name").type(STRING).description("회원이름"),
                                fieldWithPath("data.user.age").type(NUMBER).description("회원나이"),
                                fieldWithPath("data.user.hobbies").type(ARRAY).description("회원취미목록"),
                                fieldWithPath("data.user.hobbies[0].hobby").type(STRING).description("회원취미")
                        )
                )
            );
    }

    @Test
    @DisplayName("Post 를 모두 찾는다.")
    void testFindAll() throws Exception {

        mockMvc.perform(get("/api/v1/posts")
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-find-all",
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(STRING).description("응답시간"),
                                fieldWithPath("data").type(ARRAY).description("데이터"),
                                fieldWithPath("data[0].id").type(NUMBER).description("게시판아이디"),
                                fieldWithPath("data[0].title").type(STRING).description("게시판제목"),
                                fieldWithPath("data[0].content").type(STRING).description("게시판내용"),
                                fieldWithPath("data[0].user").type(OBJECT).description("회원"),
                                fieldWithPath("data[0].user.id").type(NUMBER).description("회원아이디"),
                                fieldWithPath("data[0].user.name").type(STRING).description("회원이름"),
                                fieldWithPath("data[0].user.age").type(NUMBER).description("회원나이"),
                                fieldWithPath("data[0].user.hobbies").type(ARRAY).description("회원취미목록"),
                                fieldWithPath("data[0].user.hobbies[0].hobby").type(STRING).description("회원취미")
                        )
                    )
                );
    }

    @Test
    @DisplayName("Post 를 업데이트한다.")
    void testUpdatePost() throws Exception {
        var request = PostDto.builder()
                .id(postId)
                .title("updated title")
                .content("updated content")
                .user(userMapper.toDto(savedUser))
                .build();

        var requestString = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/v1/posts/{postId}", postId)
                .content(requestString)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(NUMBER).description("게시판아이디"),
                                fieldWithPath("title").type(STRING).description("게시판제목"),
                                fieldWithPath("content").type(STRING).description("게시판내용"),
                                fieldWithPath("user").type(OBJECT).description("회원"),
                                fieldWithPath("user.id").type(NUMBER).description("회원아이디"),
                                fieldWithPath("user.name").type(STRING).description("회원이름"),
                                fieldWithPath("user.age").type(NUMBER).description("회원나이"),
                                fieldWithPath("user.hobbies").type(ARRAY).description("회원취미목록"),
                                fieldWithPath("user.hobbies[0].hobby").type(STRING).description("회원취미")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(STRING).description("응답시간"),
                                fieldWithPath("data").type(NUMBER).description("데이터")
                        )
                    )
                );
    }
}