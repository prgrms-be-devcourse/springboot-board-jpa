package com.programmers.springboard.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 60)
	private String title;

	@Column(nullable = false)
	@Lob
	private String content;

	@Builder.Default
	private Boolean isDeleted = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
 	private Member member;

	public void changePostTitleContent(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
