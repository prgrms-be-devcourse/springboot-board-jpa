package com.kdt.board.domain.user.entity;

import com.kdt.board.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "hobby")
    private String hobby;
}
