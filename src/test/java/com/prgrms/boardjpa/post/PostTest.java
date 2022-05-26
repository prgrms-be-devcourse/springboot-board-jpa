package com.prgrms.boardjpa.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.prgrms.boardjpa.commons.exception.CreationFailException;
import com.prgrms.boardjpa.user.domain.Hobby;
import com.prgrms.boardjpa.user.domain.User;

public class PostTest {
	private User writer;

	@BeforeEach
	void setUp() {
		writer = createUser();
	}

	@Nested
	@DisplayName("게시글을 생성할 때 ")
	public class PostCreationFailTest {

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {"   "})
		@DisplayName("제목이 비어있는경우 생성에 실패한다")
		public void withEmptyTitle(String title) {
			Assertions.assertThatThrownBy(() ->
					createPost(title, writer, "content"))
				.isInstanceOf(CreationFailException.class)
				.hasMessage("Post생성에 실패하였습니다");
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {"   "})
		@DisplayName("본문이 비어있는경우 생성에 실패한다")
		public void withEmptyContents(String contents) {
			Assertions.assertThatThrownBy(() ->
					createPost("title", writer, contents))
				.isInstanceOf(CreationFailException.class)
				.hasMessage("Post생성에 실패하였습니다");
		}

		@Test
		@DisplayName("작성자가 비어있는 경우 생성에 실패한다")
		public void withNullWriter() {
			Assertions.assertThatThrownBy(() ->
					createPost("title", null, "contents"))
				.isInstanceOf(CreationFailException.class)
				.hasMessage("Post생성에 실패하였습니다");
		}
	}

	@Test
	@DisplayName("작성자 이름과 게시글 createdBy 는 일치한다")
	public void matchName_withWriterName() {
		Post newPost = createPost("title", writer, "contents");
		String writerName = writer.getName();

		Assertions.assertThat(newPost.getCreatedBy())
			.isEqualTo(writerName);
	}

	private Post createPost(String title, User writer, String content) {
		return Post.builder()
			.title(title)
			.content(content)
			.writer(writer)
			.build();
	}

	private User createUser() {
		return User.builder()
			.email("abc@naver.com")
			.age(27)
			.hobby(Hobby.MOVIE)
			.password("123")
			.name("lee")
			.build();
	}

}
