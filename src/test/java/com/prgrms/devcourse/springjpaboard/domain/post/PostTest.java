package com.prgrms.devcourse.springjpaboard.domain.post;

import static com.prgrms.devcourse.springjpaboard.domain.post.TestPostObjectProvider.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PostTest {

	@Test
	void 객체_생성_실패_title_null() {
		String title = null;
		String content = "내용";
		assertThrows(IllegalArgumentException.class, () -> createPost(title, content));
	}

	@Test
	void 객체_생성_실패_title_empty() {
		String title = "";
		String content = "내용";
		assertThrows(IllegalArgumentException.class, () -> createPost(title, content));
	}

	@Test
	void 객체_생성_실패_title_blank() {
		String title = " ";
		String content = "내용";
		assertThrows(IllegalArgumentException.class, () -> createPost(title, content));
	}

	@Test
	void 객체_생성_실패_content_null() {
		String title = "제목";
		String content = null;
		assertThrows(IllegalArgumentException.class, () -> createPost(title, content));
	}

	@Test
	void 객체_생성_실패_content_empty() {
		String title = "제목";
		String content = "";
		assertThrows(IllegalArgumentException.class, () -> createPost(title, content));
	}

	@Test
	void 객체_생성_실패_content_blank() {
		String title = "제목";
		String content = " ";
		assertThrows(IllegalArgumentException.class, () -> createPost(title, content));
	}

	@Test
	void 객체_생성_성공() {
		String title = "제목";
		String content = "내용";
		assertDoesNotThrow(() -> createPost(title, content));
	}

}