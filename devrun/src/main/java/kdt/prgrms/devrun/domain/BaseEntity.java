package kdt.prgrms.devrun.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity{

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    public void changeCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void changeUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
