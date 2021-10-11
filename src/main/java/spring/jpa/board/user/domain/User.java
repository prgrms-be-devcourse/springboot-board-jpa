package spring.jpa.board.user.domain;

import lombok.Getter;
import lombok.Setter;
import spring.jpa.board.Utils.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name", nullable = false, length = 20)
    private String name;

    @Column(name="age",  nullable = false)
    private Integer age;

    @Column(name="hobby")
    private String hobby;
}
