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

import com.programmers.jpaboard.domain.BaseEntity;
import com.programmers.jpaboard.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 100)
	private String title;

	@Lob
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Post(String title, String content, User user) {
		this.title = title;
		this.content = content;
		this.user = user;
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
