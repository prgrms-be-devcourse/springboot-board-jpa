package org.programmers.kdtboard.domain.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.programmers.kdtboard.controller.response.ErrorCode;
import org.programmers.kdtboard.domain.BaseEntity;
import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.exception.NotValidException;

import lombok.Builder;

@Entity
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 30)
	private String title;

	@Lob
	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	protected Post() {
	}

	@Builder
	public Post(Long id, String title, String content, User user) {
		verifyTitleContent(title, content);
		verifyUser(user);

		this.id = id;
		this.title = title;
		this.content = content;
		this.user = user;
	}

	public void update(String title, String content) {
		verifyTitleContent(title, content);

		this.title = title;
		this.content = content;
	}

	private void verifyTitleContent(String title, String content) {
		if (title.isBlank() || content.isEmpty()) {
			throw new NotValidException(ErrorCode.INVALID_REQUEST_VALUE, "title과 content는 비어있을 수 없습니다.");
		}

		if (title.length() > 30) {
			throw new NotValidException(ErrorCode.INVALID_REQUEST_VALUE, "title의 길이는 30자를 초과할 수 없습니다.");
		}
	}

	private void verifyUser(User user) {
		if (user == null) {
			throw new NotValidException(ErrorCode.INVALID_REQUEST_VALUE, "user는 null일 수 없습니다.");
		}
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public User getUser() {
		return user;
	}
}
