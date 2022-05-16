package com.programmers.board.Service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.programmers.board.controller.PostDto;
import com.programmers.board.domain.Post;
import com.programmers.board.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceTest {

	@InjectMocks
	private PostService postService;

	@Mock
	private PostConverter postConverter;

	@Mock
	private PostRepository postRepository;

	@Test
	@DisplayName("post 발행")
	void testSave() {
		//given
		PostDto.Save saveDto = PostDto.Save.builder()
				.title("test")
				.content("test contents...")
				.build();

		Post dtoToPost = Post.builder()
				.title(saveDto.title())
				.content(saveDto.content())
				.build();

		PostDto.Response postToResponse = PostDto.Response.builder()
				.id(dtoToPost.getId())
				.title(dtoToPost.getTitle())
				.content(dtoToPost.getContent())
				.build();

		given(postConverter.toDomain(saveDto)).willReturn(dtoToPost);
		given(postRepository.save(dtoToPost)).willReturn(dtoToPost);
		given(postConverter.toResponse(dtoToPost)).willReturn(postToResponse);

		//when
		PostDto.Response saved = postService.save(saveDto);

		//then
		Assertions.assertNotNull(saved);
		assertThat(saved.title()).isEqualTo(dtoToPost.getTitle());
		assertThat(saved.content()).isEqualTo(dtoToPost.getContent());
		assertThat(saved.title()).isEqualTo(saveDto.title());
		assertThat(saved.content()).isEqualTo(saveDto.content());
	}

	/**
	 * note : issue
	 */
	@Test
	@DisplayName("특정 post 조회 _ id")
	void testFoundOne() {
		//given
		long savedId = 1L;
		Post testPost = Post.builder()
				.title("test title")
				.content("test contents ... ")
				.build();

		ReflectionTestUtils.setField(testPost, "id", savedId);

		PostDto.Response postToResponse = PostDto.Response.builder()
				.id(testPost.getId())
				.title(testPost.getTitle())
				.content(testPost.getContent())
				.createdAt(testPost.getCreatedAt())
				.updatedAt(testPost.getUpdatedAt())
				.createdBy(testPost.getCreatedBy())
				.updatedBy(testPost.getUpdatedBy())
				.build();

		given(postRepository.findById(savedId)).willReturn(Optional.of(testPost));
		given(postConverter.toResponse(testPost)).willReturn(postToResponse);

		//when
		PostDto.Response response = postService.findOne(savedId);

		//then
		Assertions.assertNotNull(response);
		MatcherAssert.assertThat(response, Matchers.samePropertyValuesAs(postToResponse));
	}

}