package com.ray.springbootboard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @Range(min = 1, max = 120)
    private Integer age;

    @Size(max = 20)
    @Column(length = 20)
    private String hobby;

    public static User withOnlyId(Long id) {
        return new User(id, null, null, null);
    }
}
