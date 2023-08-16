package com.seungwon.board.post;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import com.seungwon.board.common.exception.InvalidDataException;
import com.seungwon.board.member.domain.Member;
import com.seungwon.board.post.domain.Post;

class PostTest {
	@Mock
	private Member member;

	@ParameterizedTest
	@ValueSource(strings = {
			"",
			"게시글의 제목을 여기에 입력합니다. 제목에 최대 글자 개수 이상의 문자열이 입력되는 경우 테스트로 예외가 발생함을 검증하기 위해 입력하는 제목에 입력될 수 있는 테스트 문자열입니다."
	})
	@DisplayName("제목이 비어있거나 최대 입력 가능 길이보다 긴 경우 예외가 발생한다")
	void title_validation(String title) {
		// given
		String validContent = "게시글내용";

		// when

		// then
		assertThrows(InvalidDataException.class, () -> new Post(title, validContent, member));
	}
}
