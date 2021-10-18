package com.example.board.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {

	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;

	protected BaseEntity() {
		this.createdAt = LocalDateTime.now();
	}
}
