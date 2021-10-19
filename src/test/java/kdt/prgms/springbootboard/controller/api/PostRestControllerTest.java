package kdt.prgms.springbootboard.controller.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.stream.*;
import kdt.prgms.springbootboard.converter.UserConverter;
import kdt.prgms.springbootboard.domain.User;
import kdt.prgms.springbootboard.dto.PostDto;

import kdt.prgms.springbootboard.repository.UserRepository;
import kdt.prgms.springbootboard.service.PostService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    UserConverter userConverter;

    @Autowired
    ObjectMapper objectMapper;

    User testUser;


    @BeforeAll
    void setup() {
        testUser = userRepository.save(User.createUser("tester", 20));
    }

    @Test
    void 게시글_생성_요청_성공() throws Exception {
        // given
        var postDto = createPostDto(testUser, 1).get(0);

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("posts-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NULL).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("작성자"),
                    fieldWithPath("createdDate").type(JsonFieldType.STRING).description("생성일"),
                    fieldWithPath("lastModifiedBy").type(JsonFieldType.STRING).description("수정자"),
                    fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("수정일"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                )
            ));
    }

    @Test
    void 게시글이_제목이_유효하지_않는경우_게시글생성_요청_실패가_정상() throws Exception {
        // given
        var postDto = createPostDto(testUser, 1).get(0);
        postDto.setTitle("");

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isBadRequest())
            .andDo(document("posts-create-invalid-title-empty",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NULL).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                ),
                responseFields(
                    fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                    fieldWithPath("status").type(JsonFieldType.NUMBER).description("제목"),
                    fieldWithPath("errors").type(JsonFieldType.ARRAY).description("에러"),
                    fieldWithPath("errors[].field").type(JsonFieldType.STRING).description("필드"),
                    fieldWithPath("errors[].value").type(JsonFieldType.STRING).description("값"),
                    fieldWithPath("errors[].reason").type(JsonFieldType.STRING).description("사유"),
                    fieldWithPath("code").type(JsonFieldType.STRING).description("코드")
                )
            ));

        postDto = createPostDto(testUser, 1).get(0);
        String title = IntStream.range(0, 10).mapToObj(i -> "Very Long Title.")
            .collect(Collectors.joining());
        postDto.setTitle(title);

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isBadRequest())
            .andDo(print())
            .andDo(document("posts-create-invalid-title-long",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NULL).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                ),
                responseFields(
                    fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                    fieldWithPath("status").type(JsonFieldType.NUMBER).description("제목"),
                    fieldWithPath("errors").type(JsonFieldType.ARRAY).description("에러"),
                    fieldWithPath("errors[].field").type(JsonFieldType.STRING).description("필드"),
                    fieldWithPath("errors[].value").type(JsonFieldType.STRING).description("값"),
                    fieldWithPath("errors[].reason").type(JsonFieldType.STRING).description("사유"),
                    fieldWithPath("code").type(JsonFieldType.STRING).description("코드")
                )
            ));

    }

    @Test
    void 게시글의_작성자가_존재하지_않으면_생성_요청_실패가_정상() throws Exception {
        // given
        var postDto = createPostDto(
            User.createUser("non exist user", 20), 1
        ).get(0);

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andDo(print())
            .andDo(document("posts-create-invalid-user",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NULL).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NULL).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                ),
                responseFields(
                    fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                    fieldWithPath("status").type(JsonFieldType.NUMBER).description("제목"),
                    fieldWithPath("errors").type(JsonFieldType.ARRAY).description("에러"),
                    fieldWithPath("code").type(JsonFieldType.STRING).description("코드")
                )
            ));
    }


    @Test
    void 게시글_페이지_요청_성공() throws Exception {
        // given
        createPostDto(testUser, 30).forEach(postDto -> postService.save(postDto));

        // when, then
        mockMvc.perform(get("/api/posts/v1")
            .param("page", "2")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("posts-list",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("content"),
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("content[].title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content[].content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("content[].user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("content[].user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("content[].user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("content[].user.age").type(JsonFieldType.NUMBER).description("나이"),
                    fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                    fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                    fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                    fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                    fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                    fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                    fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                    fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                    fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                    fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                    fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                    fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                    fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                    fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                    fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                    fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                    fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                )
            ));
    }

    @Test
    void 게시글_단건_요청_성공() throws Exception {
        // given
        var resPostDto = postService.save(createPostDto(testUser, 1).get(0));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/v1/{id}", resPostDto.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("posts-detail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("아이디")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("작성자"),
                    fieldWithPath("createdDate").type(JsonFieldType.STRING).description("생성일"),
                    fieldWithPath("lastModifiedBy").type(JsonFieldType.STRING).description("수정자"),
                    fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("수정일"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                )
            ));
    }

    @Test
    void 게시글_수정_요청_성공() throws Exception {
        // given
        var postDto = createPostDto(testUser, 1).get(0);
        var restPostDetailDto = postService.save(postDto);
        System.out.println("save end");

        postDto.setTitle("updated " + postDto.getTitle());
        postDto.setContent("updated " + postDto.getContent());

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/posts/v1/{id}", restPostDetailDto.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isOk())
            .andDo(document("posts-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("아이디")
                ),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NULL).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("작성자"),
                    fieldWithPath("createdDate").type(JsonFieldType.STRING).description("생성일"),
                    fieldWithPath("lastModifiedBy").type(JsonFieldType.STRING).description("수정자"),
                    fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("수정일"),
                    fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("아이디"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이")
                )
            ));
    }


    private List<PostDto> createPostDto(User user, int count) {
        return IntStream.range(0, count).mapToObj(i -> new PostDto(
            "testTitle#" + i,
            "testContent#" + i,
            userConverter.convertUserDto(user)
        )).collect(Collectors.toCollection(ArrayList::new));
    }

}