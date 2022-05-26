package com.prgrms.boardjpa.post;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostRestController.class)
public class PostRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Test
	@DisplayName("페이징 관련 요청 파라미터가 없는 경우 디폴트 페이지를 적용한다")
	void withNullPageableRequestParam() throws Exception {
		mockMvc.perform(
			get("/posts")
		).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor =
			ArgumentCaptor.forClass(Pageable.class);

		verify(postService).getAllByPaging(pageableCaptor.capture());

		Pageable pageRequest = pageableCaptor.getValue();
		PageRequest defaultPage = PageRequest.of(0, 20);

		Assertions.assertThat(pageRequest)
			.usingRecursiveComparison()
			.isEqualTo(defaultPage);
	}

	@Test
	@DisplayName("페이징 관련 요청 파라미터가 있는 경우 전달된 파라미터로 역직렬화된 페이지를 리졸빙한다")
	void withNonNullPageableRequestParam() throws Exception {
		mockMvc.perform(
			get("/posts")
				.param("page", "0")
				.param("size", "2")
		).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor =
			ArgumentCaptor.forClass(Pageable.class);

		verify(postService).getAllByPaging(pageableCaptor.capture());

		Pageable pageRequest = pageableCaptor.getValue();

		Assertions.assertThat(pageRequest)
			.usingRecursiveComparison()
			.isEqualTo(
				PageRequest.of(0, 2)
			);
	}
}
