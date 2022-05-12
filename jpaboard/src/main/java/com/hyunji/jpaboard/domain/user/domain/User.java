package com.hyunji.jpaboard.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;

@DynamicUpdate
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String hobby;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @Builder
    public User(String name, int age, String hobby, String createdBy) {
        checkArgument(Strings.isNotBlank(name), "name 공백 불가");
        checkArgument(age > 0, "age 0 이하 불가");
        checkArgument(Strings.isNotBlank(hobby), "hobby 공백 불가");
        checkArgument(Strings.isNotBlank(createdBy), "createdBy 공백 불가");

        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdBy = createdBy;
    }
}
