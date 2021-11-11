package kdt.prgms.springbootboard.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE user_id=?")
@Where(clause = "deleted=false")
@Table(name = "user")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Embedded
    private Hobby hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void changeUserProfile(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
