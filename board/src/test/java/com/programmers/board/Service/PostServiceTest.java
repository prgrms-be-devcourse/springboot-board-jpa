package com.programmers.board.Service;

import static org.assertj.core.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
				.title(dtoToPost.getTitle())
				.content(dtoToPost.getContent())
				.build();

		BDDMockito.given(postConverter.toDomain(saveDto)).willReturn(dtoToPost);
		BDDMockito.given(postRepository.save(dtoToPost)).willReturn(dtoToPost);
		BDDMockito.given(postConverter.toResponse(dtoToPost)).willReturn(postToResponse);

		//when
		PostDto.Response saved = postService.save(saveDto);

		//then
		Assertions.assertNotNull(saved);
		assertThat(saved.title()).isEqualTo(dtoToPost.getTitle());
		assertThat(saved.content()).isEqualTo(dtoToPost.getContent());
		assertThat(saved.title()).isEqualTo(saveDto.title());
		assertThat(saved.content()).isEqualTo(saveDto.content());
	}

}