package yjh.jpa.springnoticeboard.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", columnDefinition = "TIMESTAMP",updatable = false)
    private LocalDateTime cratedAt;

}
