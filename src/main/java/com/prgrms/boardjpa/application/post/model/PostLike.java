package com.prgrms.boardjpa.application.post.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.core.commons.domain.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class PostLike extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", referencedColumnName = "id") // Post 엔티티의 pk 와 join
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	protected PostLike() {}

	public PostLike(Long id, Post post, User user) {
		this.id = id;
		this.post = post;
		this.user = user;
	}

	public PostLike(Post post, User user) {
		this(null, post, user);
	}

}
