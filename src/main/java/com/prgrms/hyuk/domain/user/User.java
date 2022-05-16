package com.prgrms.hyuk.domain.user;

import com.prgrms.hyuk.domain.BaseEntity;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;
}
