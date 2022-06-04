package org.programmers.kdtboard.domain.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.programmers.kdtboard.domain.BaseEntity;
import org.programmers.kdtboard.domain.user.User;

import lombok.Builder;

@Builder
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

	public Post(Long id, String title, String content, User user) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.user = user;
	}

	protected Post() {
	}

	public Post update(String title, String content) {
		this.title = title;
		this.content = content;

		return this;
	}

	public void setUser(User user) {
		this.user = user;
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
