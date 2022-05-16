package com.programmers.board.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Where(clause = "deleteYn=0")
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

	@Column(nullable = false)
	private boolean deleteYn = false;

	@Builder
	public Post(String title, String content, Customer customer) {
		this.title = title;
		this.content = content;
		this.customer = customer;
	}

	protected Post() {
	}
}
