package com.prgrms.work.user.domain;

import com.prgrms.work.common.BaseEntity;
import com.prgrms.work.error.EntityInvalidException;
import com.prgrms.work.post.domain.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;
    private int age;
    @Column(length = 100)
    private String hobby;

    @OneToMany(mappedBy = "user")
    List<Post> posts = new ArrayList<>();

    protected User() {}

    protected User(String name, int age, String hobby) {
        super(name, LocalDateTime.now());
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User create(String name, int age, String hobby) {
        verifyNameValid(name);

        return new User(name, age, hobby);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    private static void verifyNameValid(String name) {
        if (Objects.isNull(name)) {
            throw new EntityInvalidException("작성자의 이름은 필수로 입력하셔야 합니다.");
        }
        if (name.length() >= 150) {
            throw new EntityInvalidException("이름의 길이는 최대 100글자입니다.");
        }
    }

}
