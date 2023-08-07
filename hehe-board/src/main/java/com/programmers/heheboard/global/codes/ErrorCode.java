package com.programmers.heheboard.global.codes;

import lombok.Getter;

@Getter
public enum ErrorCode {
	POST_CREATE_FAIL("P10001", "게시글 저장 실패", 400),
	POST_NOT_FOUND("P10003", "게시글 찾기 실패", 400),
	POST_TITLE_VALIDATION_FAIL("P10004", "게시글 제목 검증 실패", 400),
	POST_CONTENT_VALIDATION_FAIL("P10005", "게시글 내용 검증 실패", 400),

	USER_CREATE_FAIL("U10001", "사용자 저장 실패", 400),
	USER_NOT_FOUND("U10003", "사용자 찾기 실패", 400),
	USER_AGE_VALIDATION_FAIL("U10004", "사용자 나이 검증 실패", 400),
	USER_NAME_VALIDATION_FAIL("U10005", "사용자 이름 검증 실패", 400),
	USER_HOBBY_VALIDATION_FAIL("U10006", "사용자 취미 검증 실패", 400),

	VALIDATION_FAIL("V10001", "유효성 검사 실패", 400);

	private final String code;

	private final String message;

	private final int httpStatusCode;

	ErrorCode(String code, String message, int httpStatusCode) {
		this.code = code;
		this.message = message;
		this.httpStatusCode = httpStatusCode;
	}
}
