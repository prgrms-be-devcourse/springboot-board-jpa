package org.jpa.kdtboard.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by yunyun on 2021/10/12.
 */


@Setter
@Getter
@Entity
@DiscriminatorValue("INTRODUCE")
public class IntroducePost extends Post{

}
