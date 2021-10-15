package org.jpa.kdtboard.domain.board;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by yunyun on 2021/10/11.
 */

@Getter
@Setter
@Table(name="posts")
@Entity
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition="TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") //fk
    private User user;

    @Column(length = 64 , nullable = false)
    private String password;

}
