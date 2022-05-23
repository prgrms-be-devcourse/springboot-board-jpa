package com.prgrms.boardjpa.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Board 클래스 테스트")
public class BoardTest {
	@Nested
	@DisplayName("Board 생성 실패 테스트")
	public class BoardCreationFailTest {
		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {"   "})
		@DisplayName("title 이 비어있는경우")
		public void withNullTitle(String title) {
			Assertions.assertThatThrownBy(() ->
					createBoard(title, "content"))
				.hasMessage("제목을 입력해주세요");
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {"   "})
		@DisplayName("contents 가 비어있는경우")
		public void withNullContents(String contents) {
			Assertions.assertThatThrownBy(() ->
					createBoard("title", contents)
				)
				.hasMessage("본문을 입력해주세요");
		}
	}

	private Board createBoard(String title, String content) {
		return Board.builder()
			.title(title)
			.content(content)
			.build();
	}
}
