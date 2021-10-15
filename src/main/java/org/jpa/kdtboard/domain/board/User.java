package org.jpa.kdtboard.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by yunyun on 2021/10/11.
 */
@Getter
@Setter
@Entity
@Table(name="users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    private int age;

    @Column(length = 50)
    private String hobby;


}
