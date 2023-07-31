package com.programmers.heheboard.domain.post;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostController.class)
@AutoConfigureRestDocs(uriScheme = "https")
class PostTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void create() throws Exception {
		// given
		PostResponseDTO responseDTO = PostResponseDTO.builder()
			.title("title")
			.content("content")
			.createdAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.modifiedAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.build();

		when(postService.createPost(any(CreatePostRequestDto.class))).thenReturn(responseDTO);

		// when
		CreatePostRequestDto requestDto = new CreatePostRequestDto("title", "content", 1L);

		ResultActions result = this.mockMvc.perform(
			post("/posts")
				.contentType((MediaType.APPLICATION_JSON))
				.content(objectMapper.writeValueAsString(requestDto))
		);

		// then
		result.andExpect(status().isOk())
			.andDo(print())
			.andDo(
				document("post-save",
					requestFields(
						fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User Id"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
					),
					responseFields(
						fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
						fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일"),
						fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정일")
					)
				)
			);
	}

	@Test
	void findSinglePost() throws Exception {
		// given
		PostResponseDTO responseDTO = PostResponseDTO.builder()
			.title("title")
			.content("content")
			.createdAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.modifiedAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.build();

		Long postId = 1L;

		when(postService.findPost(postId)).thenReturn(responseDTO);

		// when
		ResultActions result = this.mockMvc.perform(
			get("/posts/%,d".formatted(postId))
		);

		// then
		result.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-find",
					responseFields(
						fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
						fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일"),
						fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정일")
					)
				)
			);
	}

	@Test
	void getPosts() throws Exception {
		// given
		List<PostResponseDTO> postList = Arrays.asList(
			PostResponseDTO.builder()
				.title("title3")
				.content("content3")
				.createdAt(LocalDateTime.of(2000, 1, 1, 1, 1))
				.modifiedAt(LocalDateTime.of(2000, 1, 1, 1, 1))
				.build(),

			PostResponseDTO.builder()
				.title("title4")
				.content("content4")
				.createdAt(LocalDateTime.of(2000, 1, 1, 1, 1))
				.modifiedAt(LocalDateTime.of(2000, 1, 1, 1, 1))
				.build()
		);

		Slice<PostResponseDTO> responseSlice = new SliceImpl<>(postList);

		int page = 1;
		int size = 2;

		when(postService.getPosts(page, size)).thenReturn(responseSlice);

		// when
		ResultActions result = this.mockMvc.perform(
			get("/posts")
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size))
		);

		// then
		result.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-get",
					responseFields(
						fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
						fieldWithPath("data.content").description("List of Posts"),
						fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("내용"),
						fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("생성일"),
						fieldWithPath("data.content[].modifiedAt").type(JsonFieldType.STRING).description("수정일"),

						fieldWithPath("data.pageable").type(JsonFieldType.STRING).description("pageable"),

						fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
						fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),

						fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
						fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
						fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),

						fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
						fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
						fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
						fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty")
					)
				)
			);
	}

	@Test
	void update() throws Exception {
		Long postId = 1L;

		PostResponseDTO responseDTO = PostResponseDTO.builder()
			.title("updated title")
			.content("updated content")
			.createdAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.modifiedAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.build();

		when(postService.updatePost(eq(1L), any(UpdatePostRequestDto.class))).thenReturn(responseDTO);

		UpdatePostRequestDto updatePostRequestDto = new UpdatePostRequestDto("updated content", "updated content");

		// when
		ResultActions result = this.mockMvc.perform(
			put("/posts/%,d".formatted(postId))
				.contentType((MediaType.APPLICATION_JSON))
				.content(objectMapper.writeValueAsString(updatePostRequestDto))
		);

		// then
		result.andExpect(status().isOk())
			.andDo(print())
			.andDo(
				document("post-update",
					requestFields(
						fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
					),
					responseFields(
						fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
						fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성일"),
						fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정일")
					)
				)
			);
	}
}