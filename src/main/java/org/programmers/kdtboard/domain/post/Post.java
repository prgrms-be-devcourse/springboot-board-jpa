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

@Entity
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String title;

	@Lob
	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	protected Post() {}

	public static Post create(String title, String content) {
		return new Post(title, content);
	}

	public void update(String title, String content) {
		this.content = content;
		this.title = title;
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
