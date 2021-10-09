package kdt.prgrms.devrun.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String loginPw;

    private String name;

    private int age;

    private String email;

    private String hobby;

    @OneToMany(mappedBy = "user")
    List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        if(Objects.nonNull(post)){
            this.posts.remove(post);
        }
        this.posts.add(post);
    }

}
