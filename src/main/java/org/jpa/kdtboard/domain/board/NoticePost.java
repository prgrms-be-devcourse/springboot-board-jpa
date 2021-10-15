package org.jpa.kdtboard.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * Created by yunyun on 2021/10/12.
 */

@Setter
@Getter
@Entity
@DiscriminatorValue("NOTICE")
public class NoticePost extends Post{

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime expireDate;
}
