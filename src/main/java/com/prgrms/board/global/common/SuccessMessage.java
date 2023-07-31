package com.prgrms.board.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage {
	GET_POSTS_SUCCESS("게시물 조회 성공"),
	CREATE_POST_SUCCESS("게시물 생성 성공"),
	;

	private final String value;
}
