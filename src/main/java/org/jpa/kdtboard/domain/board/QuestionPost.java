package org.jpa.kdtboard.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by yunyun on 2021/10/12.
 */

@Setter
@Getter
@Entity
@DiscriminatorValue("QUESTION")
public class QuestionPost extends Post{

    @Enumerated(EnumType.STRING)
    private PostScope postScope;
}
