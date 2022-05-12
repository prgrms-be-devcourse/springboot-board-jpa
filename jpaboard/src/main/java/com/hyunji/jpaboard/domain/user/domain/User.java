package com.hyunji.jpaboard.domain.user.domain;

import com.hyunji.jpaboard.model.BaseEntity;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String hobby;

    @Builder
    public User(String name, int age, String hobby) {
        checkArgument(Strings.isNotBlank(name), "name 공백 불가");
        checkArgument(age > 0, "age 0 이하 불가");
        checkArgument(Strings.isNotBlank(hobby), "hobby 공백 불가");

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
