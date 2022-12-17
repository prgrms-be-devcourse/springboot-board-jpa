package com.programmers.jpaboard.domain.post.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.programmers.jpaboard.domain.BaseEntity;
import com.programmers.jpaboard.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Post extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false)
	private String title;

	@Lob
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public Post(String title, String content, User user) {
		this.title = title;
		this.content = content;
		setUser(user);
	}

	public Post(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public void setUser(User user) {
		if (Objects.nonNull(this.user)) {
			this.user.getPosts().remove(this);
		}

		this.user = user;
		user.getPosts().add(this);
	}

	public void changePost(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
