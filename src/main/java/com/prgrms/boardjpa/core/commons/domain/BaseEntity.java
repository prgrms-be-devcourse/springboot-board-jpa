package com.prgrms.boardjpa.core.commons.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	protected BaseEntity() {
	}

	protected BaseEntity(LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@CreatedDate
	@Column(nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@LastModifiedDate
	protected LocalDateTime updatedAt;
}
