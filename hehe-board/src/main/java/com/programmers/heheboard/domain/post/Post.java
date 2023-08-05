package com.programmers.heheboard.domain.post;

import java.util.Objects;

import com.programmers.heheboard.domain.user.User;
import com.programmers.heheboard.global.BaseEntity;

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
		this.title = title;
		this.content = content;
	}

	public void attachUser(User user) {
		if (Objects.nonNull(this.user)) {
			this.user.getPosts().remove(this);
		}

		this.user = user;
		user.getPosts().add(this);
	}

	public void changeTitle(String newTitle) {
		this.title = newTitle;
	}

	public void changeContents(String newContent) {
		this.content = newContent;
	}
}
