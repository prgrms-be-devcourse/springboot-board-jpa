package com.devco.jpaproject.domain;

import com.devco.jpaproject.domain.common.BaseEntity;
import com.devco.jpaproject.domain.embedded.Posts;
import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    private String hobby;

    @Embedded
    private Posts posts;
}
