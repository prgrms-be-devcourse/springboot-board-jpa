package com.programmers.heheboard.domain.post;

import java.util.Objects;
import java.util.regex.Pattern;

import com.programmers.heheboard.domain.user.User;
import com.programmers.heheboard.global.BaseEntity;
import com.programmers.heheboard.global.codes.ErrorCode;
import com.programmers.heheboard.global.exception.GlobalRuntimeException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "posts")
@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
	private static final int TITLE_MAX_LEN = 30;
	private static final int TITLE_MIN_LEN = 1;
	private static final String NOT_VALID_TITLE_REG_EXP = "//gm";
	private static final String NOT_VALID_CONTENT_REG_EXP = "//gm";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String title;
	@Lob
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Post(String title, String content) {
		validateTitle(title);
		validateContent(content);
		this.title = title;
		this.content = content;
	}

	private void validateTitle(String title) {
		if (title.length() < TITLE_MIN_LEN ||
			title.length() > TITLE_MAX_LEN ||
			Pattern.matches(NOT_VALID_TITLE_REG_EXP, title)) {
			throw new GlobalRuntimeException(ErrorCode.POST_TITLE_VALIDATION_FAIL);
		}
	}

	private void validateContent(String content) {
		if (Pattern.matches(NOT_VALID_CONTENT_REG_EXP, content)) {
			throw new GlobalRuntimeException(ErrorCode.POST_CONTENT_VALIDATION_FAIL);
		}
	}

	public void attachUser(User user) {
		if (Objects.nonNull(this.user)) {
			this.user.removePost(this);
		}

		this.user = user;
	}

	public void changeTitle(String newTitle) {
		validateTitle(newTitle);
		this.title = newTitle;
	}

	public void changeContents(String newContent) {
		validateContent(content);
		this.content = newContent;
	}
}
