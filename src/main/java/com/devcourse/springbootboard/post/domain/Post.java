package com.devcourse.springbootboard.post.domain;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.devcourse.springbootboard.global.domain.BaseEntity;
import com.devcourse.springbootboard.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "post")
@Getter
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Lob
	@Column(name = "content", nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "user_id",
		referencedColumnName = "id",
		foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
	)
	private User user;

	protected Post() {
	}

	@Builder
	public Post(String title, String content, User user) {
		this.title = title;
		this.content = content;
		this.user = user;
	}

	public void changeInfo(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
