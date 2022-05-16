package com.programmers.board.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import com.programmers.board.exception.DomainException;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Where(clause = "delete_yn=0")
@DynamicUpdate
@AttributeOverride(name = "id", column = @Column(name = "post_id"))
@Entity
public class Post extends BaseEntity {

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "CLOB")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@ToString.Exclude
	private Customer customer;

	@Column(nullable = false, name = "delete_yn")
	private boolean deleteYn = false;

	@Builder
	public Post(String title, String content, Customer customer) {
		this.title = title;
		this.content = content;
		this.customer = customer;
	}

	protected Post() {
	}

	public Post update(Post updateDtoToPost) {
		if (updateDtoToPost.getTitle().isBlank() || updateDtoToPost.getContent().isBlank()) {
			throw new DomainException.ConstraintException("title 과 content 는 최소 1자 이상 존재해야만 합니다..");
		}

		this.title = updateDtoToPost.getTitle();
		this.content = updateDtoToPost.getContent();

		return this;
	}

	public Post softDelete() {
		this.deleteYn = true;

		return this;
	}
}
