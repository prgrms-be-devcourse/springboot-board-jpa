package kdt.prgrms.devrun.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
    name = "user",
    uniqueConstraints = {
    @UniqueConstraint(name = "UniqueLoginId", columnNames = {"login_id"}),
    @UniqueConstraint(name = "UniqueEmail", columnNames = {"email"}),
})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "login_pw", nullable = false)
    private String loginPw;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Min(0)
    @Column(name = "age", nullable = false)
    private int age;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

//    private String hobby;

    @OneToMany(mappedBy = "user")
    List<Post> posts = new ArrayList<>();

    @Builder
    private User(Long id, String loginId, String loginPw, String name, int age, String email) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.age = age;
        this.email = email;
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        if(Objects.nonNull(post)){
            this.posts.remove(post);
        }
        this.posts.add(post);
    }

}
