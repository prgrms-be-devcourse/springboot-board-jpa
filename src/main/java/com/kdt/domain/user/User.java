package com.kdt.domain.user;

import static lombok.AccessLevel.PROTECTED;

import com.kdt.domain.common.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@ToString(of = {"id", "name"})
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "age")
    private int age;

    @Builder
    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = calculateAge(age);
    }

    private int calculateAge(int age) {
        return LocalDate.now().getYear() - age + 1;
    }

}
