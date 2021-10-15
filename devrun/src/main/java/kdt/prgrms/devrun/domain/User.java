package kdt.prgrms.devrun.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void addPost(Post post) {
        if(Objects.nonNull(post)){
            this.posts.remove(post);
        }
        this.posts.add(post);
    }

}
