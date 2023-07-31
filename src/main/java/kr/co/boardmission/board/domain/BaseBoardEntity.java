package kr.co.boardmission.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import kr.co.boardmission.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseBoardEntity extends BaseTimeEntity {
    @Column(name = "created_by", length = 20, nullable = false, updatable = false)
    protected String createdBy;

    @Column(name = "modified_by", length = 20)
    protected String modifiedBy;

    protected BaseBoardEntity(
            String createdBy,
            String modifiedBy
    ) {
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }
}
