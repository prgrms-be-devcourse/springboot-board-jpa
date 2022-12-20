package com.ys.board.domain.user.model;

import com.ys.board.domain.base.AbstractCreatedColumn;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends AbstractCreatedColumn {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 1)
    private String name;

    @Size(min = 1)
    private int age;

    private String hobby;

    public User(String name, int age, String hobby) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User create(String name, Integer age, String hobby) {
        return new User(name, age, hobby);
    }

    private void validateAge(int age) {
        Assert.isTrue(age > 0, "age는 1 이상 이여야 합니다.");
    }

    private void validateName(String name) {
        Assert.hasText(name, "이름은 빈 값이면 안되고 1글자 이상이여야 합니다");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(
            name, user.name) && Objects.equals(hobby, user.hobby);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, hobby);
    }

}
