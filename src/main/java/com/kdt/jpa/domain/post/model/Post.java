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

	protected Post() {
	}

	private Post(String title, String content, Member author) {
		super(LocalDateTime.now(), author.getName());
		this.title = title;
		this.content = content;
		this.setAuthor(author);
	}

	public Post(Long id, String title, String content, Member author) {
		this(title, content, author);
		this.id = id;
		this.setAuthor(author);
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

	public String updateTitle(String title) {
		if (title != null) {
			this.title = title;
		}

		return this.title;
	}

	public String updateContent(String content) {
		if (content != null) {
			this.content = content;
		}

		return this.content;
	}

	public static Post createNew(String title, String content, Member author) {
		return new Post(title, content, author);
	}
}
