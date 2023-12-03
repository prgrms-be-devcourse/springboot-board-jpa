package com.programmers.springboard.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", nullable = false, length = 60)
	private String title;

	@Column(name = "content", nullable = false)
	@Lob
	private String content;

	@Column(name = "is_deleted")
	private Boolean isDeleted = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "id")
	private Member member;

	public Post(String title, String content, Member member) {
		this.title = title;
		this.content = content;
		this.member = member;
		this.isDeleted = false;
	}

	public void changePostTitleAndContent(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
