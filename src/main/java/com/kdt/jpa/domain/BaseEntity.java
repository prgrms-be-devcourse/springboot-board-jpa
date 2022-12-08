package com.kdt.jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "created_by")
	private String createdBy;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public BaseEntity(LocalDateTime createdAt, String createdBy) {
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}

	public BaseEntity() {
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
