package com.seungwon.board;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seungwon.board.member.domain.Member;
import com.seungwon.board.member.infra.MemberRepository;
import com.seungwon.board.post.application.dto.PostRequestDto;
import com.seungwon.board.post.domain.Post;
import com.seungwon.board.post.infra.PostRepository;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class BoardApplicationTests {
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PostRepository postRepository;

	private Member member;
	private Post post;

	@BeforeEach
	void setup() {
		member = Member.builder()
				.hobby("운동")
				.age(26)
				.name("한승원")
				.build();
		memberRepository.save(member);
		post = Post.builder()
				.writer(member)
				.content("게시글 내용")
				.title("게시글 제목")
				.build();
		postRepository.save(post);
	}

	@AfterEach
	void tearDown() {
		postRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("게시글을 등록한다")
	void create() throws Exception {
		// given
		// when
		long memberId = member.getId();
		PostRequestDto postRequestDto = PostRequestDto.builder()
				.title(post.getTitle())
				.content(post.getContent())
				.writerId(memberId)
				.build();

		// then
		mockMvc.perform(post("/api/v1/posts")
						.content(objectMapper.writeValueAsString(postRequestDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("post-create",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						requestFields(
								fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
								fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 아이디")
						), responseFields(
								fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 게시글의 아이디"),
								fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 생성 날짜 및 시간")
						)
				));
	}

	@Test
	@DisplayName("게시글을 전체 조회 한다")
	void findAll() throws Exception {
		// given
		// when
		// then
		mockMvc.perform(get("/api/v1/posts")
						.param("page", String.valueOf(0))
						.param("size", String.valueOf(5))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("post-inquiry-all",
						preprocessResponse(prettyPrint()),
						responseFields(
								fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
								fieldWithPath("content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
								fieldWithPath("content[].content").type(JsonFieldType.STRING).description("게시글 내용"),
								fieldWithPath("content[].writerId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
								fieldWithPath("content[].updatedAt").type(JsonFieldType.STRING)
										.description("가장 최근의 수정일"),

								fieldWithPath("pageable.sort.sorted").description("정렬 여부"),
								fieldWithPath("pageable.sort.empty").description("데이터가 비었는지 여부"),
								fieldWithPath("pageable.sort.unsorted").description("정렬 안 됐는지 여부"),

								fieldWithPath("pageable.offset").description("몇 번째 데이터인지 (0부터 시작)"),
								fieldWithPath("pageable.pageNumber").description("현재 페이지 번호"),
								fieldWithPath("pageable.pageSize").description("한 페이지당 조회할 데이터 개수"),
								fieldWithPath("pageable.paged").description("페이징 정보를 포함하는지 여부"),
								fieldWithPath("pageable.unpaged").description("페이징 정보를 포함하지 않는지 여부"),

								fieldWithPath("last").description("마지막 페이지인지 여부"),
								fieldWithPath("totalPages").description("전체 페이지 개수"),
								fieldWithPath("totalElements").description("테이블 총 데이터 개수"),
								fieldWithPath("first").description("첫번째 페이지인지 여부"),
								fieldWithPath("numberOfElements").description("요청 페이지에서 조회 된 데이터 개수"),
								fieldWithPath("number").description("현재 페이지 번호"),
								fieldWithPath("size").description("각 페이지별로 조회할 데이터 개수"),

								fieldWithPath("sort.sorted").description("정렬 됐는지 여부"),
								fieldWithPath("sort.unsorted").description("정렬 안 됐는지 여부"),
								fieldWithPath("sort.empty").description("데이터가 비었는지 여부"),

								fieldWithPath("empty").description("데이터가 비었는지 여부")

						)
				));
	}

	@Test
	@DisplayName("게시글을 id로 단건 조회 한다")
	void findOneById() throws Exception {
		// given
		// when
		Long id = post.getId();
		// then
		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("post-inquiry-id",
						preprocessResponse(prettyPrint()),
						pathParameters(
								parameterWithName("id").description("조회하고자 하는 게시글의 ID")
						),
						responseFields(
								fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
								fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
								fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
								fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("가장 최근의 수정일")
						)
				));
	}

	@Test
	@DisplayName("게시글을 수정한다")
	void update() throws Exception {
		// given
		// when
		long memberId = member.getId();
		PostRequestDto postRequestDto = PostRequestDto.builder()
				.title(post.getTitle())
				.content(post.getContent())
				.writerId(memberId)
				.build();
		long postId = post.getId();
		// then
		mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/posts/{id}", postId)
						.content(objectMapper.writeValueAsString(postRequestDto))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("post-update",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						pathParameters(
								parameterWithName("id").description("수정하고자 하는 게시글의 ID")
						),
						requestFields(
								fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
								fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
								fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 아이디")
						), responseFields(
								fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성된 게시글의 아이디"),
								fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정된 날짜 및 시간")
						)
				));
	}

}
