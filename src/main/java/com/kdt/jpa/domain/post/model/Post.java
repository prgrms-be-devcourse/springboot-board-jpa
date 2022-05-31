package com.kdt.jpa.domain.post.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

import com.kdt.jpa.domain.BaseEntity;
import com.kdt.jpa.domain.member.model.Member;

import lombok.Builder;

@Entity
@Builder
@Table(name = "post")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "title")
	private String title;
	@Column(name = "content")
	private String content;
	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	private Member author;

	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public Post(Long id, String title, String content,Member member) {
		this.id = id;
		this.title = title;
		this.content = content;
		setAuthor(member);
	}

	public Post(Long id, String title, String content, Member member, LocalDateTime createdAt, String createdBy) {
		super(createdAt, createdBy);
		this.id = id;
		this.title = title;
		this.content = content;
		setAuthor(member);
	}

	protected Post() {
	}

	private void setAuthor(Member author) {
		if (Objects.nonNull(this.author)) {
			this.author.getPosts().remove(this);
		}
		this.author = author;
		author.getPosts().add(this);
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

	public Member getAuthor() {
		return author;
	}

	public Post update(Post post) {
		this.title = post.title;
		this.content = post.content;

		return this;
	}
}
