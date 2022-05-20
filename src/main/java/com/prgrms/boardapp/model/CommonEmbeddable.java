package com.prgrms.boardapp.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.prgrms.boardapp.constants.UserErrMsg;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

@Embeddable
public class CommonEmbeddable {
    @Column(
            length = 50
    )
    private String createdBy;
    @Column(
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    @LastModifiedDate
    private LocalDateTime createdAt;

    public static final int CREATED_BY_MAX_LENGTH = 50;

    protected CommonEmbeddable() {
    }

    public CommonEmbeddable(String createdBy, LocalDateTime createdAt) {
        this.validateCreatedBy(createdBy);
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    private void validateCreatedBy(String createdBy) {
        Assert.isTrue(createdBy.length() <= CREATED_BY_MAX_LENGTH, UserErrMsg.CREATED_LENGTH_ERR_MSG.getMessage());
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

        public String toString() {
            return "CommonEmbeddableBuilder(createdBy=" + this.createdBy + ", createdAt=" + this.createdAt + ")";
        }
    }
}
