package kdt.prgrms.devrun.domain;

import lombok.Getter;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity{

    private String createdBy;

    private String updatedBy;

    public void changeCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void changeUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
