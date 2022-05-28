package org.prgrms.board.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@CreatedBy
	@Column(nullable = true, updatable = false)
	protected String createdBy;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}
}