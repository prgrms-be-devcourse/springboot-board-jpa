package org.prgms.springbootboardjpayu.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    @Length(min = 2, max = 30)
    @NotBlank
    private String name;

    @Column(name = "age")
    @Range(min = 0, max = 200)
    private Integer age;

    @Column(name = "hobby")
    @Length(max = 100)
    private String hobby;

    @Column(name = "posts")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCratedAt(LocalDateTime.now());
        this.setCreatedBy(name);
    }
}
