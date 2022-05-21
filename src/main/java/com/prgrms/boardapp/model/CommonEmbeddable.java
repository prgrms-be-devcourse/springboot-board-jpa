package com.prgrms.boardapp.model;

import com.prgrms.boardapp.constant.UserErrMsg;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class CommonEmbeddable {
    @Column(
            length = 50
    )
    private String createdBy;

    @Column(
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    public static final int CREATED_BY_MAX_LENGTH = 50;

    protected CommonEmbeddable() {
    }

    public CommonEmbeddable(String createdBy, LocalDateTime createdAt) {
        this.validateCreatedBy(createdBy);
        this.createdBy = createdBy;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    private void validateCreatedBy(String createdBy) {
        if (createdBy != null)
            Assert.isTrue(createdBy.length() <= CREATED_BY_MAX_LENGTH, UserErrMsg.CREATED_LENGTH_ERR_MSG.getMessage());
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static int getCreatedByMaxLength() {
        return CREATED_BY_MAX_LENGTH;
    }

    @Override
    public String toString() {
        return "CommonEmbeddable{" + "createdBy=" + createdBy + ", createdAt=" + createdAt + '}';
    }

    public static CommonEmbeddableBuilder builder() {
        return new CommonEmbeddableBuilder();
    }

    public static class CommonEmbeddableBuilder {
        private String createdBy;
        private LocalDateTime createdAt;

        CommonEmbeddableBuilder() {
        }

        public CommonEmbeddableBuilder createdBy(final String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public CommonEmbeddableBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommonEmbeddable build() {
            return new CommonEmbeddable(this.createdBy, this.createdAt);
        }
    }
}
