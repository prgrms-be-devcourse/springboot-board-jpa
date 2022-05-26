package com.prgrms.boardjpa.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.commons.api.SuccessResponse;
import com.prgrms.boardjpa.user.domain.Hobby;
import com.prgrms.boardjpa.user.domain.User;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostRestController.class)
public class PostRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Validator validator;

	@Test
	@DisplayName("페이징 관련 요청 파라미터가 없는 경우 디폴트 페이지를 적용한다")
	void withNullPageableRequestParam() throws Exception {
		mockMvc.perform(
			get("/api/posts")
		).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor =
			ArgumentCaptor.forClass(Pageable.class);

		verify(postService).getAllByPaging(pageableCaptor.capture());

		Pageable pageRequest = pageableCaptor.getValue();
		PageRequest defaultPage = PageRequest.of(0, 20);

		assertThat(pageRequest)
			.usingRecursiveComparison()
			.isEqualTo(defaultPage);
	}

	@Test
	@DisplayName("페이징 관련 요청 파라미터가 있는 경우 전달된 파라미터로 역직렬화된 페이지를 리졸빙한다")
	void withNonNullPageableRequestParam() throws Exception {
		mockMvc.perform(
			get("/api/posts")
				.param("page", "0")
				.param("size", "2")
		).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor =
			ArgumentCaptor.forClass(Pageable.class);

		verify(postService).getAllByPaging(pageableCaptor.capture());

		Pageable pageRequest = pageableCaptor.getValue();

		assertThat(pageRequest)
			.usingRecursiveComparison()
			.isEqualTo(
				PageRequest.of(0, 2)
			);
	}

	@Test
	@DisplayName("제목이 비어있는 게시글 생성 요청 DTO 에 대한 빈 검증결과, 1개의 제약조건 위반이 발생한다")
	public void test_Dto() {
		PostDto.CreateRequest postCreateRequest =
			new PostDto.CreateRequest("  ", 1L, "    content");

		Set<ConstraintViolation<PostDto.CreateRequest>> constraintViolations = validator.validate(postCreateRequest);

		assertThat(constraintViolations.size())
			.isEqualTo(1);
	}

	@Test
	@DisplayName("컨텐츠가 비어있는 게시글 생성 요청 DTO 를 포함한 요청이 올 경우, BAD_REQUEST 로 응답한다")
	public void with_violatedDto() throws Exception {
		PostDto.CreateRequest postCreateRequest =
			new PostDto.CreateRequest("title01", 1L, "    ");

		MvcResult mvcResult = mockMvc.perform(
				post("/api/posts/")
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(postCreateRequest)))
			.andExpect(status().isBadRequest())
			.andReturn();
	}

	@Test
	@DisplayName("성공적인 응답 객체 직렬화에 성공한다")
	public void with_SuccessResponse() throws Exception {
		PostDto.Info postInfo = PostDto.Info.from(Post.builder()
			.id(1L)
			.content("content")
			.title("title")
			.writer(createUser())
			.build());

		Mockito.when(postService.getById(1L))
			.thenReturn(postInfo);

		MvcResult mvcResult = mockMvc.perform(
				get("/api/posts/{postId}", 1L))
			.andExpect(status().isOk())
			.andReturn();

		SuccessResponse<PostDto.Info> expectedResponse = SuccessResponse.of(postInfo);

		String actualResponseBody = mvcResult.getResponse()
			.getContentAsString();

		assertThat(actualResponseBody)
			.isEqualToIgnoringWhitespace(
				objectMapper.writeValueAsString(expectedResponse)
			);
	}

	private User createUser() {
		return User.builder()
			.email("abc@naver.com")
			.hobby(Hobby.MOVIE)
			.name("writer")
			.password("abc")
			.age(27)
			.build();
	}
}
