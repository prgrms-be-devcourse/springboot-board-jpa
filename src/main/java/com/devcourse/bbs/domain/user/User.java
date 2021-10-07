package com.devcourse.bbs.domain.user;

import com.devcourse.bbs.domain.DateRecordedEntity;
import com.devcourse.bbs.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class User extends DateRecordedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_name", nullable = false, unique = true, length = 64)
    private String name;
    @Column(name = "user_age", nullable = false)
    private int age;
    @Column(name = "hobby", nullable = false, length = 128)
    private String hobby;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    public void updateName(@NonNull String name) {
        if(name.isBlank()) throw new IllegalArgumentException("Username cannot be blank.");
        this.name = name;
    }

    public void updateAge(int age) {
        if(age < 0) throw new IllegalArgumentException("Age cannot be negative.");
        this.age = age;
    }

    public UserDTO toDTO() {
        return new UserDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
