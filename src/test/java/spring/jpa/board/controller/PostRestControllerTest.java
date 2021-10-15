package spring.jpa.board.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import spring.jpa.board.domain.User;
import spring.jpa.board.dto.post.PostCreateRequest;
import spring.jpa.board.dto.post.PostModifyRequest;
import spring.jpa.board.dto.user.UserConverter;
import spring.jpa.board.dto.user.UserDto;
import spring.jpa.board.service.PostService;
import spring.jpa.board.service.UserService;

@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  @Autowired
  UserConverter userConverter;

  @Autowired
  private ObjectMapper objectMapper;

  Long userId;
  Long postId;

  @BeforeEach
  public void setUp() throws NotFoundException {
    User user = new User("강희정", 24);
    user.setHobby("영화");
    UserDto save = userService.save(userConverter.convertUserDto(user));
    userId = save.getId();

    PostCreateRequest post1 = new PostCreateRequest("테스트 글 첫번째 제목", "테스트중인 첫번째 게시글입니다.",
        userId);
    postId = postService.save(post1);

    PostCreateRequest post2 = new PostCreateRequest("테스트 글 두번째 제목", "테스트중인 두번째 게시글입니다.",
        userId);
    postId = postService.save(post2);

  }

  @AfterEach
  public void cleanAll() {
    postService.deleteAll();
    userService.deleteAll();
  }


  @Test
  @DisplayName("모든 게시글을 읽을 수 있다.")
  public void getAllPostTest() throws Exception {
    mockMvc.perform(get("/posts")
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-find-all",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간"),

                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("게시글"),
                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                    .description("title"),
                fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                    .description("content"),
                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING)
                    .description("createdAt"),
                fieldWithPath("data.content[].createdBy").type(JsonFieldType.NUMBER)
                    .description("createdBy"),
                fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER)
                    .description("userId"),
                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이징 설정"),
                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT)
                    .description("페이징 정렬"),
                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("empty"),
                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                    .description("sorted"),
                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("unsorted"),
                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER)
                    .description("offset"),
                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
                    .description("pageSize"),
                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
                    .description("pageNumber"),
                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
                    .description("unpaged"),
                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
                    .description("paged"),
                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                    .description("totalElements"),
                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
                    .description("totalPages"),
                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("정렬"),
                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("unsorted"),
                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("numberOfElements"),
                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty")

            )
        ));
    ;
  }

  @Test
  @DisplayName("id로 게시글을 찾을 수 있다.")
  public void getOnePostTest() throws Exception {
    mockMvc.perform(get("/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-find-by-id",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간"),

                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                fieldWithPath("data.createdBy").type(JsonFieldType.NUMBER).description("createdBy"),
                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId")

            )
        ));
  }

  @Test
  @DisplayName("게시글을 저장할 수 있다.")
  public void savePostTest() throws Exception {
    PostCreateRequest postCreateRequest = new PostCreateRequest("제목란입니다.", "내용란입니다.", userId);

    mockMvc.perform(post("/posts/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postCreateRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-save",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 id"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("게시글을 수정할 수 있다.")
  public void updatePostTest() throws Exception {
    PostModifyRequest postModifyRequest = new PostModifyRequest("제목란입니다.", "내용란입니다.");

    mockMvc.perform(post("/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postModifyRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-update",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data").type(JsonFieldType.NUMBER).description("게시글 id"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

}
