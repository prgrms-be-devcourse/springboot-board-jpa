package org.prgms.board.domain.post.domain;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.prgms.board.domain.user.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 게시글 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
	public static final int MAX_TITLE_LENGTH = 255;

	/** 게시글 식별 번호 **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 게시글 제목 **/
	@Column(length = MAX_TITLE_LENGTH, nullable = false)
	private String title;

	/** 게시글 내용 **/
	@Lob
	@Column(nullable = false)
	private String content;

	/** 게시글 작성자 **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User writer;

	/** 게시글 작성 일자 **/
	private LocalDateTime createdAt;

	private Post(String title, String content, User writer) {
		validateTitleAndContent(title, content);
		checkNotNull(writer, "작성자는 필수 항목입니다!");

		this.title = title;
		this.content = content;
		this.createdAt = LocalDateTime.now();
		initWriter(writer);
	}

	private void validateTitleAndContent(String title, String content) {
		checkArgument(Objects.nonNull(title) && title.length() > 0 && title.length() <= MAX_TITLE_LENGTH,
			"제목은 1자 이상255자 이하이어야 합니다.");
		checkArgument(Objects.nonNull(content) && content.length() > 0, "내용은 필수입니다.");
	}

	private void initWriter(User user) {
		this.writer = user;
		user.getPosts().add(this);
	}

	public static Post create(String title, String content, User writer) {
		return new Post(title, content, writer);
	}

	public void updatePost(String title, String content) {
		validateTitleAndContent(title, content);

		this.title = title;
		this.content = content;
	}

}
